package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OnlineDTO;

import java.util.List;

/**
 * 类功能描述
 * 在线用户服务接口
 *
 * @author 鹰嘴豆
 * @date 2018/11/26
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface OnlineService {

    List<OnlineDTO> list(OnlineDTO onlineDTO, PageInfo pageInfo);

    /**
     * 根据用户Id禁用指定用户
     * @param userId 用户Id
     */
    void inValidUser(Integer userId);
}
