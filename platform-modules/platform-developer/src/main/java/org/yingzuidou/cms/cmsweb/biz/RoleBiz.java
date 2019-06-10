package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.RoleRepository;
import org.yingzuidou.cms.cmsweb.dto.RoleDTO;
import org.yingzuidou.cms.cmsweb.entity.QRoleEntity;
import org.yingzuidou.cms.cmsweb.entity.RoleEntity;


/**
 * RoleBiz
 *
 * @author 鹰嘴豆
 * @date 2018/9/24
 */
@Service
public class RoleBiz {

    private QRoleEntity qRoleEntity = QRoleEntity.roleEntity;

    @Autowired
    private RoleRepository roleRepository;

    public Page<RoleEntity> findAllRoleWithCondition(RoleDTO roleDTO, Pageable pageable) {
        BooleanExpression expression = qRoleEntity.isDelete.eq("N");
        // 动态表单域查询
        if (!StringUtils.isEmpty(roleDTO.getRoleName())) {
            expression = expression.and(qRoleEntity.roleName.like("%" + roleDTO.getRoleName() + "%"));
        }
        if (!StringUtils.isEmpty(roleDTO.getInUse())) {
            expression = expression.and(qRoleEntity.inUse.eq(roleDTO.getInUse()));
        }
        return roleRepository.findAll(expression, pageable);
    }

}
