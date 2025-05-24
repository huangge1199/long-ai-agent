package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.RagService;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

/**
 * RagServiceImpl
 *
 * @author huangge1199
 * @since 2025/5/24 9:21:38
 */
@Service
public class RagServiceImpl implements RagService {

    @Resource
    private ChatClient chatClient;

    @Resource
    private VectorStore vectorStore;

    @Override
    public String localDoc(String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(new MyLoggerAdvisor())
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }
}
