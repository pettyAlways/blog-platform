package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;

import java.util.List;

/**
 * RoleService 角色服务接口
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */

public interface RoleService {

    void pagingRole(RoleDTO roleDTO, PageInfo pageInfo);

    void save(RoleEntity roleEntity);

    boolean edit(RoleEntity roleEntity);

    void delete(String ids);

    RoleDTO listAll();

    void resourceAuth(RoleDTO roleDTO);

    RoleDTO acquireResource(Integer roleId);

    /**
     * 根据当前角色Id查找所有的用户Id
     *
     * @param id 角色Id
     * @return 角色Id列表
     */
    List<Integer> findUserIdsByRole(int id);
}
