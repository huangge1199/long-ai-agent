package com.huangge1199.aiagent.controller;

import com.huangge1199.aiagent.Service.DBService;
import com.huangge1199.aiagent.common.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DBController
 *
 * @author huangge1199
 * @since 2025/5/27 10:37:25
 */
@RestController
@RequestMapping("/db")
@Tag(name = "数据库相关")
public class DBController {

    @Resource
    private DBService dbService;

    @PostMapping("/similaritySearch")
    @Operation(summary = "相似度查询")
    public R<List<Document>> similaritySearch() {
        List<Document> results = dbService.similaritySearch();
        return R.ok(results);
    }
}
