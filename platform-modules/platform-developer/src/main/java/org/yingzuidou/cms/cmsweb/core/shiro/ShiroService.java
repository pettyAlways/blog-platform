package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

/**
 * ShiroService
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
public interface ShiroService {

    /**
     * 加载系统需要授权的资源
     *
     * @return 需要授权的资源
     */
    Map<String, String> loadFilterChainDefinitions();

    /**
     * 重新加载被授权的资源
     *
     * @param shiroFilterFactoryBean Shiro过滤器工厂类
     */
    void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean);

    /**
     * 如果用户已经在别的地方登录那么踢出前面的登录
     *
     * @param subject  可以获取用户的对象
     */
    void kickOutUser(Subject subject);

    /**
     * 获取在线用户的Id
     *
     * @return 在线用户Id
     */
    List<Integer> currentOnlineUser();

    /**
     * 获取在线用户Id与Session的映射
     *
     * @return 映射的Map
     */
    Map<Integer, Session> mapSessionUsingUser();

    /**
     * 根据用户Id清除指定的会话并通知前端的用户
     *
     * @param userId 用户Id
     * @param msg 提示消息
     */
    void killSpecifySession(Integer userId, String msg);
}
