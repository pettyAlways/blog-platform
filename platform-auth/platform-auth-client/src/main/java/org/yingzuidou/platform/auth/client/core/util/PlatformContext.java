package org.yingzuidou.platform.auth.client.core.util;

import lombok.Data;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/20
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class PlatformContext {

    private static String issuer;

    private static String subject;

    private static String secret;

    private static String tokenHeader;

    private static String tokenHeaderPrefix;

    private static long refreshToken;

    private static int expires;

    /**
     * 经过网关的请求头部携带的key
     */
    private static String zuulHeader;

    /**
     * 经过网关的请求头部携带的value
     */
    private static String zuulHeaderValue;

    private PlatformContext() {
    }

    public static void create(String issuer, String subject, String secret, String tokenHeader, String tokenHeaderPrefix,
                        int expires, long refreshToken, String zuulHeader, String zuulHeaderValue) {
        PlatformContext.issuer = issuer;
        PlatformContext.subject = subject;
        PlatformContext.secret = secret;
        PlatformContext.tokenHeader = tokenHeader;
        PlatformContext.tokenHeaderPrefix = tokenHeaderPrefix;
        PlatformContext.expires = expires;
        PlatformContext.refreshToken = refreshToken;
        PlatformContext.zuulHeader = zuulHeader;
        PlatformContext.zuulHeaderValue = zuulHeaderValue;
    }

    public static String getIssuer() {
        return issuer;
    }

    public static void setIssuer(String issuer) {
        PlatformContext.issuer = issuer;
    }

    public static String getSubject() {
        return subject;
    }

    public static void setSubject(String subject) {
        PlatformContext.subject = subject;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        PlatformContext.secret = secret;
    }

    public static String getTokenHeader() {
        return tokenHeader;
    }

    public static void setTokenHeader(String tokenHeader) {
        PlatformContext.tokenHeader = tokenHeader;
    }

    public static String getTokenHeaderPrefix() {
        return tokenHeaderPrefix;
    }

    public static void setTokenHeaderPrefix(String tokenHeaderPrefix) {
        PlatformContext.tokenHeaderPrefix = tokenHeaderPrefix;
    }

    public static long getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(long refreshToken) {
        PlatformContext.refreshToken = refreshToken;
    }

    public static int getExpires() {
        return expires;
    }

    public static void setExpires(int expires) {
        PlatformContext.expires = expires;
    }

    public static String getZuulHeader() {
        return zuulHeader;
    }

    public static void setZuulHeader(String zuulHeader) {
        PlatformContext.zuulHeader = zuulHeader;
    }

    public static String getZuulHeaderValue() {
        return zuulHeaderValue;
    }

    public static void setZuulHeaderValue(String zuulHeaderValue) {
        PlatformContext.zuulHeaderValue = zuulHeaderValue;
    }
}
