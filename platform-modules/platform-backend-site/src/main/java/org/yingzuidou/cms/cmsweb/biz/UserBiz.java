package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRepository;
import org.yingzuidou.cms.cmsweb.dao.UserRoleRepository;
import org.yingzuidou.cms.cmsweb.dto.UserDTO;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.QCmsUserEntity;
import org.yingzuidou.platform.common.entity.RoleEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UserBiz
 *
 * @author shangguanls
 * @date 2018/9/24
 */
@Service
public class UserBiz {

    private QCmsUserEntity qCmsUserEntity = QCmsUserEntity.cmsUserEntity;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Page<CmsUserEntity> findAllUserWithCondition(UserDTO userDTO, Pageable pageable) {
        BooleanExpression expression = qCmsUserEntity.isDelete.eq("N").and(qCmsUserEntity.userDepart.eq(userDTO.getUserDepart()));
        if (!StringUtils.isEmpty(userDTO.getUserName())) {
            expression = expression.and(qCmsUserEntity.userName.like("%" + userDTO.getUserName() + "%"));
        }
        if (!StringUtils.isEmpty(userDTO.getUserAccount())) {
            expression = expression.and(qCmsUserEntity.userAccount.like("%" + userDTO.getUserAccount() + "%"));
        }
        return userRepository.findAll(expression, pageable);
    }

    public void deleteUserByIds(Integer[] delIds) {
        Predicate predicate = qCmsUserEntity.id.in(delIds).and(qCmsUserEntity.isDelete.eq("N"));
        Iterable<CmsUserEntity> entities = userRepository.findAll(predicate);
        entities.forEach(item -> item.setIsDelete("Y"));
        userRepository.saveAll(entities);
    }

    public Optional<CmsUserEntity> findById(Integer id) {
        Predicate predicate = qCmsUserEntity.isDelete.eq("N").and(qCmsUserEntity.id.eq(id));
        return userRepository.findOne(predicate);
    }

    public CmsUserEntity findByUserAccount(String userAccount) {
       return userRepository.findByUserAccountAndIsDeleteIs(userAccount, "N");
    }

    public CmsUserEntity existAccount(String userAccount, int id) {
       return userRepository.findByUserAccountAndIdNotAndIsDelete(userAccount, id, "N");
    }

    public List<String> findRoleNameByUserId(Integer userId) {
        List<Object> roleIds = userRoleRepository.findAllByUserIdAndRoleInUse(userId);
        List<String> roleNames = null;
        if (Objects.nonNull(roleIds)) {
            List<Integer> roleIdList = roleIds.stream()
                    .map(item -> Integer.parseInt(String.valueOf(item))).collect(Collectors.toList());
            List<RoleEntity> roleEntities = roleRepository.findAllByIdInAndIsDeleteIs(roleIdList, "N");
            if (Objects.nonNull(roleEntities)) {
                roleNames = roleEntities.stream().map(RoleEntity::getRoleName).collect(Collectors.toList());
            }
        }
        return roleNames;
    }

    public List<CmsUserEntity> findUsersByIds(List<Integer> userIds) {
        return userRepository.findAllByIdInAndIsDelete(userIds, "N");
    }
}
