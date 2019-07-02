package org.yingzuidou.platform.blog.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.constant.InUseEnum;
import org.yingzuidou.platform.blog.dao.ResourceRepository;
import org.yingzuidou.platform.blog.dao.RoleResourceRepository;
import org.yingzuidou.platform.common.entity.QResourceEntity;
import org.yingzuidou.platform.common.entity.ResourceEntity;
import org.yingzuidou.platform.common.vo.Node;

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
    private ResourceRepository resourceRepository;

    private QResourceEntity qResourceEntity = QResourceEntity.resourceEntity;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

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
        root.setName("博客资源");
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
                    child.setBelongs(node.getBelongs());
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
