package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.biz.ResourceBiz;
import org.yingzuidou.platform.blog.dao.ResourceRepository;
import org.yingzuidou.platform.blog.dao.RoleResourceRepository;
import org.yingzuidou.platform.blog.dao.UserRoleRepository;
import org.yingzuidou.platform.blog.service.ResourceService;
import org.yingzuidou.platform.common.entity.ResourceEntity;
import org.yingzuidou.platform.common.entity.RoleResourceEntity;
import org.yingzuidou.platform.common.vo.Node;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/6/24
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private ResourceBiz resourceBiz;



    /**
     * 获取外部资源的资源路径与路径所有角色的映射集合
     *
     * @return 资源路径与角色集合的映射
     */
    @Override
    public Map<String, List<String>> findResourceRoleMap() {
        Map<String, List<String>> authMap = new HashMap<>(20);
        List<Object> roleResources = resourceRepository.acquireRoleResources();
        Optional.ofNullable(roleResources).orElse(new ArrayList<>()).forEach(roleResource -> {
            Object[] objects = (Object[])roleResource;
            String[] roleNames = ((String) objects[1]).split(",");
            List<String> roleList = Arrays.asList(roleNames);
            authMap.put((String) objects[0], roleList);
        });
        return authMap;
    }

    @Override
    public Node acquireUserPermission(int userId) {
        List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(userId);
        List<ResourceEntity> resourceEntities = null;
        if (!Objects.isNull(roleIds)) {
            List<Integer> roleIdList = roleIds.stream()
                    .map(item -> Integer.parseInt(String.valueOf(item))).collect(Collectors.toList());
            List<RoleResourceEntity> roleResourceEntities = roleResourceRepository.findAllByRoleIdIn(roleIdList);
            if (Objects.nonNull(roleResourceEntities)) {
                List<Integer> resourceIds = roleResourceEntities.parallelStream()
                        .map(RoleResourceEntity::getResourceId).distinct().collect(Collectors.toList());
                if (Objects.nonNull(resourceIds)) {
                    resourceEntities = resourceRepository.findAllByIdInAndIsDeleteIs(resourceIds, "N");
                }
            }
        }
        // 过滤提供其他平台使用的外部资源
        Node root = resourceBiz.acquirePermissions(resourceEntities, true);
        List<Node> filterNodes = Optional.ofNullable(root.getChildren()).orElse(new ArrayList<>()).stream()
                .filter(node -> Objects.equals(node.getBelongs(), "external")).collect(Collectors.toList());
        root.setChildren(filterNodes);

        return root;
    }
}
