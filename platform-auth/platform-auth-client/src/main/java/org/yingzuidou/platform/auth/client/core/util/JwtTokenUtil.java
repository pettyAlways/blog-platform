package org.yingzuidou.platform.auth.client.core.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.yingzuidou.platform.auth.client.core.base.JWTUserDetails;

import java.util.*;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class JwtTokenUtil {

    public static final String ROLE_REFRESH_TOKEN = "ROLE_REFRESH_TOKEN";

    private static final String CLAIM_KEY_USER_ID = "user_id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    private static final String CLAIM_KEY_ACCOUNT_ENABLED = "enabled";
    private static final String CLAIM_KEY_ACCOUNT_NON_LOCKED = "non_locked";
    private static final String CLAIM_KEY_ACCOUNT_NON_EXPIRED = "non_expired";
    private static final String CLAIM_KEY_USER_ACCOUNT = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    //签名方式
    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    //密匙
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access_token}")
    private Long access_token_expiration;

    @Value("${jwt.refresh_token}")
    private Long refresh_token_expiration;
    //过期时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据token 获取用户信息
     * @param token
     * @return
     */
    public JWTUserDetails getUserFromToken(String token) {
        JWTUserDetails  jwtUserDetails;
        try {
            final Claims claims = getClaimsFromToken(token);
            long userId = getUserIdFromToken(token);
            String username = claims.getSubject();
            List<?> roles = (List<?>) claims.get(CLAIM_KEY_AUTHORITIES);
            Collection<? extends GrantedAuthority> authorities = parseArrayToAuthorities(roles);
            boolean accountEnabled = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_ENABLED);
            boolean accountNonLocked = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_LOCKED);
            boolean accountNonExpired = (Boolean) claims.get(CLAIM_KEY_ACCOUNT_NON_EXPIRED);
            JWTUserDetails userDetails = new JWTUserDetails();
            return userDetails;
        } catch (Exception e) {
            jwtUserDetails = null;
        }
        return jwtUserDetails;
    }

    /**
     * 根据token 获取用户ID
     * @param token
     * @return
     */
    public long getUserIdFromToken(String token) {
        long userId;
        try {
            final Claims claims = getClaimsFromToken(token);
            userId = Long.valueOf(claims != null ? claims.get(CLAIM_KEY_USER_ID).toString() :"0");
        } catch (Exception e) {
            e.printStackTrace();
            userId = 0;
        }
        return userId;
    }

    /**
     * 根据token 获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 根据token 获取生成时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    /**
     * 根据token 获取过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /***
     * 解析token 信息
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    // 签名的key
                    .setSigningKey(secret)
                    // 签名token
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成失效时间
     * @param expiration
     * @return
     */
    private Date generateExpirationDate(long expiration) {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * token 是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 生成时间是否在最后修改时间之前
     * @param created   生成时间
     * @param lastPasswordReset  最后修改密码时间
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /**
     * 根据用户信息 生成token
     * @param userDetails
     * @return
     */
    public String generateAccessToken(UserDetails userDetails) {
        JWTUserDetails user = (JWTUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(authoritiesToArray(user.getAuthorities())));
        return generateAccessToken(user.getUsername(), claims);
    }

    /**
     * 重置(更新)token 过期时间
     * @param token
     * @param expiration
     */
    public String restTokenExpired(String token,long expiration){

        final Claims claims = getClaimsFromToken(token);
        Jwts.builder()
                //一个map 可以资源存放东西进去
                .setClaims(claims)
                //  用户名写入标题
                .setSubject(claims.getSubject())
                .setExpiration(new Date(expiration));
        //claims.setExpiration(new Date(expiration));
        // String refreshedToken = generateAccessToken(claims.getSubject(), claims,expiration);
        return "";
    }

    private Map<String, Object> generateClaims(JWTUserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        // claims.put(CLAIM_KEY_USER_ID, user.getUserId());
        claims.put(CLAIM_KEY_ACCOUNT_ENABLED, user.isEnabled());
        claims.put(CLAIM_KEY_ACCOUNT_NON_LOCKED, user.isAccountNonLocked());
        claims.put(CLAIM_KEY_ACCOUNT_NON_EXPIRED, user.isAccountNonExpired());
        return claims;
    }

    /**
     * 生成token
     * @param subject  用户名
     * @param claims
     * @return
     */
    private String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, access_token_expiration);
    }


    /**
     * 生成token
     * @param subject  用户名
     * @param claims
     * @return
     */
    private String generateAccessToken(String subject, Map<String, Object> claims,long expiration) {
        return generateToken(subject, claims, expiration);
    }

    /**
     * 用户所拥有的资源权限
     * @param authorities
     * @return
     */
    private List<?> authoritiesToArray(Collection<? extends GrantedAuthority> authorities) {
        List<String> list = new ArrayList<>();
        for (GrantedAuthority ga : authorities) {
            list.add(ga.getAuthority());
        }
        return list;
    }

    private Collection<? extends GrantedAuthority> parseArrayToAuthorities(List<?> roles) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority;
        for (Object role : roles) {
            authority = new SimpleGrantedAuthority(role.toString());
            authorities.add(authority);
        }
        return authorities;
    }

    /**
     * 根据用户信息 重新获取token
     * @param userDetails
     * @return
     */
    public String generateRefreshToken(UserDetails userDetails) {
        JWTUserDetails user = (JWTUserDetails) userDetails;
        Map<String, Object> claims = generateClaims(user);
        // 只授于更新 token 的权限
        String roles[] = new String[]{ROLE_REFRESH_TOKEN};
        claims.put(CLAIM_KEY_AUTHORITIES, JSON.toJSON(roles));
        return generateRefreshToken(user.getUsername(), claims);
    }

    /**
     * 重新获取token
     * @param subject 用户名
     * @param claims
     * @return
     */
    private String generateRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, refresh_token_expiration);
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token));
    }

    /**
     * 刷新重新获取token
     * @param token 源token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            refreshedToken = generateAccessToken(claims.getSubject(), claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    private String generateToken(String subject, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
                //一个map 可以资源存放东西进去
                .setClaims(claims)
                //  用户名写入标题
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(generateExpirationDate(expiration))
                //系统时间之前的token都是不可以被承认的
                //.setNotBefore(now)
                //数字签名
                .signWith(SIGNATURE_ALGORITHM, secret)
                .compact();
    }

    /**
     * 验证token 是否合法
     * @param token  token
     * @param userDetails  用户信息
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        JWTUserDetails user = (JWTUserDetails) userDetails;
        final long userId = getUserIdFromToken(token);
        final String username = getUsernameFromToken(token);
        // final Date created = getCreatedDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(token);
        return (userId == user.getUserId()
                && username.equals(user.getUsername())
                && !isTokenExpired(token)
                /* && !isCreatedBeforeLastPasswordReset(created, userDetails.getLastPasswordResetDate()) */
        );
    }


}
