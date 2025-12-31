package com.deepmirror.server.service;

import com.deepmirror.server.exception.AiAnalysisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta";
    private static final String FALLBACK_MESSAGE = "AI 분석 결과를 생성할 수 없습니다. 잠시 후 다시 시도해주세요.";

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public GeminiService(
            @Value("${spring.ai.gemini.api-key}") String apiKey,
            @Value("${spring.ai.gemini.chat.options.model:gemini-pro}") String model,
            RestClient.Builder restClientBuilder) {
        this.apiKey = apiKey;
        this.model = model;
        this.restClient = restClientBuilder
                .baseUrl(BASE_URL)
                .build();
        log.info("GeminiService 초기화 - 모델: {}", model);
    }

    /**
     * Gemini AI를 사용하여 콘텐츠 생성
     * @param systemPrompt 시스템 프롬프트
     * @param userPrompt 사용자 프롬프트
     * @return 생성된 텍스트
     */
    public String generateContent(String systemPrompt, String userPrompt) {
        log.debug("Gemini API 요청 시작 - 모델: {}", model);

        try {
            String endpoint = buildEndpoint();
            Map<String, Object> requestBody = buildRequestBody(systemPrompt, userPrompt);

            Map<String, Object> response = sendRequest(endpoint, requestBody);
            String generatedText = extractTextFromResponse(response);

            log.debug("Gemini API 응답 성공 - 텍스트 길이: {}", generatedText.length());
            return generatedText;

        } catch (RestClientException e) {
            log.error("Gemini API 통신 오류: {}", e.getMessage(), e);
            throw new AiAnalysisException("AI 서비스와 통신 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            log.error("AI 분석 중 예상치 못한 오류: {}", e.getMessage(), e);
            throw new AiAnalysisException("AI 분석 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * API 엔드포인트 생성
     */
    private String buildEndpoint() {
        return String.format("/models/%s:generateContent?key=%s", model, apiKey);
    }

    /**
     * 요청 본문 생성
     */
    private Map<String, Object> buildRequestBody(String systemPrompt, String userPrompt) {
        String combinedPrompt = systemPrompt + "\n\n" + userPrompt;

        return Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", combinedPrompt)
                                )
                        )
                )
        );
    }

    /**
     * Gemini API 요청 전송
     */
    private Map<String, Object> sendRequest(String endpoint, Map<String, Object> requestBody) {
        Map<String, Object> response = restClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if (response == null) {
            log.error("Gemini API 응답이 null입니다.");
            throw new AiAnalysisException("AI 서비스로부터 응답을 받지 못했습니다.");
        }

        return response;
    }

    /**
     * 응답에서 생성된 텍스트 추출
     */
    private String extractTextFromResponse(Map<String, Object> response) {
        if (!response.containsKey("candidates")) {
            log.warn("응답에 candidates 필드가 없습니다: {}", response);
            return FALLBACK_MESSAGE;
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            log.warn("candidates가 비어있습니다.");
            return FALLBACK_MESSAGE;
        }

        Map<String, Object> firstCandidate = candidates.get(0);
        if (!firstCandidate.containsKey("content")) {
            log.warn("첫 번째 candidate에 content가 없습니다: {}", firstCandidate);
            return FALLBACK_MESSAGE;
        }

        Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

        if (parts == null || parts.isEmpty()) {
            log.warn("content의 parts가 비어있습니다.");
            return FALLBACK_MESSAGE;
        }

        String text = (String) parts.get(0).get("text");
        if (text == null || text.trim().isEmpty()) {
            log.warn("추출된 텍스트가 비어있습니다.");
            return FALLBACK_MESSAGE;
        }

        return text;
    }
}

