package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.entity.CmsUserEntity;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/20
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class OperRecordDTO {

    /**
     * 操作用户名字
     */
    private String operUserName;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 操作类型
     */
    private String operType;

    /**
     * 处理用户名字
     */
    private String handleUserName;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 对象类型
     */
    private String objType;

    /**
     * 对象值
     */
    private Integer obj;

    /**
     * 对象名字
     */
    private String objName;

    /**
     * 根类型
     */
    private String rootType;

    /**
     * 根对象
     */
    private Integer rootObj;

    /**
     * 保留字段
     */
    private String reserve;

    /**
     * 根对象名字
     */
    private String rootName;

    public static Function<Object[], OperRecordDTO> operRecord = data -> new OperRecordDTO()
            .setOperUserName(String.valueOf(data[0]))
            .setOperTime(DateUtil.transformStrToDate(String.valueOf(data[1]), "yyyy-MM-dd HH:mm:ss"))
            .setOperType(String.valueOf(data[2])).setHandleUserName(String.valueOf(data[3]))
            .setHandleResult(CmsBeanUtils.objectToString(data[4])).setObjType(String.valueOf(data[5]))
            .setObj(CmsBeanUtils.objectToInt(data[6])).setRootType(String.valueOf(data[7]))
            .setRootObj(CmsBeanUtils.objectToInt(data[8])).setReserve(CmsBeanUtils.objectToString(data[9]));

}
