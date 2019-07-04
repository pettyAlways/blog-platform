package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.constant.InUseEnum;
import org.yingzuidou.platform.blog.constant.IsDeleteEnum;
import org.yingzuidou.platform.blog.dao.CategoryRepository;
import org.yingzuidou.platform.blog.service.CategoryService;
import org.yingzuidou.platform.common.entity.CategoryEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.*;

/**
 * 类功能描述
 * 分类服务实现类，做分类常见的增删改查功能操作，这部分功能是提供用户操作的，所以需要考虑数据的安全性与可恢复性
 *
 * <p><note>今天2019年7月3号是我三十而立de日子，时间过得真快啊</note>
 * @author 鹰嘴豆
 * @date 2019/7/3
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 获取所有的分类包括不可用资源
     *
     * @return 所有分类集合
     */
    @Override
    public List<CategoryEntity> searchCategory() {
        return categoryRepository.findAllWithCreatorName();
    }

    /**
     * <p>根据分类ID删除分类，由于是共享分类，所以需要采用软删除的方式，保证数据可还原,还原操作在开发者平台完成、
     * <p>需要记录操作日志，这部分在后面完善，操作日志在开发者平台完成
     *
     * @param categoryId 分类ID
     */
    @Override
    public void deleteCategory(Integer categoryId) {
        if (Objects.isNull(categoryId)) {
            throw new BusinessException("分类ID不能为空");
        }
        Optional<CategoryEntity> categoryEntityOp = categoryRepository.findById(categoryId);
        if (!categoryEntityOp.isPresent()) {
            throw new BusinessException("指定分类[" + categoryId+ "]不存在");
        }
        CategoryEntity categoryEntity = categoryEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        categoryEntity.setUpdator(user.getId());
        categoryEntity.setUpdateTime(new Date());
        categoryEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void updateCategory(CategoryEntity categoryEntity) {
        if (Objects.isNull(categoryEntity)) {
            throw new BusinessException("请传递完整的分类信息");
        }
        Optional<CategoryEntity> categoryEntityOp = categoryRepository.findById(categoryEntity.getId());
        if (!categoryEntityOp.isPresent()) {
            throw new BusinessException("需要更新分类不存在");
        }

        CategoryEntity target = categoryEntityOp.get();
        CmsBeanUtils.copyMorNULLProperties(categoryEntity, target);

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        target.setUpdator(user.getId());
        target.setUpdateTime(new Date());
        target.setIsDelete(IsDeleteEnum.DELETE.getValue());
        categoryRepository.save(target);


    }

    /**
     * 根据前端页面填写的分类信息完成一条分类的新增, 如果需要新增的分类无论是否启用它已经存在数据库，则不能新增
     *
     * @param categoryEntity 需要新增的分类对象
     */
    @Override
    public void insertCategory(CategoryEntity categoryEntity) {
        if (Objects.isNull(categoryEntity)) {
            throw new BusinessException("请传递完整的分类信息");
        }
        List<CategoryEntity> categoryEntities = categoryRepository
                .findAllByCategoryName(categoryEntity.getCategoryName());

        if (categoryEntities.size() > 0) {
            if (Objects.equals(categoryEntities.get(0).getInUse(), InUseEnum.NONUSE.getValue())) {
                throw new BusinessException("分类名已存在请启用即可");
            }
            throw new BusinessException("分类名已存在");
        }

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        categoryEntity.setCreator(user.getId());
        categoryEntity.setCreateTime(new Date());
        categoryRepository.save(categoryEntity);
    }
}
