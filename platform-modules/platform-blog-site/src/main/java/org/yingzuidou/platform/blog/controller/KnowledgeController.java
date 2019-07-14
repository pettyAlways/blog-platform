package org.yingzuidou.platform.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yingzuidou.platform.blog.dto.KnowledgeDTO;
import org.yingzuidou.platform.blog.dto.RecentKnowledgeDTO;
import org.yingzuidou.platform.blog.service.KnowledgeService;
import org.yingzuidou.platform.blog.service.OperRecordService;
import org.yingzuidou.platform.common.entity.KnowledgeEntity;
import org.yingzuidou.platform.common.vo.CmsMap;

import java.util.List;

/**
 * 类功能描述
 *
 * @author 鹰嘴豆
 * @date 2019/7/7
 * <p>
 * 时间           作者          版本        描述
 * ====================================================
 */
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private OperRecordService operRecordService;


    @GetMapping(value="/list")
    public CmsMap<List<KnowledgeDTO>> list(KnowledgeDTO knowledgeDTO) {
        List<KnowledgeDTO> result = knowledgeService.list(knowledgeDTO);
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(result);
    }

    @GetMapping(value="/item")
    public CmsMap<KnowledgeDTO> knowledgeItem(@RequestParam("knowledgeId") Integer knowledgeId) {
        KnowledgeDTO result = knowledgeService.item(knowledgeId);
        return CmsMap.<KnowledgeDTO>ok().setResult(result);
    }


    @PostMapping("/add")
    public CmsMap add(@RequestBody KnowledgeEntity knowledgeEntity) {
        knowledgeService.addKnowledge(knowledgeEntity);
        return CmsMap.ok();
    }

    @PutMapping("/removeParticipant")
    public CmsMap removeParticipant(@RequestParam("participantId") Integer uId, @RequestParam("knowledgeId") Integer kId ) {
        knowledgeService.removeParticipant(uId, kId);
        return CmsMap.ok();
    }

}
