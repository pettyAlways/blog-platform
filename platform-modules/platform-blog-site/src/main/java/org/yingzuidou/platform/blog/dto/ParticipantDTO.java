package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

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
public class ParticipantDTO {

    /**
     * 参与者名字
     */
    private String participantName;

    /**
     * 参与者ID
     */
    private Integer participantId;

    public static Function<Object[], ParticipantDTO> participantInKnowledge = data -> new ParticipantDTO()
            .setParticipantId(CmsBeanUtils.objectToInt(data[0])).setParticipantName(String.valueOf(data[1]));
}
