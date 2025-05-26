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

    @PostMapping("/queryRewrite")
    @Operation(summary = "查询重写")
    public R<String> queryRewrite(@RequestBody String question) {
        String queryList = ragService.queryRewrite(question);
        return R.ok(queryList);
    }

    @PostMapping("/queryTranslation")
    @Operation(summary = "查询翻译")
    public R<String> queryTranslation(@RequestBody String question) {
        String queryList = ragService.queryTranslation(question);
        return R.ok(queryList);
    }

    @PostMapping("/contextAwareQueries")
    @Operation(summary = "上下文感知查询")
    public R<String> contextAwareQueries(@RequestBody String question) {
        String queryList = ragService.contextAwareQueries(question);
        return R.ok(queryList);
    }

    @PostMapping("/baseAdvisor")
    @Operation(summary = "检索增强顾问：基础用法")
    public R<String> baseAdvisor(@RequestBody String question) {
        String queryList = ragService.baseAdvisor(question);
        return R.ok(queryList);
    }

    @PostMapping("/advancedAdvisor")
    @Operation(summary = "检索增强顾问：高级用法")
    public R<String> advancedAdvisor(@RequestBody String question) {
        String queryList = ragService.advancedAdvisor(question);
        return R.ok(queryList);
    }
}
