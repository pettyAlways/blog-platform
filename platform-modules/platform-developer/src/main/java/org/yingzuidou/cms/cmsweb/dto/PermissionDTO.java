package org.yingzuidou.cms.cmsweb.dto;

import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.entity.ResourceEntity;

import java.util.List;
import java.util.Objects;

/**
 * PermissionDTO
 *
 * @author shangguanls
 * @date 2018/9/26
 */
public class PermissionDTO {

    /**
     * 父资源
     */
    private Integer parentId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源集合
     */
    private List<ResourceEntity> resources;

    /**
     * 资源树
     */
    private Node tree;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public List<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(List<ResourceEntity> resources) {
        this.resources = resources;
    }

    public Node getTree() {
        return tree;
    }

    public void setTree(Node tree) {
        this.tree = tree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionDTO that = (PermissionDTO) o;
        return Objects.equals(parentId, that.parentId) &&
                Objects.equals(resourceName, that.resourceName) &&
                Objects.equals(resources, that.resources);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parentId, resourceName, resources);
    }

    @Override
    public String toString() {
        return "PermissionDTO{" +
                "parentId=" + parentId +
                ", resourceName='" + resourceName + '\'' +
                ", resources=" + resources +
                '}';
    }
}
