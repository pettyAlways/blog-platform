package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dto.OnlineDTO;
import org.yingzuidou.cms.cmsweb.service.OnlineService;
import org.yingzuidou.platform.common.dto.CmsMap;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;

import java.util.List;

/**
 * 在线用户管理
 * 对在线用户做查询、踢出、禁用的功能
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 */

@RestController
@RequestMapping(value="/online")
public class OnlineController {

    private final OnlineService onlineService;

    private final ShiroService shiroService;

    @Autowired
    public OnlineController(OnlineService onlineService, ShiroService shiroService) {
        this.onlineService = onlineService;
        this.shiroService = shiroService;
    }

    @GetMapping(value="/list.do")
    public CmsMap list(OnlineDTO onlineDTO, PageInfo pageInfo) {
        List<OnlineDTO> result = onlineService.list(onlineDTO, pageInfo);
        return CmsMap.ok().appendData("counts", result.size())
                .setResult(result);
    }

    /**
     * 踢出在线用户，先结束用户的会话在WebSocket推送消息到前端
     * 不能踢出当前操作人的账号，不然接口请求返回时因为session不存在而出错
     *
     * @param userId 用户Id
     * @return 请求状态
     */
    @PostMapping("kickout.do")
    public CmsMap kickout(Integer userId) {
        if (CmsCommonUtil.getCurrentLoginUserId().equals(userId)) {
            throw new BusinessException("不能踢出当前操作用户");
        }
       shiroService.killSpecifySession(userId, "账号已经被踢出");
       return CmsMap.ok();
    }

    /**
     * 禁用指定用户
     * 不能踢出当前操作人的账号，不然接口请求返回时因为session不存在而出错
     *
     * @param userId 用户Id
     * @return 请求状态
     */
    @PostMapping("invalidUser.do")
    public CmsMap inValidUser(Integer userId) {
        if (CmsCommonUtil.getCurrentLoginUserId().equals(userId)) {
            throw new BusinessException("不能禁用当前操作用户");
        }
        onlineService.inValidUser(userId);
        shiroService.killSpecifySession(userId, "你的账号已被禁用");
        return CmsMap.ok();
    }

}
