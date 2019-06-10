package org.yingzuidou.cms.cmsweb.core.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

/**
 * 类功能描述
 * 提供系统常用到的工具接口
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v.10        常用工具接口
 */
public class CmsCommonUtil {

    private static String hashAlgorithmName = "MD5";
    private static int hashIterations = 1024;
    /**
     * 从shiro中得到当前的登录用户
     *
     * @return 当前登录用户对象
     */
    public static CmsUserEntity getCurrentLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipal();
        return user;
    }

    /**
     * 得到当前登录用户的ID,在新增和修改数据库记录中
     * 需要获取当前操作用户的ID
     *
     * @return 当前登录用户ID
     */
    public static Integer getCurrentLoginUserId() {
        CmsUserEntity currentUser = getCurrentLoginUser();
        return currentUser.getId();
    }

    /**
     * 根据一个uuid生成盐值，使用md5对密码进行加密
     *
     * @param uuid UUID
     * @param credentials 密码
     * @return
     */
    public static String getMd5PasswordText(String uuid, String credentials) {
        ByteSource credentialsSalt = ByteSource.Util.bytes(uuid);
        String password = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations).toString();
        return password;
    }
}
