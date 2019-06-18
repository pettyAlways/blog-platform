package org.yingzuidou.cms.cmsweb.core.vo;

import java.util.List;

/**
 * 资源树节点
 *
 * @author 鹰嘴豆
 * @date 2018/9/27     
 */
public class Node {

    /**
     * 节点树ID
     */
    private Integer id;

    /**
     * 资源名字
     */
    private String name;

    /**
     * 资源地址
     */
    private String path;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 资源别名
     */
    private String alias;

    private Integer sort;

    private String defaultPage;

    private String belongs;

    /**
     * 资源子集
     */
    private List<Node> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
    }

    public String getBelongs() {
        return belongs;
    }

    public void setBelongs(String belongs) {
        this.belongs = belongs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (!name.equals(node.name)) return false;
        if (!type.equals(node.type)) return false;
        if (!icon.equals(node.icon)) return false;
        if (!alias.equals(node.alias)) return false;
        return children.equals(node.children);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + alias.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", icon='" + icon + '\'' +
                ", alias='" + alias + '\'' +
                ", children=" + children +
                '}';
    }
}
