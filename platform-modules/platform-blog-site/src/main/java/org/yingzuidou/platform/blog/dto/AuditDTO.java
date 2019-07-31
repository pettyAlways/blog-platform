package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;
import org.yingzuidou.platform.common.utils.DateUtil;

import java.util.Date;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/30
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class AuditDTO {

    /**
     * 审核ID
     */
    private Integer auditId;

    /**
     * 申请人名字
     */
    private String applyUserName;

    /**
     * 处理人名字
     */
    private String handleUserName;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 知识库名字
     */
    private String knowledgeName;

    /**
     * 申请理由
     */
    private String reason;

    /**
     * 处理结果
     */
    private String handleResult;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 拒绝理由
     */
    private String rejectReason;

    public static Function<Object[], AuditDTO> authorAuditList = data -> new AuditDTO()
            .setApplyUserName(CmsBeanUtils.objectToString(data[0])).setHandleUserName(CmsBeanUtils.objectToString(data[1]))
            .setApplyTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[2]), "yyyy-MM-dd HH:mm:ss"))
            .setReason(CmsBeanUtils.objectToString(data[3]))
            .setHandleResult(CmsBeanUtils.objectToString(data[4]))
            .setHandleTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[5]), "yyyy-MM-dd HH:mm:ss"))
            .setRejectReason(CmsBeanUtils.objectToString(data[6])).setAuditId(CmsBeanUtils.objectToInt(data[7]));

    public static Function<Object[], AuditDTO> joinKnowledgeList = data -> new AuditDTO()
            .setApplyUserName(CmsBeanUtils.objectToString(data[0])).setHandleUserName(CmsBeanUtils.objectToString(data[1]))
            .setKnowledgeName(CmsBeanUtils.objectToString(data[2]))
            .setApplyTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[3]), "yyyy-MM-dd HH:mm:ss"))
            .setHandleResult(CmsBeanUtils.objectToString(data[4]))
            .setHandleTime(DateUtil.transformStrToDate(CmsBeanUtils.objectToString(data[5]), "yyyy-MM-dd HH:mm:ss"))
            .setReason(CmsBeanUtils.objectToString(data[6]))
            .setRejectReason(CmsBeanUtils.objectToString(data[7])).setAuditId(CmsBeanUtils.objectToInt(data[8]));
}
