package org.yingzuidou.cms.cmsweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.cms.cmsweb.core.CmsMap;
import org.yingzuidou.cms.cmsweb.core.paging.PageInfo;
import org.yingzuidou.cms.cmsweb.dto.ConstDTO;
import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;
import org.yingzuidou.cms.cmsweb.service.ConstService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 类功能描述
 * key/value、字典、枚举、系统配置等内容可在页面中配置
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1          提供常量可配置
 */

@RestController
@RequestMapping(value="/const")
public class ConstController {

    @Autowired
    private ConstService constService;

    @GetMapping(value="/list.do")
    public CmsMap<List<CmsConstEntity>> list(ConstDTO constDTO, PageInfo pageInfo) {
        CmsMap<List<CmsConstEntity>> cMap = new CmsMap<>();
        ConstDTO result = constService.list(constDTO, pageInfo);
        cMap.success().appendData("counts", pageInfo.getCounts()).setResult(result.getConstList());
        return cMap;
    }

    @PostMapping(value="/save.do")
    public CmsMap save(@RequestBody CmsConstEntity cmsConstEntity) {
        constService.save(cmsConstEntity);
        return CmsMap.ok();
    }

    @PutMapping(value="/edit.do")
    public CmsMap edit(@RequestBody CmsConstEntity cmsConstEntity) {
        constService.update(cmsConstEntity);
        return CmsMap.ok();
    }

    /**
     * 删除常用的变量，需要清空对应常量缓存
     * 如果删除系统常量并且键为root_resource还需要清空资源树缓存
     *
     * @param ids 待删除的常量id
     * @param type 常用变量类型
     * @return 请求结果
     */
    @DeleteMapping(value="/delete.do")
    public CmsMap delete(String ids, String type) {
        // 在这里转主要是为了能被缓存使用
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf)
                .collect(Collectors.toList());
        CmsConstEntity constEntity = constService.findRootResource("root_resource");
        // 主要用于缓存清空，不存在则设置一个不存在的值
        Integer rootResourceId = -1;
        if (Objects.nonNull(constEntity)) {
            rootResourceId = constEntity.getId();
        }
        constService.delete(idsList, type, rootResourceId);
        return CmsMap.ok();
    }
}
