package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.vo.Node;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * UserDTO
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
@Data
@Accessors(chain = true)
public class UserDTO {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 是否是作者
     */
    private Boolean isAuthor;

    /**
     * 用户名密码
     */
    private String userPassword;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 用户性别
     */
    private String userSex;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 用户简介
     */
    private String introduce;

    /**
     * 用户职业
     */
    private String work;

    /**
     * 用户居住地
     */
    private String place;

    /**
     * 用户兴趣
     */
    private String hobby;

    /**
     * 用户技能
     */
    private List<UserSkillDTO> skillList;


    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 用户资料封面
     */
    private String coverUrl;

    /**
     * 用户个性签名
     */
    private String signature;

    /**
     * 部门ID
     */
    private Integer userDepart;

    /**
     * 第三方ID
     */
    private Integer thirdPartyId;

    /**
     * 第三方登录是否绑定账号
     */
    private String isBind;

    /**
     * 资源树
     */
    private Node resourceTree;


    public static Function<Object[], UserDTO> participantDetail = data -> new UserDTO()
            .setUserId(CmsBeanUtils.objectToInt(data[0])).setUserName(CmsBeanUtils.objectToString(data[1]));

}
