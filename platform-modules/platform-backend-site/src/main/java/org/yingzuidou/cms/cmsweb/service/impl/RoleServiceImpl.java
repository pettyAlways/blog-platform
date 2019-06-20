package org.yingzuidou.cms.cmsweb.service.impl;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.RoleBiz;
import org.yingzuidou.cms.cmsweb.constant.InUseEnum;
import org.yingzuidou.cms.cmsweb.constant.IsDeleteEnum;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.core.shiro.ShiroService;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.service.RoleService;
import org.yingzuidou.platform.common.entity.RoleEntity;
import org.yingzuidou.platform.common.entity.RoleResourceEntity;
import org.yingzuidou.platform.common.entity.UserRoleEntity;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.CmsCommonUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RoleServiceImpl 角色服务实现类
 *
 * @author 鹰嘴豆
 * @date 2018/10/1
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CmsCacheManager cmsCacheManager;

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private RoleBiz roleBiz;

    @Override
    public void pagingRole(RoleDTO roleDTO, PageInfo pageInfo) {
        Page<RoleEntity> entityPage = roleBiz.findAllRoleWithCondition(roleDTO, pageInfo.toPageable());
        pageInfo.setCounts(entityPage.getTotalElements());
        roleDTO.setRoles(entityPage.getContent());
    }

    @Override
    public void save(RoleEntity roleEntity) {
        roleEntity.setCreator(1);
        roleEntity.setCreateTime(new Date());
        roleEntity.setIsDelete("N");
        roleRepository.save(roleEntity);
    }

    /**
     * 更新角色信息
     * 如果角色启用和不启用互相转换时需要重新授权
     *
     * @param roleEntity 角色实体
     * @return 是否更改是启用，如果是就需要重新加载shiro授权
     */
    @Override
    public boolean edit(RoleEntity roleEntity) {
       RoleEntity entity = roleRepository.findById(roleEntity.getId()).get();
       CmsBeanUtils.copyMorNULLProperties(roleEntity, entity);
       entity.setUpdator(1);
       entity.setUpdateTime(new Date());
       roleRepository.save(entity);
       return !roleEntity.getInUse().equals(entity.getInUse());
    }

    @Override
    public void delete(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
        List<RoleEntity> resourceList = roleRepository.findAllByIdInAndIsDeleteIs(idsList, "N");
        Optional.ofNullable(resourceList).orElse(Collections.emptyList()).forEach(item -> {
            item.setIsDelete("Y");
            item.setUpdator(1);
            item.setUpdateTime(new Date());
        });
        roleRepository.saveAll(resourceList);
    }

    @Override
    public RoleDTO listAll() {
        RoleDTO roleDTO = new RoleDTO();
        List<RoleEntity> allRoles = roleRepository
                .findAllByInUseAndIsDeleteIs(InUseEnum.USE.getValue(), IsDeleteEnum.NOTDELETE.getValue());
        roleDTO.setRoles(allRoles);
        return roleDTO;
    }

    /**
     * 保存当前用户新的授权资源
     * 先为每一个登录的用户都缓存各自授权的资源，每次更新授权以后都需要
     * 根据当前修改的角色所关联的所有用户的id去清空授权资源的缓存
     * 这需要重新定义一个缓存的注解，使用AOP织入增强（以后再优化先用缓存工具类）
     *
     * @param roleDTO 新授权资源
     */
    @Override
    public void resourceAuth(RoleDTO roleDTO) {
        // 删除原先的角色用户关联数据在重新插入
        roleResourceRepository.deleteAllByRoleIdIs(roleDTO.getId());
        roleDTO.getResources().forEach(item -> {
            RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
            roleResourceEntity.setRoleId(roleDTO.getId());
            roleResourceEntity.setResourceId(item);
            roleResourceEntity.setCreateTime(new Date());
            roleResourceEntity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
            roleResourceRepository.save(roleResourceEntity);
        });

        // 无力扩展缓存将编码清空缓存
        List<UserRoleEntity> userRoleEntities = userRoleRepository
                .findAllByRoleId(roleDTO.getId());
        Optional.ofNullable(userRoleEntities).orElse(new ArrayList<>()).stream()
                .map(UserRoleEntity::getUserId)
                .forEach(item -> cmsCacheManager
                        .clearCacheByKeys("resourceCache", "resourceTree_" + item));
    }

    @Override
    public RoleDTO acquireResource(Integer roleId) {
        RoleDTO roleDTO = new RoleDTO();
        List<RoleResourceEntity> roleResourceEntities = roleResourceRepository.findAllByRoleIdIs(roleId);
        if (!Objects.isNull(roleResourceEntities)) {
            List<Integer> ids =roleResourceEntities.stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toList());
            roleDTO.setResources(ids);
        }
        return roleDTO;
    }

    @Override
    public List<Integer> findUserIdsByRole(int id) {
        List<UserRoleEntity> userRoleEntities = userRoleRepository.findAllByRoleId(id);
        List<Integer> userIds = Optional.ofNullable(userRoleEntities).orElse(new ArrayList<>()).stream()
                .map(UserRoleEntity::getUserId).collect(Collectors.toList());
        return userIds;
    }
}
