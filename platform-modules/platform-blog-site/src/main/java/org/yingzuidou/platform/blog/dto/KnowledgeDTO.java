package org.yingzuidou.platform.blog.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.yingzuidou.platform.common.utils.CmsBeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@Data
@Accessors(chain = true)
public class KnowledgeDTO {

    /**
     * 知识库ID
     */
    private Integer knowledgeId;

    /**
     * 知识库名字
     */
    private String knowledgeName;

    /**
     * 知识库描述
     */
    private String knowledgeDesc;

    /**
     * 知识库封面
     */
    private String knowledgeCover;

    /**
     * 创建人名字
     */
    private String createName;

    /**
     * 创建人ID
     */
    private Integer creator;

    /**
     * 知识库访问权限
     */
    private String knowledgeAccess;

    /**
     * 知识库分类
     */
    private Integer categoryType;

    /**
     * 预留字段
     */
    private String reserveO;

    /**
     * 知识库下的文章列表
     */
    private List<ArticleDTO> articleEntities;

    /**
     * 知识库的参与人列表
     */
    private List<ParticipantDTO> participantEntities;

    public static Function<Object[], KnowledgeDTO> participantKnowledge = data -> new KnowledgeDTO().
            setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(String.valueOf(data[1]))
            .setKnowledgeDesc(String.valueOf(data[2])).setCreator(CmsBeanUtils.objectToInt(data[3]))
            .setCreateName(String.valueOf(data[4]))
            .setParticipantEntities(transformToParticipant(data[5], data[6]));

    public static Function<Object[], KnowledgeDTO> knowledgeDetail = data -> new KnowledgeDTO().
            setKnowledgeId(CmsBeanUtils.objectToInt(data[0])).setKnowledgeName(String.valueOf(data[1]))
            .setKnowledgeDesc(String.valueOf(data[2])).setKnowledgeCover(String.valueOf(data[3]))
            .setCreateName(String.valueOf(data[4])).setCreator(CmsBeanUtils.objectToInt(data[5]))
            .setKnowledgeAccess(CmsBeanUtils.objectToString(data[6])).setCategoryType(CmsBeanUtils.objectToInt(data[7]))
            .setReserveO(CmsBeanUtils.objectToString(data[8]));

    /**
     * 将sql通过group_concat组合的参与者名字和id解析成ParticipantDTO列表
     *
     * @param name 参与者名字
     * @param id 参与者ID
     * @return ParticipantDTO列表
     */
    private static List<ParticipantDTO> transformToParticipant(Object name, Object id) {
        List<ParticipantDTO> participantDTOS = new ArrayList<>();
        if (Objects.nonNull(name) && Objects.nonNull(id)) {
            String[] names = String.valueOf(name).split(",");
            String[] ids = String.valueOf(id).split(",");
            for (int index = 0; index < names.length; index++) {
                ParticipantDTO participantDTO = new ParticipantDTO();
                participantDTO.setParticipantId(Integer.valueOf(ids[index])).setParticipantName(names[index]);
                participantDTOS.add(participantDTO);
            }
        }
        return participantDTOS;
    }

}
