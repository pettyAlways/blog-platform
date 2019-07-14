package org.yingzuidou.cms.cmsweb.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yingzuidou.cms.cmsweb.core.websocket.CmsWebSocket;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.platform.common.constant.WebSocketTypeEnum;
import org.yingzuidou.platform.common.entity.CmsUserEntity;

import java.util.*;

/**
 * ShiroServiceImpl
 *
 * @author 鹰嘴豆
 * @date 2018/10/22
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Value("${skip.login.url}")
    private String skipPath;

    /**
     * 这里本来使用ResourceBiz resourceBiz;依赖注入来加载资源，但是因为esourceBiz
     * 比shiro实例化早因此会出现AOP功能失效，实例化不会创建代理对象.增加@Lazy无效，
     * 因为ShiroConfiguration中shiroFilter创建实例前需要调用loadFilterChainDefinitions，
     * 在执行这个方法时需要调用resourceBiz中的资源加载方法，这时候resourceBiz即使
     * 懒加载也会实例化，因此退而求其次使用与AOP无关的DAO接口加载
     */
    @Autowired
    private RoleResourceRepository roleResourceRepository;

    private static final String PREMISSION_STRING = "roles[\"%s\"]";

    /**
     * 给资源路径授权
     *
     * @return 资源映射
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 需要跳过的路径
        String[] paths = skipPath.split(",");
        Arrays.stream(paths).forEach(item -> filterChainDefinitionMap.put(item, "anon"));

        // 需要校验按钮级别的权限路径（菜单模块等权限校验交给前端vue路由）
        List<Object> resources = roleResourceRepository.acquireRoleResources();
        Optional.ofNullable(resources).orElse(new ArrayList<>()).forEach(item -> {
            Object[] objs = (Object[])item;
            filterChainDefinitionMap.put(objs[0].toString(), String.format(PREMISSION_STRING, Objects.isNull(objs[1]) ? "" : objs[1]));
        });

        // 其他没有匹配的路径都需要登录了才能访问
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    /**
     *  在动态更新资源的权限的时候,重新为资源授权
     *
     * @param shiroFilterFactoryBean Shiro过滤器工厂类
     */
    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();
            // 清空授权资源路径的权限配置Map
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            // 重新加载授权资源
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成各个拦截器对应的资源路径
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim()
                        .replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }

    /**
     * 同一个账户只能在一个地方
     * 需要用WebSocket通知前端用户
     *
     * @param subject  可以获取用户的对象
     */
    @Override
    public void kickOutUser(Subject subject) {
        // 获取当前已登录的用户session列表
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        CmsUserEntity curLoginUser = (CmsUserEntity) subject.getPrincipal();
        // 停止拥有相同的用户的不同session
        Optional.ofNullable(sessions).orElse(new ArrayList<>()).forEach(session -> {
            Subject sessionSubject = new Subject.Builder().session(session).buildSubject();
            if (sessionSubject.isAuthenticated()) {
                CmsUserEntity user = (CmsUserEntity) sessionSubject.getPrincipal();
                if (user.getId() == curLoginUser.getId()) {
                    // 防止误杀当前已经登录的session
                    if (!session.getId().equals(subject.getSession().getId())) {
                        Map<String, Object> msg = new HashMap<>(2);
                        msg.put("type", WebSocketTypeEnum.TIP.getValue());
                        msg.put("msg", "账号在其他地方登陆");
                        Optional.of(CmsWebSocket.connectSessions.entrySet()).orElse(new HashSet<>())
                                .stream().filter(item -> item.getKey().equals(user.getId()))
                                .forEach(item -> CmsWebSocket.sendMessage(msg, item.getValue()));
                        session.stop();
                    }
                }
            }
        });
    }

    @Override
    public List<Integer> currentOnlineUser() {
        // 获取当前已登录的用户session列表
       Map<Integer, Session> sessions = mapSessionUsingUser();
       return new ArrayList<>(sessions.keySet());
    }

    /**
     * 在线用户Id与Session的映射
     *
     * @return 映射Map
     */
    @Override
    public Map<Integer, Session> mapSessionUsingUser() {
        Map<Integer, Session> mapSession = new HashMap<>(10);
        // 获取当前已登录的用户session列表
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        DefaultWebSessionManager sessionManager = (DefaultWebSessionManager)securityManager.getSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        Optional.ofNullable(sessions).orElse(new ArrayList<>()).parallelStream()
                .filter(session -> new Subject.Builder().session(session).buildSubject().isAuthenticated())
                .forEach(session -> {
                    Subject sessionSubject = new Subject.Builder().session(session).buildSubject();
                    CmsUserEntity user = (CmsUserEntity) sessionSubject.getPrincipal();
                    mapSession.put(user.getId(), session);
                });
        return mapSession;
    }

    /**
     * 根据指定的用户Id去清除Session会话
     *
     * @param userId 用户Id
     */
    @Override
    public void killSpecifySession(Integer userId, String msg) {
        Map<Integer, Session> sessionMap =  mapSessionUsingUser();
        if (Objects.nonNull(sessionMap)) {
            Session session = sessionMap.get(userId);
            // 先提示用户消息
            Map<String, Object> msgMap = new HashMap<>(2);
            msgMap.put("type", WebSocketTypeEnum.TIP.getValue());
            msgMap.put("msg", msg);
            CmsWebSocket.sendSpecifyUserMsg(userId, msgMap);
            // 清除会话,session.stop清除资源不够彻底还能被getAtiveSessions拿到停止的session
            Subject subject = new Subject.Builder().session(session).buildSubject();
            subject.logout();
        }
    }
}
