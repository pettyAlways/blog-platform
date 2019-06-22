package org.yingzuidou.platform.auth.client.core.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.yingzuidou.platform.auth.client.core.service.PlatformUserDetailsService;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.common.utils.CmsCommonUtil;

import java.util.Objects;

/**
 * 类功能描述
 * <p>开发者平台使用shiro的{@code HashedCredentialsMatcher}类进行加密，而博客系统使用SpringSecurity框架，它不支持这个
 *  HashedCredentialsMatcher类，因此根据SpringSecurity提供的接口自定义一个密码校验器
 * <p>也许看到{@link HashedCredentialsEncoder#encode(CharSequence)}中的{@code uuid}变量会感到迷惑，这个uuid是在开发者
 * 平台增加用户的时候生成，它的作用是生成MD5摘要指定的一个盐值，在{@link PlatformUserDetailsService#loadUserByUsername(String)}
 * 的方法中将uuid存放到本地线程中。
 *
 * @author 鹰嘴豆
 * @date 2019/6/22
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public class HashedCredentialsEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            String uuid = (String) ThreadStorageUtil.getItem("salt-uuid");
            return CmsCommonUtil.getMd5PasswordText(uuid, String.valueOf(rawPassword));
        } finally {
            ThreadStorageUtil.removeItemByKey("salt-uuid");
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String checkPw = encode(rawPassword);
        return Objects.equals(checkPw, encodedPassword);
    }
}
