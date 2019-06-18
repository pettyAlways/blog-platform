package org.yingzuidou.cms.cmsweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yingzuidou.cms.cmsweb.biz.ConstBiz;
import org.yingzuidou.cms.cmsweb.constant.InUseEnum;
import org.yingzuidou.cms.cmsweb.core.exception.BusinessException;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.core.utils.CmsBeanUtils;
import org.yingzuidou.cms.cmsweb.core.utils.CmsCommonUtil;
import org.yingzuidou.cms.cmsweb.dao.CmsConstRepository;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;

import java.util.*;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2018/11/17
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
@Transactional
public class ConstServiceImpl implements ConstService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConstBiz constBiz;

    @Autowired
    private CmsConstRepository cmsConstRepository;

    @Override
    public ConstDTO list(ConstDTO constDTO, PageInfo pageInfo) {
        Page<CmsConstEntity> cmsConstEntities = constBiz.findConstWithCondition(constDTO, pageInfo.toPageable());
        pageInfo.setCounts(cmsConstEntities.getTotalElements());
        constDTO.setConstList(cmsConstEntities.getContent());
        return constDTO;
    }

    /**
     * 保存一个常量
     * 每次保存时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value="resourceCache", key="'resourceTree'",
                    condition="#cmsConstEntity.type.equals('1')&&#cmsConstEntity.constKey.equals('root_resource')"),
            @CacheEvict(value="constCache", key="'const_key_'+#cmsConstEntity.type") })
    public void save(CmsConstEntity cmsConstEntity) {
        cmsConstEntity.setCreator(CmsCommonUtil.getCurrentLoginUserId());
        cmsConstEntity.setCreateTime(new Date());
        cmsConstRepository.save(cmsConstEntity);
    }

    /**
     * 更新一个常量，需要根据主键获取实例在保存
     * 每次更新时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     * springEL需要加上#表示对象的引用，如果是常量是平台的根目录则需要清空resourceTree
     * 的缓存
     *
     * @param cmsConstEntity 常量内容
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value="resourceCache", key="'resourceTree'",
                    condition="#cmsConstEntity.type.equals('1')&&#cmsConstEntity.constKey.equals('root_resource')"),
            @CacheEvict(value="constCache", key="'const_key_'+#cmsConstEntity.type") })
    public void update(CmsConstEntity cmsConstEntity) {
        Optional<CmsConstEntity> constEntityOp = cmsConstRepository.findById(cmsConstEntity.getId());
        if (!constEntityOp.isPresent()) {
            throw new BusinessException("该常量已经被删除");
        }
        CmsConstEntity dbCmsConstEntity = constEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(cmsConstEntity, dbCmsConstEntity);
        dbCmsConstEntity.setUpdator(CmsCommonUtil.getCurrentLoginUserId());
        dbCmsConstEntity.setUpdateTime(new Date());
        cmsConstRepository.save(dbCmsConstEntity);
    }

    /**
     * 批量删除常量，硬删除不可恢复
     * 每次删除时需要更新缓存中的系统常量，因此这里移除constCache的
     * key为const_key_1表示系统常量的缓存
     * springEL需要加上#表示对象的引用，如果是常量是平台的根目录则需要清空resourceTree
     *
     * @param ids 常量ID以逗号隔开
     * @param type 常量类型为缓存做清空
     * @param rootResourceId 常量键为root_resource的Id
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value="resourceCache", key="'resourceTree'",
                    condition="#type.equals('1')&&#ids.contains(#rootResourceId)"),
            @CacheEvict(value="constCache", key="'const_key_'+#type") })
    public void delete(List<Integer> ids, String type, Integer rootResourceId) {
        cmsConstRepository.deleteAllByIdIn(ids);
    }

    /**
     * 获取指定类型的常量并以key为cache_key_1缓存在constCache中
     * 查找所有启用的常量
     *
     * @param type 1、2等表示系统常量、枚举
     * @return 从缓存或者数据库获取到的常量列表
     */
    @Override
    @Cacheable(value="constCache", key="'const_key_'+#type")
    public List<CmsConstEntity> findAllConstByType(String type) {
        logger.info("缓存未命中");
        return cmsConstRepository.findAllByTypeAndInUse(type, InUseEnum.USE.getValue());
    }

    @Override
    public CmsConstEntity findRootResource(String rootResource) {
        return cmsConstRepository.findByConstKey(rootResource);
    }

    /**
     *  查询缓存中的系统常量，如果没在缓存没命中则从数据库查询
     *
     * @return 系统常量列表
     */
    @Override
    public Map<String, String> systemConst() {
        Map<String, String> systemParams = new HashMap<>(100);
        List<CmsConstEntity> constEntities = findAllConstByType("1");
        Optional.ofNullable(constEntities).orElse(new ArrayList <>())
                .forEach(item -> systemParams.put(item.getConstKey(), item.getConstValue()));
        return systemParams;
    }
}
