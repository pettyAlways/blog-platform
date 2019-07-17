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

    @PutMapping("/update")
    public CmsMap updateKnowledge(@RequestBody KnowledgeEntity knowledgeEntity) {
        knowledgeService.editKnowledge(knowledgeEntity);
        return CmsMap.ok();
    }

    @PutMapping("/share/update")
    public CmsMap updateShareKnowledge(@RequestBody KnowledgeEntity knowledgeEntity) {
        knowledgeService.editShareKnowledge(knowledgeEntity);
        return CmsMap.ok();
    }

    @DeleteMapping("/remove")
    public CmsMap removeKnowledge(@RequestParam Integer knowledgeId) {
        knowledgeService.removeKnowledge(knowledgeId);
        return CmsMap.ok();
    }

    @DeleteMapping("/share/remove")
    public CmsMap removeShareKnowledge(@RequestParam Integer knowledgeId) {
        knowledgeService.removeShareKnowledge(knowledgeId);
        return CmsMap.ok();
    }

    @PutMapping("/removeParticipant")
    public CmsMap removeParticipant(@RequestParam("participantId") Integer uId, @RequestParam("knowledgeId") Integer kId ) {
        knowledgeService.removeParticipant(uId, kId);
        return CmsMap.ok();
    }

    @PutMapping("/share/removeParticipant")
    public CmsMap removeShareParticipant(@RequestParam("participantId") Integer uId, @RequestParam("knowledgeId") Integer kId ) {
        knowledgeService.removeShareParticipant(uId, kId);
        return CmsMap.ok();
    }

    @GetMapping("/belongs/mine")
    public CmsMap<List<KnowledgeDTO>> retrieveParicipantKnowledge() {
        List<KnowledgeDTO> knowledgeDTOS = knowledgeService.retrieveParticipantKnowledge();
        return CmsMap.<List<KnowledgeDTO>>ok().setResult(knowledgeDTOS);
    }


}
