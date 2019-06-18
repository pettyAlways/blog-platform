package org.yingzuidou.cms.cmsweb.service.impl;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.UserBiz;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.core.vo.Node;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsUserEntity;
import org.yingzuidou.cms.cmsweb.entity.UserRoleEntity;
import org.yingzuidou.cms.cmsweb.service.PermissionService;
import org.yingzuidou.cms.cmsweb.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * UserServiceImpl
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserBiz userBiz;

    private final UserRepository userRepository;

    private final PermissionService permissionService;

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserBiz userBiz, UserRepository userRepository, PermissionService permissionService, UserRoleRepository userRoleRepository) {
        this.userBiz = userBiz;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDTO list(UserDTO userDTO, PageInfo pageInfo) {
        Page<CmsUserEntity> orgPage = userBiz.findAllUserWithCondition(userDTO, pageInfo.toPageable());
        pageInfo.setCounts(orgPage.getTotalElements());
        userDTO.setUsers(orgPage.getContent());
        return userDTO;
    }

    /**
     * 新增一个新的用户，密码使用md5加盐值为uuid进行加密
     *
     * @param cmsUserEntity 用户实体类
     */
    @Override
    public void save(CmsUserEntity cmsUserEntity) {
        CmsUserEntity user = userBiz.findByUserAccount(cmsUserEntity.getUserAccount());
        if (!Objects.isNull(user)) {
            throw new BusinessException("账户名已存在");
        }
        cmsUserEntity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
        cmsUserEntity.setCreateTime(new Date());
        cmsUserEntity.setIsDelete("N");
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        cmsUserEntity.setUuid(uuid);
        cmsUserEntity.setUserPassword(CmsCommonUtil.getMd5PasswordText(uuid, cmsUserEntity.getUserPassword()));
        userRepository.save(cmsUserEntity);
    }

    /**
     * 更新一个新的用户，密码使用md5加盐值为uuid进行加密
     * 更新前需要对比密码是否一致，如果一致则跳过加密
     * 每次更新uuid都在变因此得到的密文也在变
     *
     * @param userEntity 用户实体类
     */
    @Override
    public void update(CmsUserEntity userEntity) {
        CmsUserEntity hasUser = userBiz.existAccount(userEntity.getUserAccount(), userEntity.getId());
        if (!Objects.isNull(hasUser)) {
            throw new BusinessException("账户名已存在");
        }
        Optional<CmsUserEntity> optionEntity = userBiz.findById(userEntity.getId());
        CmsUserEntity entity = optionEntity.get();
        // 如果密码发生改变就需要加密
        if (!entity.getUserPassword().equals(userEntity.getUserPassword())) {
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String encryptPwd = CmsCommonUtil.getMd5PasswordText(uuid, userEntity.getUserPassword());
            userEntity.setUserPassword(encryptPwd);
            userEntity.setUuid(uuid);
        }
        CmsBeanUtils.copyMorNULLProperties(userEntity, entity);
        entity.setUpdator(CmsCommonUtil.getCurrentLoginUserId());
        entity.setUpdateTime(new Date());
        userRepository.save(entity);
    }

    @Override
    public void delete(Integer[] delIds) {
        userBiz.deleteUserByIds(delIds);
    }

    @Override
    public UserDTO userInfo() {
        UserDTO userDTO = new UserDTO();
        CmsUserEntity user = (CmsUserEntity)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        Node permissions = permissionService.acquireUserPermission(user.getId());
        userDTO.setResourceTree(permissions);
        return userDTO;
    }

    /**
     * 为指定用户授予角色，一个用户可以多角色
     * 给用户重新指定角色时需要清除指定用户的授权资源缓存
     *
     * @param userDTO 用户ID和角色ID列表
     */
    @Override
    @CacheEvict(value="resourceCache", key="'resourceTree_'+#userDTO.id")
    public void authUser(UserDTO userDTO) {
        userRoleRepository.deleteAllByUserId(userDTO.getId());
        List<UserRoleEntity> userRoleList = userDTO.getRoles().stream().map(item -> {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRoleId(item);
            userRole.setUserId(userDTO.getId());
            userRole.setCreateTime(new Date());
            userRole.setCreator(1);
            return userRole;
        }).collect(Collectors.toList());
        userRoleRepository.saveAll(userRoleList);
    }

    /**
     * 获取指定给用户的所有启用的角色
     * 如果没有启用的角色将被过滤
     *
     * @param id 用户ID
     * @return 赋予用户的角色ID列表
     */
    @Override
    public UserDTO acquireRoles(Integer id) {
        UserDTO userDTO = new UserDTO();
        List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(id);
        List<Integer> roleIdList = roleIds.stream()
                .map(item -> Integer.parseInt(String.valueOf(item))).collect(Collectors.toList());
        userDTO.setRoles(roleIdList);
        return userDTO;
    }
}
