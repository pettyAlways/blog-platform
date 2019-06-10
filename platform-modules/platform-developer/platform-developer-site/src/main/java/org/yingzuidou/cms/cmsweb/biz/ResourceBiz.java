package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.constant.InUseEnum;
import org.yingzuidou.cms.cmsweb.core.cache.CmsCacheManager;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.PermissionRepository;
import org.yingzuidou.cms.cmsweb.dao.RoleResourceRepository;
import org.yingzuidou.cms.cmsweb.dto.PermissionDTO;
import org.yingzuidou.cms.cmsweb.entity.QResourceEntity;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 资源业务类
 *
 * @author 鹰嘴豆
 * @date 2018/9/27     
 */
@Service
public class ResourceBiz {

    @Autowired
    private PermissionRepository permissionRepository;

    private QResourceEntity qResourceEntity = QResourceEntity.resourceEntity;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    /**
     * 缓存管理类
     */
    @Autowired
    private CmsCacheManager cmsCacheManager;

    public Page<ResourceEntity> findAllResourceWithCondition(PermissionDTO permissionDTO, Pageable pageable) {
        BooleanExpression expression = qResourceEntity.isDelete.eq("N").and(qResourceEntity.parentId
                .eq(permissionDTO.getParentId()));
        if (!StringUtils.isEmpty(permissionDTO.getResourceName())) {
            expression = expression.and(qResourceEntity.resourceName.like("%" + permissionDTO.getResourceName() + "%"));
        }
        return permissionRepository.findAll(expression, pageable);
    }

    /**
     * 获取所有的资源树
     * 资源管理查询所有未删除的资源放到这个方法中组装成资源树
     * 用户加载授权的可用资源列表放到这个方法中组装成资源树
     *
     * @param flatNode 资源列表
     * @param skipNonUse 跳过不可用资源（资源管理需要查询所有可用和不可用资源）
     * @return 资源树节点
     */
    public Node acquirePermissions(List<ResourceEntity> flatNode, Boolean skipNonUse) {
        Node root = new Node();
        // 从系统常量中获取标题
        Map<String, String> systemParams = cmsCacheManager.systemConst();
        String rootResource = systemParams.get("root_resource");
        if (Objects.isNull(rootResource)) {
            rootResource = "Cms资源平台";
        }
        root.setName(rootResource);
        root.setId(-1);
        root.setType("root");
        getChildrenList(root, flatNode, skipNonUse);
        return root;
    }


    /**
     * 根据当前父节点查询所有子资源并且递归子资源
     * 如果当前的父节点资源不可用就不需要查询子资源
     *
     * @param parentNode 父节点资源
     * @param allNode 所有资源
     * @param skipNonUse 跳过不可用的资源
     */
    private void getChildrenList(Node parentNode, List<ResourceEntity> allNode, Boolean skipNonUse) {
        List<ResourceEntity> allNodeList = Optional.ofNullable(allNode).orElse(new ArrayList<>());
        List<Node> childrenNodeList = allNodeList.stream()
                // 找出所有可用的资源
                .filter(node -> parentNode.getId().equals(node.getParentId())
                        && (!skipNonUse || InUseEnum.USE.getValue().equals(node.getInUse())))
                .map(node -> {
                    // Entity -> DTO 转化页面所需的信息
                    Node child = new Node();
                    child.setId(node.getId());
                    child.setName(node.getResourceName());
                    child.setIcon(node.getResourceIcon());
                    child.setType(node.getResourceType());
                    child.setSort(node.getResourceSort());
                    child.setPath(node.getResourcePath());
                    child.setAlias(node.getAlias());
                    child.setDefaultPage(node.getDefaultPage());
                    return child;
                })
                .collect(Collectors.toList());
        parentNode.setChildren(childrenNodeList);

        // 孩子列表获取出来可能是空，因此需要用Optional处理
        Optional.ofNullable(childrenNodeList).orElse(new ArrayList<>())
                .forEach(node -> getChildrenList(node, allNode, skipNonUse));

    }
}
