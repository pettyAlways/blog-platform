package org.yingzuidou.cms.cmsweb.biz;


import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;
import org.yingzuidou.cms.cmsweb.entity.QOrganizationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 组织业务实现类
 *
 * @author yingzuidou
 * @date 2018/9/18     
 */
@Service
public class OrganizationBiz {

    private final OrganizationRepository organizationRepository;

    private QOrganizationEntity qOrganizationEntity = QOrganizationEntity.organizationEntity;

    @Autowired
    public OrganizationBiz(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * 根据传入orgId查询未删除的实体类
     *
     * @param orgId  当前组织ID
     * @return
     */
    public Optional<OrganizationEntity> findOrgById(Integer orgId) {
        Predicate predicate = qOrganizationEntity.isDelete.eq("N").and(qOrganizationEntity.id.eq(orgId));
        return organizationRepository.findOne(predicate);
    }

    public Page<OrganizationEntity> findAllSubOrganizationWithCondition(OrganizationDTO organizationDTO, Pageable pageable) {
        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtils.isEmpty(organizationDTO.getOrgName())) {
            predicateList.add(qOrganizationEntity.orgName.like("%" + organizationDTO.getOrgName() + "%"));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        Predicate predicate = qOrganizationEntity.parentId.eq(organizationDTO.getParentId())
                .and(qOrganizationEntity.isDelete.eq("N")).andAnyOf(predicateList.toArray(predicates));
        return organizationRepository.findAll(predicate, pageable);
    }

    public void deleteOrgByIds(Integer[] delIds) {
        Predicate predicate = qOrganizationEntity.id.in(delIds).and(qOrganizationEntity.isDelete.eq("N"));
        Iterable<OrganizationEntity> entities = organizationRepository.findAll(predicate);
        entities.forEach(item -> item.setIsDelete("Y"));
        organizationRepository.saveAll(entities);
    }

    public Optional<OrganizationEntity> findById(Integer id) {
        Predicate predicate = qOrganizationEntity.isDelete.eq("N").and(qOrganizationEntity.id.eq(id));
        return organizationRepository.findOne(predicate);
    }
}
