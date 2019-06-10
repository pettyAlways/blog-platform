package org.yingzuidou.cms.cmsweb.dto;

import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;

import java.util.List;
import java.util.Objects;

/**
 * UserDTO
 *
 * @author shangguanls
 * @date 2018/9/24
 */
public class UserDTO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户名密码
     */
    private String userPassword;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 部门ID
     */
    private Integer userDepart;

    /**
     * 用户列表
     */
    private List<CmsUserEntity> users;

    /**
     * 用户关联的角色
     */
    private List<Integer> roles;

    /**
     * 资源树
     */
    private Node resourceTree;

    public List<CmsUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<CmsUserEntity> users) {
        this.users = users;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Integer getUserDepart() {
        return userDepart;
    }

    public void setUserDepart(Integer userDepart) {
        this.userDepart = userDepart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Node getResourceTree() {
            return resourceTree;
    }

    public void setResourceTree(Node resourceTree) {
        this.resourceTree = resourceTree;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) &&
                Objects.equals(userName, userDTO.userName) &&
                Objects.equals(userAccount, userDTO.userAccount) &&
                Objects.equals(userDepart, userDTO.userDepart) &&
                Objects.equals(users, userDTO.users);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, userAccount, userDepart, users);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userDepart=" + userDepart +
                ", users=" + users +
                '}';
    }
}
