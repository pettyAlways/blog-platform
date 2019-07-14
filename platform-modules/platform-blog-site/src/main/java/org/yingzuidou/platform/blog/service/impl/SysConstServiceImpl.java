package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.blog.dao.CmsConstRepository;
import org.yingzuidou.platform.blog.service.SysConstService;
import org.yingzuidou.platform.common.constant.InUseEnum;
import org.yingzuidou.platform.common.entity.CmsConstEntity;

import java.util.*;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/13
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class SysConstServiceImpl implements SysConstService {

    @Autowired
    private CmsConstRepository cmsConstRepository;

    @Cacheable(value="constCache", key="'const_key_'+#type")
    @Override
    public Map<String, String> listSystemConst(String type) {
        Map<String, String> systemParams = new HashMap<>(100);
        List<CmsConstEntity> cmsConstEntities = cmsConstRepository.findAllByTypeAndInUse(type, InUseEnum.USE.getValue());
        Optional.ofNullable(cmsConstEntities).orElse(new ArrayList<>())
                .forEach(item -> systemParams.put(item.getConstKey(), item.getConstValue()));
        return systemParams;
    }
}
