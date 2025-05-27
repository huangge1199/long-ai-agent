package com.huangge1199.aiagent;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hyy
 */
@SpringBootApplication(exclude = PgVectorStoreAutoConfiguration.class)
public class LongAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LongAiAgentApplication.class, args);
    }

}
