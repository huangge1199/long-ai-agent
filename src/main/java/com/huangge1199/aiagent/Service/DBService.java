package com.huangge1199.aiagent.Service;

import org.springframework.ai.document.Document;

import java.util.List;

/**
 * DBService
 *
 * @author huangge1199
 * @since 2025/5/27 10:46:40
 */
public interface DBService {
    List<Document> similaritySearch();

    List<Document> loadMdToDd();
}
