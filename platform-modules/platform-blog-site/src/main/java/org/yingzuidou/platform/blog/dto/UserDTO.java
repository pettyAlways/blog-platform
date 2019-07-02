package org.yingzuidou.platform.blog.dto;

import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.vo.Node;

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
     * 资源树
     */
    private Node resourceTree;

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
                Objects.equals(userPassword, userDTO.userPassword) &&
                Objects.equals(userAccount, userDTO.userAccount) &&
                Objects.equals(userDepart, userDTO.userDepart) &&
                Objects.equals(resourceTree, userDTO.resourceTree);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, userPassword, userAccount, userDepart, resourceTree);
    }
}
