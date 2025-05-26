package com.huangge1199.aiagent.Service.impl;

import com.huangge1199.aiagent.Service.RagService;
import com.huangge1199.aiagent.config.MyLoggerAdvisor;
import com.huangge1199.aiagent.rag.MyMultiQueryExpander;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
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

    @Resource
    private EmbeddingModel ollamaEmbeddingModel;

    @Override
    public String localDoc(String question) {
        return chatClient.prompt().user(question).advisors(new MyLoggerAdvisor()).advisors(new QuestionAnswerAdvisor(vectorStore)).call().content();
    }

    @Override
    public List<Query> getMultiQueryExpand(String question) {
        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);

        ChatClient chatClient = builder.defaultSystem("你是一位专业的室内设计顾问，精通各种装修风格、材料选择和空间布局。请基于提供的参考资料，为用户提供专业、详细且实用的建议。在回答时，请注意：\n" + "1. 准确理解用户的具体需求\n" + "2. 结合参考资料中的实际案例\n" + "3. 提供专业的设计理念和原理解释\n" + "4. 考虑实用性、美观性和成本效益\n" + "5. 如有需要，可以提供替代方案").build();

//        MultiQueryExpander queryExpander = MultiQueryExpander.builder()
        MyMultiQueryExpander queryExpander = MyMultiQueryExpander.builder().chatClientBuilder(builder)
                // 不包含原始查询
                .includeOriginal(false)
                // 生成3个查询变体
                .numberOfQueries(4).build();
        return queryExpander.expand(new Query(question));
    }

    @Override
    public String queryRewrite(String question) {
        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);
        // 创建一个模拟用户学习AI的查询场景
        Query query = new Query(question);

        // 创建查询重写转换器
        QueryTransformer queryTransformer = RewriteQueryTransformer.builder().chatClientBuilder(builder).build();

        // 执行查询重写
        Query transformedQuery = queryTransformer.transform(query);
        return transformedQuery.text();
    }

    @Override
    public String queryTranslation(String question) {
        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);
        // 创建一个英文查询
        Query query = new Query(question);

        // 创建查询翻译转换器，设置目标语言为中文
        QueryTransformer queryTransformer = TranslationQueryTransformer.builder().chatClientBuilder(builder)
                // 设置目标语言为中文
                .targetLanguage("chinese").build();

        // 执行查询翻译
        Query transformedQuery = queryTransformer.transform(query);

        // 输出翻译后的查询
        return transformedQuery.text();
    }

    @Override
    public String contextAwareQueries(String question) {
        ChatClient.Builder builder = ChatClient.builder(ollamaChatModel);
        // 构建带有历史上下文的查询
        // 这个例子模拟了一个房地产咨询场景，用户先问小区位置，再问房价
        Query query = Query.builder()
                // 当前用户的提问
                .text(question)
                // 历史对话中用户的问题
                .history(new UserMessage("深圳市南山区的碧海湾小区在哪里?"),
                        // AI的回答
                        new AssistantMessage("碧海湾小区位于深圳市南山区后海中心区，临近后海地铁站。")).build();
        // 创建查询转换器
        // QueryTransformer用于将带有上下文的查询转换为完整的独立查询
        QueryTransformer queryTransformer = CompressionQueryTransformer.builder()
                .chatClientBuilder(builder)
                .build();

        // 执行查询转换
        // 将模糊的代词引用（"这个小区"）转换为明确的实体名称（"碧海湾小区"）
        Query transformedQuery = queryTransformer.transform(query);
        return transformedQuery.text();

    }

    @Override
    public String baseAdvisor(String question) {
        Advisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .build())
                .build();
        return advisor(question, advisor);
    }

    @Override
    public String advancedAdvisor(String question) {
        Advisor advisor = RetrievalAugmentationAdvisor.builder()
                // 配置查询增强器
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        // 允许空上下文查询
                        .allowEmptyContext(true)
                        .build())
                // 配置文档检索器
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        // 相似度阈值
                        .similarityThreshold(0.5)
                        // 返回文档数量
                        .topK(3)
                        .filterExpression(new FilterExpressionBuilder()
                                .eq("genre", "fairytale")
                                .build())     // 文档过滤表达式
                        .build())
                .build();
        return advisor(question, advisor);
    }

    /**
     * 检索增强顾问
     * @param question 问题
     * @param advisor 检索增强顾问
     * @return 查询结果
     */
    private String advisor(String question, Advisor advisor) {
        // 1. 初始化向量存储
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(ollamaEmbeddingModel)
                .build();

        // 2. 添加文档到向量存储
        List<Document> documents = List.of(
                new Document("产品说明书:产品名称：智能机器人\n" +
                        "产品描述：智能机器人是一个智能设备，能够自动完成各种任务。\n" +
                        "功能：\n" +
                        "1. 自动导航：机器人能够自动导航到指定位置。\n" +
                        "2. 自动抓取：机器人能够自动抓取物品。\n" +
                        "3. 自动放置：机器人能够自动放置物品。\n"));
        vectorStore.add(documents);

        // 3. 创建检索增强顾问 advisor

        // 4. 在聊天客户端中使用顾问
        return chatClient.prompt()
                .user(question)
                // 添加检索增强顾问
                .advisors(advisor)
                .call()
                .content();
    }
}
