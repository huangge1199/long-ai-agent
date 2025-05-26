package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.RagService;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import com.huangge1199.aiagent.rag.MyMultiQueryExpander;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Resource
    private ChatModel ollamaChatModel;

    @Override
    public String localDoc(String question) {
        return chatClient.prompt()
                .user(question)
                .advisors(new MyLoggerAdvisor())
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }

    @Override
    public List<Query> getMultiQueryExpand(String question) {
        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);

        ChatClient chatClient = builder
                .defaultSystem("你是一位专业的室内设计顾问，精通各种装修风格、材料选择和空间布局。请基于提供的参考资料，为用户提供专业、详细且实用的建议。在回答时，请注意：\n" +
                        "1. 准确理解用户的具体需求\n" +
                        "2. 结合参考资料中的实际案例\n" +
                        "3. 提供专业的设计理念和原理解释\n" +
                        "4. 考虑实用性、美观性和成本效益\n" +
                        "5. 如有需要，可以提供替代方案")
                .build();

//        MultiQueryExpander queryExpander = MultiQueryExpander.builder()
        MyMultiQueryExpander queryExpander = MyMultiQueryExpander.builder()
                .chatClientBuilder(builder)
                // 不包含原始查询
                .includeOriginal(false)
                // 生成3个查询变体
                .numberOfQueries(4)
                .build();
        List<Query> queries = queryExpander.expand(new Query(question));
        return queries;
    }
}
