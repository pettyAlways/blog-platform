package org.yingzuidou.cms.blog.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类功能描述
 * 博客系统资源管理 - 提供开发者平台调用且会通过Shiro进行鉴权。真正执行数据库操作是在博客后台服务中进行，需要通过feign
 * 进行消费
 *
 * @author 鹰嘴豆
 * @date 2019/6/10
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping(value="/blog/resource")
public class ResourceController {


    @GetMapping(value="/listResource.do")
    public CmsMap listPower() {
        CmsMap<Node> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.listPower();
        cMap.success().setResult(result.getTree());
        return cMap;
    }

    /**
     * 根据父资源ID获取其下所有的子资源
     *
     * @param permissionDTO 查询条件
     * @param pageInfo 分页条件
     * @return 子资源
     */
    @GetMapping(value="/subPower.do")
    public CmsMap subPower(PermissionDTO permissionDTO, PageInfo pageInfo) {
        CmsMap<List<ResourceEntity>> cMap = new CmsMap<>();
        PermissionDTO result = permissionService.subPower(permissionDTO, pageInfo);
        cMap.success().appendData("total", pageInfo.getCounts()).setResult(result.getResources());
        return cMap;
    }

    /**
     * 删除指定的资源
     *
     * @param ids 待删除的资源ID
     * @return 删除结果
     */
    @DeleteMapping(value="/deletePower.do")
    public CmsMap deletePower(String ids) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        permissionService.deletePower(ids);
        cMap.success();
        return cMap;
    }

    @PutMapping(value="/updatePower.do")
    public CmsMap updatePower(@RequestBody ResourceEntity entity) {
        permissionService.updateResouce(entity);
        // 更改资源需要清空对应用户的缓存
        List<Integer> userIds = permissionService.findUserIdsByResource(entity.getId());
        if (!userIds.isEmpty()) {
            userIds.forEach(userId -> cmsCacheManager
                    .clearCacheByKeys("resourceCache", "resourceTree_" + userId));
        }
        return CmsMap.ok();
    }

    @PostMapping(value="/savePower.do")
    public CmsMap savePower(@RequestBody ResourceEntity entity) {
        CmsMap<PermissionDTO> cMap = new CmsMap<>();
        permissionService.saveResource(entity);
        cMap.success();
        return cMap;
    }
}
