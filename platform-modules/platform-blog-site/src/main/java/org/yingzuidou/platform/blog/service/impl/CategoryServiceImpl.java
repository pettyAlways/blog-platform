package org.yingzuidou.platform.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yingzuidou.platform.auth.client.core.util.ThreadStorageUtil;
import org.yingzuidou.platform.blog.constant.InUseEnum;
import org.yingzuidou.platform.blog.constant.IsDeleteEnum;
import org.yingzuidou.platform.blog.dao.CategoryRepository;
import org.yingzuidou.platform.blog.dao.OperRecordRepository;
import org.yingzuidou.platform.blog.dao.UserRepository;
import org.yingzuidou.platform.blog.dto.CategoryDTO;
import org.yingzuidou.platform.blog.service.CategoryService;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.MessageService;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.blog.websocket.BlogSocket;
import org.yingzuidou.platform.common.constant.*;
import org.yingzuidou.platform.common.entity.CategoryEntity;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.exception.BusinessException;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 类功能描述
 * 分类服务实现类，做分类常见的增删改查功能操作，这部分功能是提供用户操作的，所以需要考虑数据的安全性与可恢复性
 *
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperRecordService operRecordService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 获取所有的分类包括不可用资源,jpa的多表查询结果转换成CategoryDTO非常的麻烦，因此这里暂时使用一次一个查询
     *
     * @return 所有分类集合
     */
    @Override
    public List<CategoryDTO> searchCategory() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByIsDelete(IsDeleteEnum.NOTDELETE.getValue());
        return categoryEntities.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            CmsBeanUtils.copyMorNULLProperties(category, categoryDTO);
            Optional<CmsUserEntity> cmsUserEntityOp = userRepository.findById(category.getCreator());
            cmsUserEntityOp.ifPresent(cmsUserEntity -> categoryDTO.setUserName(cmsUserEntity.getUserName()));
            return categoryDTO;
        }).collect(Collectors.toList());
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
        if (!Objects.equals(categoryEntity.getCreator(), user.getId())) {
            throw new BusinessException("没有权限删除其他人的分类");
        }
        if (knowledgeService.stillUsingCategory(categoryId)) {
            throw new BusinessException("分类还被其他知识库引用，请先修改这些知识库的分类");
        }
        categoryEntity.setUpdator(user.getId());
        categoryEntity.setUpdateTime(new Date());
        categoryEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        categoryRepository.save(categoryEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.DEL.getValue(), ObjTypeEnum.CATEGORY.getValue(),
                null, RootEnum.CATEGORY.getValue(), categoryEntity.getCategoryName());
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

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        CategoryEntity target = categoryEntityOp.get();
        if (!Objects.equals(target.getCreator(), user.getId())) {
            throw new BusinessException("没有权限修改其他人的分类");
        }

        CmsBeanUtils.copyMorNULLProperties(categoryEntity, target);
        target.setUpdator(user.getId());
        target.setUpdateTime(new Date());
        categoryRepository.save(target);
        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.CATEGORY.getValue(),
                target.getId(), RootEnum.CATEGORY.getValue(), target.getCategoryName());
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
                .findAllByCategoryNameAndIsDelete(categoryEntity.getCategoryName(), IsDeleteEnum.NOTDELETE.getValue());

        if (categoryEntities.size() > 0) {
            if (Objects.equals(categoryEntities.get(0).getInUse(), InUseEnum.NONUSE.getValue())) {
                throw new BusinessException("分类名已存在请启用即可");
            }
            throw new BusinessException("分类名已存在");
        }

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        categoryEntity.setCreator(user.getId());
        categoryEntity.setCreateTime(new Date());
        categoryEntity = categoryRepository.save(categoryEntity);
        operRecordService.recordCommonOperation(user, OperTypeEnum.ADD.getValue(), ObjTypeEnum.CATEGORY.getValue(),
                categoryEntity.getId(), RootEnum.CATEGORY.getValue(), "");

    }

    @Override
    public void shareDeleteCategory(Integer categoryId) {
        if (Objects.isNull(categoryId)) {
            throw new BusinessException("分类ID不能为空");
        }
        Optional<CategoryEntity> categoryEntityOp = categoryRepository.findById(categoryId);
        if (!categoryEntityOp.isPresent()) {
            throw new BusinessException("指定分类[" + categoryId+ "]不存在");
        }
        if (knowledgeService.stillUsingCategory(categoryId)) {
            throw new BusinessException("分类还被其他知识库引用，请先修改这些知识库的分类");
        }
        CategoryEntity categoryEntity = categoryEntityOp.get();
        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        categoryEntity.setUpdator(user.getId());
        categoryEntity.setUpdateTime(new Date());
        categoryEntity.setIsDelete(IsDeleteEnum.DELETE.getValue());
        categoryRepository.save(categoryEntity);
        if (!Objects.equals(categoryEntity.getCreator(), user.getId())) {
            noticeKnowledgeDel(categoryEntity, user, categoryEntity.getCreator());
        }
        operRecordService.recordCommonOperation(user, OperTypeEnum.DEL.getValue(), ObjTypeEnum.CATEGORY.getValue(),
                null, RootEnum.CATEGORY.getValue(), categoryEntity.getCategoryName());
    }

    @Override
    public void updateShareCategory(CategoryEntity categoryEntity) {
        if (Objects.isNull(categoryEntity)) {
            throw new BusinessException("请传递完整的分类信息");
        }
        Optional<CategoryEntity> categoryEntityOp = categoryRepository.findById(categoryEntity.getId());
        if (!categoryEntityOp.isPresent()) {
            throw new BusinessException("需要更新分类不存在");
        }

        CmsUserEntity user = (CmsUserEntity) ThreadStorageUtil.getItem("user");
        CategoryEntity target = categoryEntityOp.get();

        CmsBeanUtils.copyMorNULLProperties(categoryEntity, target);
        target.setUpdator(user.getId());
        target.setUpdateTime(new Date());
        categoryRepository.save(target);
        if (!Objects.equals(target.getCreator(), user.getId())) {
            noticeKnowledgeEdit(target, user, target.getCreator());
        }
        operRecordService.recordCommonOperation(user, OperTypeEnum.EDIT.getValue(), ObjTypeEnum.CATEGORY.getValue(),
                target.getId(), RootEnum.CATEGORY.getValue(), target.getCategoryName());
    }

    private void noticeKnowledgeDel(CategoryEntity categoryEntity, CmsUserEntity opUser, Integer userId) {
        String message = String.format("%s删除了您创建的分类[%s]", opUser.getUserName(),
                categoryEntity.getCategoryName());
        messageService.addMessage(MessageTypeEnum.CATEGORYDEL.getValue(), message, userId);
        BlogSocket.sendSpecifyUserMsg(userId, WebSocketTypeEnum.NOTICE, "new");
    }

    private void noticeKnowledgeEdit(CategoryEntity categoryEntity, CmsUserEntity opUser, Integer userId) {
        String message = String.format("%s修改了您创建的分类[%s]的信息", opUser.getUserName(),
                categoryEntity.getCategoryName());
        messageService.addMessage(MessageTypeEnum.CATEGORYEDIT.getValue(), message, userId);
        BlogSocket.sendSpecifyUserMsg(userId, WebSocketTypeEnum.NOTICE, "new");
    }
}
