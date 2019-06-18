package org.yingzuidou.cms.cmsweb.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.List;

/**
 * UserRepository
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
public interface UserRepository extends PagingAndSortingRepository<CmsUserEntity, Integer>, QuerydslPredicateExecutor<CmsUserEntity> {

    CmsUserEntity findByUserAccountAndIsDeleteIs(String userAccount, String isDelete);

    CmsUserEntity findByUserAccountAndIdNotAndIsDelete(String userAccount, Integer id, String isDelete);

    CmsUserEntity findByIdAndIsDelete(Integer id, String isDelete);

    List<CmsUserEntity> findAllByIdInAndIsDelete(List<Integer> userIds, String isDelete);
    
    

}
