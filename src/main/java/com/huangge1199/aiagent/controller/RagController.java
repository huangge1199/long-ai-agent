package com.huangge1199.aiagent.controller;

import com.huangge1199.aiagent.Service.RagService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.rag.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RagController
 *
 * @author huangge1199
 * @since 2025/5/24 9:14:30
 */
@RestController
@RequestMapping("/rag")
@Tag(name = "RAG相关")
public class RagController {

    @Resource
    private RagService ragService;

    @PostMapping("/localDoc")
    @Operation(summary = "本地知识库")
    public R<String> localDoc(@RequestBody String question) {
        String res = ragService.localDoc(question);
        return R.ok(res);
    }

    @PostMapping("/getMultiQueryExpand")
    @Operation(summary = "多查询扩展")
    public R<List<Query>> getMultiQueryExpand(@RequestBody String question) {
        List<Query> queryList = ragService.getMultiQueryExpand(question);
        return R.ok(queryList);
    }
}
