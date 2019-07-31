package org.yingzuidou.platform.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.yingzuidou.platform.common.constant.AccessEnum;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.exception.BusinessException;

import java.util.Date;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PasswordJwtUtil {

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
        PasswordJwtUtil.issuer = issuer;
        PasswordJwtUtil.subject = subject;
        PasswordJwtUtil.secret = secret;
        algorithm = Algorithm.HMAC256(secret);
    }

    /**
     * 密码访问知识库生成一个token返回
     *
     * @param knowledgeId 知识库ID
     * @param knowledgeName 知识库名字
     * @param password 密码
     * @return token
     */
    public static String  generateToken(Integer knowledgeId, String knowledgeName, String password) {
        return JWT.create().withIssuer(issuer)
                .withSubject(subject)
                .withClaim("knowledgeId", knowledgeId)
                .withClaim("knowledgeName", knowledgeName)
                .withClaim("password", password)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 根据获取到的token校验合法性
     *
     * @param token token
     * @param knowledgeId 知识库ID
     * @param password 密码
     * @return true 校验成功 false 校验不成功
     */
    public static boolean verifyToken(String token, Integer knowledgeId, String password) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(issuer).withSubject(subject)
                .withClaim("knowledgeId", knowledgeId).withClaim("password", password).build();
        try {
           jwtVerifier.verify(token);
           return true;
        } catch (Exception exception) {
            return false;
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

    /**
     * 校验知识库密码访问
     *
     * @param knowledgeEntity 知识库实体
     * @param userId 访问用户
     * @param token 加密知识库生成的token
     * @return true 验证通过 false 验证不通过
     */
    public static boolean verifyKnowledgeEncrypt(KnowledgeEntity knowledgeEntity, Integer userId, String token) {
        if (Objects.equals(knowledgeEntity.getKAccess(), AccessEnum.ENCRYPTION.getValue())) {
            if (!Objects.equals(knowledgeEntity.getCreator(), userId)) {
                return PasswordJwtUtil.verifyToken(token, knowledgeEntity.getId(), knowledgeEntity.getKReserveO());
            }
        }
        return true;
    }
}
