package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;

import java.util.List;

/**
 * 常量配置
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 */
public interface CmsConstRepository extends PagingAndSortingRepository<CmsConstEntity, Integer>, QuerydslPredicateExecutor<CmsConstEntity> {

    /**
     * 根据id批量硬删除
     *
     * @param ids 常量id列表
     */
    void deleteAllByIdIn(List<Integer> ids);

    List<CmsConstEntity> findAllByTypeAndInUse(String type, String inUse);

    /**
     * 根据键值去找常量
     *
     * @param constKey 键值
     * @return 常量对象
     */
    CmsConstEntity findByConstKey(String constKey);
}
