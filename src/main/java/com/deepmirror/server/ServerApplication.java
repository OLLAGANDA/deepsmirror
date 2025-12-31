package com.deepmirror.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
	org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiAutoConfiguration.class,
	org.springframework.ai.autoconfigure.chat.client.ChatClientAutoConfiguration.class
})
@EnableScheduling
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
