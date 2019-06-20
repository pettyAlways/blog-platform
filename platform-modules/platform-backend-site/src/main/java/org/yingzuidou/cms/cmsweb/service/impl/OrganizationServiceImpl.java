package org.yingzuidou.cms.cmsweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.OrganizationBiz;
import org.yingzuidou.cms.cmsweb.dao.OrganizationRepository;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.service.OrganizationService;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.OrganizationEntity;
import org.yingzuidou.platform.common.paging.PageInfo;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.CmsCommonUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 组织机构实现类
 *
 * @author yingzuidou
 * @date 2018/9/13     
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class OrganizationServiceImpl implements OrganizationService{

    private final OrganizationBiz organizationBiz;

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationBiz organizationBiz, OrganizationRepository organizationRepository) {
        this.organizationBiz = organizationBiz;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public OrganizationDTO list(OrganizationDTO organizationDTO, PageInfo pageInfo) {
        // 查找父级资源信息
        Optional<OrganizationEntity> curNode = organizationBiz.findOrgById(organizationDTO.getParentId());
        // 查找父级下的子资源
        Page<OrganizationEntity> orgPage = organizationBiz.findAllSubOrganizationWithCondition(organizationDTO, pageInfo.toPageable());
        if (curNode.isPresent()) {
            OrganizationEntity organization = curNode.get();
            organizationDTO.setId(organization.getId());
            organizationDTO.setLabel(organization.getOrgName());
            organizationDTO.setExpand(organization.getExpand());
            organizationDTO.setChildrenEntityList(orgPage.getContent());
        }
        pageInfo.setCounts(orgPage.getTotalElements());
        return organizationDTO;
    }

    @Override
    public void save(OrganizationEntity organizationEntity) {
        organizationEntity.setCreator(1);
        organizationEntity.setCreateTime(new Date());
        organizationEntity.setIsDelete("N");
        organizationRepository.save(organizationEntity);
    }

    @Override
    public void update(OrganizationEntity organizationEntity) {
        organizationEntity.setUpdateTime(new Date());
        Optional<OrganizationEntity> entity = organizationRepository.findById(organizationEntity.getId());
        OrganizationEntity updateEntity = entity.orElse(organizationEntity);
        CmsBeanUtils.copyMorNULLProperties(organizationEntity, updateEntity);
        organizationRepository.save(updateEntity);
    }

    @Override
    public OrganizationDTO listTree() {
        // 先找出所有的扁平组织数据
        List<OrganizationEntity> flatNode = organizationRepository.findAllByIsDeleteIs("N");
        CmsUserEntity userEntity = CmsCommonUtil.getCurrentLoginUser();
        OrganizationEntity parent = flatNode.stream()
                .filter(node -> userEntity.getUserDepart().equals(node.getId())).findFirst().get();
        OrganizationDTO parentDTO = new OrganizationDTO();
        parentDTO.setId(parent.getId());
        parentDTO.setLabel(parent.getOrgName());
        getChildrenList(parentDTO, Optional.ofNullable(flatNode));
        return parentDTO;
    }

    @Override
    public void save(OrganizationDTO organizationDTO) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        CmsBeanUtils.copyProperties(organizationDTO, organizationEntity);
        organizationEntity.setCreator(1);
        organizationEntity.setCreateTime(new Date());
        organizationEntity.setIsDelete("N");
        organizationRepository.save(organizationEntity);
    }

    @Override
    public void delete(Integer[] delIds) {
        organizationBiz.deleteOrgByIds(delIds);
    }

    @Override
    public void update(OrganizationDTO organizationDTO) {
        Optional<OrganizationEntity> optionEntity = organizationBiz.findById(organizationDTO.getId());
        OrganizationEntity entity = optionEntity.get();
        CmsBeanUtils.copyMorNULLProperties(organizationDTO, entity);
        entity.setUpdator(1);
        entity.setUpdateTime(new Date());
        organizationRepository.save(entity);

    }

    private void getChildrenList(OrganizationDTO parentNode, Optional<List<OrganizationEntity>> allNode) {
        List<OrganizationEntity> allNodeList = allNode.orElse(new ArrayList<>());
        List<OrganizationDTO> childrenNodeList = allNodeList.stream()
                .filter(node -> parentNode.getId().equals(node.getParentId()))
                .map(node -> {
                    // Entity -> DTO 转化页面所需的信息
                    OrganizationDTO curOrganizationDTO = new OrganizationDTO();
                    curOrganizationDTO.setId(node.getId());
                    curOrganizationDTO.setLabel(node.getOrgName());
                    curOrganizationDTO.setExpand(node.getExpand());
                    return curOrganizationDTO;
                })
                .collect(Collectors.toList());
        parentNode.setChildren(childrenNodeList);

        // 孩子列表获取出来可能是空，因此需要用Optional处理
        Optional.ofNullable(childrenNodeList).orElse(new ArrayList<>())
                .forEach(node -> getChildrenList(node, allNode));
    }
}
