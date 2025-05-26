package com.huangge1199.aiagent.Service;

import org.springframework.ai.rag.Query;

import java.util.List;

/**
 * RagService
 *
 * @author huangge1199
 * @since 2025/5/24 9:21:24
 */
public interface RagService {
    String localDoc(String question);

    List<Query> getMultiQueryExpand(String question);

    String queryRewrite(String question);

    String queryTranslation(String question);

    String contextAwareQueries(String question);

    String baseAdvisor(String question);

    String advancedAdvisor(String question);

    String documentSelection();
}
