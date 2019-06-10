package org.yingzuidou.cms.cmsweb.service;

import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.OrganizationDTO;
import org.yingzuidou.cms.cmsweb.entity.OrganizationEntity;

/**
 * 组织机构接口
 *
 * @author yingzuidou
 * @date 2018/9/13
 */
public interface OrganizationService {

    OrganizationDTO list(OrganizationDTO organizationDTO, PageInfo pageInfo);

    void save(OrganizationEntity organizationEntity);

    void update(OrganizationEntity organizationEntity);

    OrganizationDTO listTree();

    void save(OrganizationDTO organizationDTO);

    void delete(Integer[] delIds);

    void update(OrganizationDTO organizationDTO);
}
