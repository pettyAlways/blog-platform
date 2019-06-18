package org.yingzuidou.cms.cmsweb.biz;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yingzuidou.cms.cmsweb.dao.CmsConstRepository;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.entity.QCmsConstEntity;


/**
 * 类功能描述
 * 常量配置更加细化的接口，方便其他地方复用
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 * 
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1.0        常量细化接口方便其他地方复用
 * 2018/11/16     鹰嘴豆        v1.0        增加ehcache作为常量的缓存框架
 */

@Service
public class ConstBiz {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 常量配置表JPA接口
     */
    @Autowired
    private CmsConstRepository cmsConstRepository;

    /**
     * JPA QueryDsl：常量复杂查询
     */
    private QCmsConstEntity qCmsConstEntity = QCmsConstEntity.cmsConstEntity;


    public Page<CmsConstEntity> findConstWithCondition(ConstDTO constDTO, Pageable pageable) {
        BooleanExpression expression = qCmsConstEntity.type.eq(constDTO.getType());
        if (!StringUtils.isEmpty(constDTO.getConstName())) {
            expression = expression.and(qCmsConstEntity.constName.like("%" + constDTO.getConstName() + "%"));
        }
        if (!StringUtils.isEmpty(constDTO.getConstKey())) {
            expression = expression.and(qCmsConstEntity.constKey.like("%" + constDTO.getConstKey() + "%"));
        }
        return cmsConstRepository.findAll(expression, pageable);
    }

}
