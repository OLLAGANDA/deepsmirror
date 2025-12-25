package com.deepmirror.server.service;

import com.deepmirror.server.domain.Result;
import com.deepmirror.server.dto.ResultCreateRequest;
import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.repository.ResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

    // 생성자 주입 (Lombok 없이)
    public ResultService(ResultRepository resultRepository, GeminiService geminiService, ObjectMapper objectMapper) {
        this.resultRepository = resultRepository;
        this.geminiService = geminiService;
        this.objectMapper = objectMapper;
    }

    /**
     * 성격 분석 결과 생성 및 저장
     * @param request 클라이언트가 보낸 설문 결과
     * @return 저장된 결과 (DTO)
     */
    public ResultResponse createResult(ResultCreateRequest request) {
        // DTO -> Entity 변환
        Result result = request.toEntity();

        // detailScores를 JSON 문자열로 변환
        if (request.getDetailScores() != null && !request.getDetailScores().isEmpty()) {
            try {
                String detailScoresJson = objectMapper.writeValueAsString(request.getDetailScores());
                result.setDetailScores(detailScoresJson);
            } catch (JsonProcessingException e) {
                // JSON 변환 실패 시 로그 출력 및 null로 설정
                System.err.println("Failed to serialize detailScores: " + e.getMessage());
                result.setDetailScores(null);
            }
        }

        // AI 분석 수행
        String aiAnalysis = analyzePersonality(
                request.getOpenness(),
                request.getConscientiousness(),
                request.getExtraversion(),
                request.getAgreeableness(),
                request.getNeuroticism()
        );

        // AI 분석 결과 저장
        result.setAiAnalysis(aiAnalysis);

        // 저장
        Result savedResult = resultRepository.save(result);

        // Entity -> DTO 변환 후 반환
        return ResultResponse.fromEntity(savedResult);
    }

    /**
     * ID로 결과 조회
     * @param id 결과 ID (UUID)
     * @return 결과 DTO (Optional)
     */
    public Optional<ResultResponse> getResult(UUID id) {
        return resultRepository.findById(id)
                .map(ResultResponse::fromEntity);
    }

    /**
     * AI를 통한 성격 분석
     * @param openness 개방성 점수
     * @param conscientiousness 성실성 점수
     * @param extraversion 외향성 점수
     * @param agreeableness 친화성 점수
     * @param neuroticism 신경성 점수
     * @return AI 분석 결과 텍스트
     */
    private String analyzePersonality(int openness, int conscientiousness,
                                      int extraversion, int agreeableness, int neuroticism) {
        String systemPrompt = "너는 심리학 전문가야. Big Five 성격 이론에 기반하여 사용자의 성격을 분석하고, 실용적이고 긍정적인 피드백을 제공해.";

        String userMessage = String.format(
                """
                다음은 Big Five 성격 검사 결과입니다. 각 항목은 0~100 사이의 점수입니다:

                - 개방성 (Openness): %d
                - 성실성 (Conscientiousness): %d
                - 외향성 (Extraversion): %d
                - 친화성 (Agreeableness): %d
                - 신경성 (Neuroticism): %d

                이 점수를 바탕으로 사용자의 성격을 종합적으로 분석하고, 각 특성이 일상생활과 대인관계에 어떤 영향을 미치는지 설명해주세요.
                200자 이내로 간결하게 작성해주세요.
                """,
                openness, conscientiousness, extraversion, agreeableness, neuroticism
        );

        return geminiService.generateContent(systemPrompt, userMessage);
    }
}
