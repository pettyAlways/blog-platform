package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
public interface ConstService {
    /**
     * 分页得到常量配置
     * type变量代表不同类型的常量
     *
     * @param constDTO 查询条件
     * @param pageInfo 分页变量
     * @return 常量列表
     */
    ConstDTO list(ConstDTO constDTO, PageInfo pageInfo);

    /**
     * 保存指定类型的常量
     *
     * @param cmsConstEntity 常量内容
     */
    void save(CmsConstEntity cmsConstEntity);

    /**
     * 更新指定类型常量
     *
     * @param cmsConstEntity 常量内容
     */
    void update(CmsConstEntity cmsConstEntity);

    /**
     * 删除指定类型常量
     *
     * @param ids 常量ID以逗号隔开
     * @param type 常量类型,用于缓存
     * @param rootResourceId 资源管理树根资源Id，用于缓存
     */
    void delete(List<Integer> ids, String type, Integer rootResourceId);

    List<CmsConstEntity> findAllConstByType(String type);

    /**
     * 查询用于资源管理根资源的常量
     *
     * @param rootResource 根资源的键名称
     * @return 根资源
     */
    CmsConstEntity findRootResource(String rootResource);
}
