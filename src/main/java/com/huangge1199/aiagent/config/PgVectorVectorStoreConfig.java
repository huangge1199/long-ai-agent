package com.huangge1199.aiagent.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

/**
 * pgConfi
 *
 * @author huangge1199
 * @since 2025/5/27 9:52:30
 */
@Configuration
public class PgVectorVectorStoreConfig {

    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, @Qualifier("ollamaEmbeddingModel") EmbeddingModel embeddingModel) {

        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                // 设置向量维度，默认为模型维度或1536
                .dimensions(1024)
                // 设置距离类型，默认为 COSINE_DISTANCE
                .distanceType(COSINE_DISTANCE)
                // 设置索引类型，默认为 HNSW
                .indexType(HNSW)
                // 是否初始化模式，默认为 false
                .initializeSchema(true)
                // 设置模式名称，默认为 "public"
                .schemaName("public")
                // 设置向量表名称，默认为 "vector_store"
                .vectorTableName("vector_store")
                // 设置最大文档批处理大小，默认为 10000
                .maxDocumentBatchSize(10000)
                .build();
    }
}

