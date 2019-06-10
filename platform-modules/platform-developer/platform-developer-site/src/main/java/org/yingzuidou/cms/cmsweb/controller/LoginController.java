package org.yingzuidou.cms.cmsweb.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.service.LoginService;
import org.yingzuidou.cms.cmsweb.service.UserService;

import java.util.Date;

/**
 * 登录控制类
 *
 * @author 鹰嘴豆
 * @date 2018/10/14     
 */
@RestController
@RequestMapping(value="/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private CmsCacheManager cmsCacheManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login.do")
    public CmsMap login(@RequestBody UserDTO userDTO) {
        Subject subject = SecurityUtils.getSubject();
        if ( !subject.isAuthenticated() ) {
            UsernamePasswordToken token = new UsernamePasswordToken(userDTO.getUserAccount(), userDTO.getUserPassword());
            try {
                // subject.login中会执行密码认证
                subject.login( token );
                // 账号在其它地方登录将踢出它在上一个登录的session
                shiroService.kickOutUser(subject);
            } catch (ExcessiveAttemptsException excessiveEx) {
                loginService.userLock(userDTO.getUserAccount());
                throw new BusinessException("用户被锁定");
            } catch (IncorrectCredentialsException iException) {
                throw new BusinessException("账号或者密码不正确");
            } catch ( AuthenticationException authException ) {
                throw new BusinessException(authException.getMessage());
            }
        }
        // 更新上一次登录时间
        CmsUserEntity user = (CmsUserEntity) subject.getPrincipal();
        user.setLoginTime(new Date());
        loginService.saveUser(user);
        return CmsMap.ok()
                .appendData("token", subject.getSession().getId());
    }

    /**
     * 退出登录清空Shiro缓存以及当前用户授权的资源缓存
     * 系统在用户登录的时候缓存用户授权的资源
     *
     * @return 请求状态
     */
    @PostMapping("/logout.do")
    public CmsMap logout() {
        String cacheKey = "resourceTree_" + CmsCommonUtil.getCurrentLoginUserId();
        cmsCacheManager.clearCacheByKeys("resourceCache", cacheKey);
        SecurityUtils.getSubject().logout();
        return CmsMap.ok();
    }
}
