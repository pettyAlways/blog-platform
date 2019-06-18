package org.yingzuidou.cms.cmsweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.ResourceBiz;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleResourceEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.CmsCommonUtil;
import org.yingzuidou.platform.common.vo.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PermissionService
 *
 * @author shangguanls
 * @date 2018/9/26
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ResourceBiz resouceBiz;

    /**
     * 获取所有的资源在资源管理、授权页面中展示
     * 将结果以key为resourceTree的形式存放到resourceCache中
     *
     * @return 系统所有资源列表
     */
    @Override
    @Cacheable(value="resourceCache", key="'resourceTree'")
    public PermissionDTO listPower() {
        List<ResourceEntity> flatNode = permissionRepository.findAllByIsDeleteIs("N");
        Node root = resouceBiz.acquirePermissions(flatNode, false);
        // 设置外部资源模块的类型在前台通过不同图标使用
        Optional.ofNullable(root.getChildren()).orElse(new ArrayList<>()).stream()
                .filter(item -> Objects.equals(item.getBelongs(), "external"))
                .forEach(item -> item.setType("exModule"));

        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setTree(root);
        return permissionDTO;
    }


    @Override
    public PermissionDTO subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        Page<ResourceEntity> allResourceWithCondition = resouceBiz.findAllResourceWithCondition(permissionDTO, pageInfo.toPageable());
        Page<ResourceEntity> ResourcePage = allResourceWithCondition;
        permissionDTO.setResources(ResourcePage.getContent());
        pageInfo.setCounts(ResourcePage.getTotalElements());
        return permissionDTO;
    }

    /**
     * 根据id删除指定的资源，同时清除resourceCache中key为resourceTree的缓存
     * 删除资源同时需要删除角色资源关联的数据
     *
     * @param ids 待删除的id列表
     */
    @Override
    @CacheEvict(value="resourceCache", key="'resourceTree'")
    public void deletePower(String ids) {
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());

        // 删除前先校验是否存在子资源
        if (checkBeforeDelete(idsList)) {
            throw new BusinessException("请先删除子资源");
        }
        // 软删除资源
        List<ResourceEntity> resourceList = permissionRepository.findAllByIdInAndIsDeleteIs(idsList, "N");
        // 删除时先检查是否存在子资源
        Optional.ofNullable(resourceList).orElse(Collections.emptyList()).forEach(item -> {
            item.setIsDelete("Y");
            item.setUpdator(CmsCommonUtil.getCurrentLoginUserId());
            item.setUpdateTime(new Date());
        });
        permissionRepository.saveAll(resourceList);

        // 删除角色资源关联数据
        roleResourceRepository.deleteAllByResourceIdIn(idsList);
    }

    private boolean checkBeforeDelete(List<Integer> idsList) {
        return permissionRepository.existsByParentIdInAndIsDelete(idsList, "N");
    }

    /**
     * 更新资源，删除resourceCache中的key为resourceCache的缓存
     *
     * @param entity 新的资源实体对象
     */
    @Override
    @CacheEvict(value="resourceCache", key="'resourceTree'")
    public void updateResouce(ResourceEntity entity) {
        // 这里对于对象是否已经被删除等就不做判断
        ResourceEntity resourceEntity = permissionRepository.findById(entity.getId()).get();
         CmsBeanUtils.copyMorNULLProperties(entity, resourceEntity);
        resourceEntity.setUpdator(CmsCommonUtil.getCurrentLoginUserId());
        resourceEntity.setUpdateTime(new Date());
        permissionRepository.save(resourceEntity);
    }

    /**
     * 保存新的资源,删除resourceCache中的key为resourceCache的缓存
     *
     * @param entity 需要保存的资源实体
     */
    @Override
    @CacheEvict(value="resourceCache", key="'resourceTree'")
    public void saveResource(ResourceEntity entity) {
        entity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
        entity.setCreateTime(new Date());
        // 设置根资源
        if (entity.getParentId() == null) {
            entity.setParentId(-1);
        }
        // 如果不设置jpa会用null覆盖mysql的默认值
        entity.setIsDelete("N");
        permissionRepository.save(entity);
    }

    /**
     * 根据用户的id获取被授权的资源，并将结果以key为resourceTree_用户id的形式存
     * 放到resourceCache缓存中
     *
     * @param userId 当前登录用户id
     * @return 当前用户被授权的资源
     */
    @Override
    @Cacheable(value="resourceCache", key="'resourceTree_'+#userId")
    public Node acquireUserPermission(int userId) {
        List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(userId);
        List<ResourceEntity> resourceEntities = null;
        if (Objects.nonNull(roleIds)) {
            List<Integer> roleIdList = roleIds.stream()
                    .map(item -> Integer.parseInt(String.valueOf(item))).collect(Collectors.toList());
            List<RoleResourceEntity> roleResourceEntities = roleResourceRepository.findAllByRoleIdIn(roleIdList);
            if (Objects.nonNull(roleResourceEntities)) {
                List<Integer> resourceIds = roleResourceEntities.parallelStream()
                        .map(RoleResourceEntity::getResourceId).distinct().collect(Collectors.toList());
                if (Objects.nonNull(resourceIds)) {
                    resourceEntities = permissionRepository.findAllByIdInAndIsDeleteIs(resourceIds, "N");
                }
            }
        }
        // 过滤提供其他平台使用的外部资源
        Node root = resouceBiz.acquirePermissions(resourceEntities, true);
        List<Node> filterNodes = Optional.ofNullable(root.getChildren()).orElse(new ArrayList<>()).stream()
                .filter(node -> Objects.equals(node.getBelongs(), "internal")).collect(Collectors.toList());
        root.setChildren(filterNodes);

        return root;
    }

    @Override
    public List<Integer> findUserIdsByResource(int id) {
        // 当前变更资源影响到的所有角色
        Optional<List<RoleResourceEntity>> roleResourceEntities = roleResourceRepository.findAllByResourceId(id);

        List<Integer> roleIds = roleResourceEntities.orElse(new ArrayList<>()).stream()
                .map(RoleResourceEntity::getRoleId).collect(Collectors.toList());
        if (!roleIds.isEmpty()) {
            List<Object> userIds = userRoleRepository.findAllByRoleIdAndRoleInUse(roleIds);
            // 转换去重
            List<Integer> iUserIds = Optional.ofNullable(userIds).orElse(new ArrayList<>()).stream()
                    .map(item -> Integer.parseInt(String.valueOf(item))).distinct().collect(Collectors.toList());
            return iUserIds;
        }
        return new ArrayList<>();
    }
}
