package org.yingzuidou.platform.auth.client.core.util;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

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

    private static Algorithm algorithm;

    private static String issuer;

    private static String subject;

    private static String secret;

    /**
     * 提供外部使用的工厂方法
     *
     * @param issuer token的发行者
     * @param subject token主题
     * @param secret token密钥
     */
    public static void create(String issuer, String subject, String secret) {
        JwtTokenUtil.issuer = issuer;
        JwtTokenUtil.subject = subject;
        JwtTokenUtil.secret = secret;
        algorithm = Algorithm.HMAC256(secret);
    }
    /**
     * 根据用户名密码生成token
     *
     * @param userId 用户Id
     * @param userName 用户名
     * @param password 密码
     * @return token
     */
    public static String  generateToken(Integer userId, String userName, String userAccount, String password,
                                        int expires, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create().withIssuer(issuer)
                .withSubject(subject)
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withClaim("userAccount", userAccount)
                .withClaim("password", password)
                .withClaim("authorities", JSONObject.toJSONString(authorities))
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtil.generateAfterDate(Calendar.HOUR, expires))
                .sign(algorithm);
    }

    /**
     * 根据获取到的token校验合法性
     *
     * @param token 外部token
     * @param userAccount 账号
     * @param password 密码
     */
    public static void verifyToken(String token, String userAccount, String password) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(issuer).withSubject(subject)
                .withClaim("userAccount", userAccount).withClaim("password", password).build();
        try {
            DecodedJWT decoderJWT = jwtVerifier.verify(token);
            System.out.println(decoderJWT);
        } catch (Exception exception) {
            throw new BusinessException("token无效");
        }
    }

    public static DecodedJWT decode(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(token);
        } catch (Exception exception) {
            throw new BusinessException("token无效");
        }
        return decodedJWT;
    }

    public static boolean isExpires(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(Calendar.getInstance().getTime());
    }

    public static void main(String[] args) {
    }
}
