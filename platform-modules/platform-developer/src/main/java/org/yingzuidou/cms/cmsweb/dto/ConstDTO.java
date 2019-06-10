package org.yingzuidou.cms.cmsweb.dto;

import org.yingzuidou.cms.cmsweb.entity.CmsConstEntity;

import java.util.List;

/**
 * 类功能描述
 * 接收常量配置页面传递的内容
 *
 * @author 鹰嘴豆
 * @date 2018/11/13
 *
 * 时间           作者          版本        描述
 * ====================================================
 * 2018/11/13     鹰嘴豆        v1.0        接收常量配置页面内容
 */
public class ConstDTO {

    /**
     * Value的中文名字
     */
    private String constName;

    /**
     * 常量的键值
     */
    private String constKey;

    /**
     * 常量类型-页面不同Tab代表不同类型
     */
    private String type;

    private List<CmsConstEntity> constList;

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName = constName;
    }

    public String getConstKey() {
        return constKey;
    }

    public void setConstKey(String constKey) {
        this.constKey = constKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CmsConstEntity> getConstList() {
        return constList;
    }

    public void setConstList(List<CmsConstEntity> constList) {
        this.constList = constList;
    }
}
