package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.DBService;
import com.huangge1199.aiagent.rag.DocumentLoaderUtils;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * DBServiceImpl
 *
 * @author huangge1199
 * @since 2025/5/27 10:46:56
 */
@Service
public class DBServiceImpl implements DBService {

    @Resource
    VectorStore pgVectorVectorStore;

    @Resource
    DocumentLoaderUtils documentLoaderUtils;


    @Override
    public List<Document> similaritySearch() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
        // 添加文档
        pgVectorVectorStore.add(documents);
        // 相似度查询
        return pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
    }

    @Override
    public List<Document> loadMdToDd() {
        List<Document> documents = documentLoaderUtils.loadMarkdowns();
        pgVectorVectorStore.add(documents);
        return pgVectorVectorStore.similaritySearch(SearchRequest.builder().topK(5).build());
    }
}
