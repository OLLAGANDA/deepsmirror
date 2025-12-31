package com.deepmirror.server.service;

import com.deepmirror.server.domain.Result;
import com.deepmirror.server.dto.ResultCreateRequest;
import com.deepmirror.server.dto.ResultResponse;
import com.deepmirror.server.exception.JsonSerializationException;
import com.deepmirror.server.repository.ResultRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ResultService {

    private static final Logger log = LoggerFactory.getLogger(ResultService.class);

    private final ResultRepository resultRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;

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
        log.info("성격 분석 요청 - 닉네임: {}", request.getNickname());

        Result result = buildResultEntity(request);
        Result savedResult = resultRepository.save(result);

        log.info("성격 분석 완료 - ID: {}, 닉네임: {}", savedResult.getId(), savedResult.getNickname());

        return ResultResponse.fromEntity(savedResult);
    }

    /**
     * ID로 결과 조회
     * @param id 결과 ID (UUID)
     * @return 결과 DTO (Optional)
     */
    @Transactional(readOnly = true)
    public Optional<ResultResponse> getResult(UUID id) {
        log.debug("결과 조회 요청 - ID: {}", id);
        return resultRepository.findById(id)
                .map(ResultResponse::fromEntity);
    }

    /**
     * Result Entity 생성
     */
    private Result buildResultEntity(ResultCreateRequest request) {
        Result result = request.toEntity();

        String detailScoresJson = serializeDetailScores(request.getDetailScores());
        result.setDetailScores(detailScoresJson);

        String aiAnalysis = performAiAnalysis(request);
        result.setAiAnalysis(aiAnalysis);

        return result;
    }

    /**
     * DetailScores를 JSON 문자열로 직렬화
     */
    private String serializeDetailScores(Map<String, Integer> detailScores) {
        if (detailScores == null || detailScores.isEmpty()) {
            return null;
        }

        try {
            String json = objectMapper.writeValueAsString(detailScores);
            log.debug("DetailScores 직렬화 성공 - 크기: {}", detailScores.size());
            return json;
        } catch (JsonProcessingException e) {
            log.error("DetailScores JSON 직렬화 실패", e);
            throw new JsonSerializationException("DetailScores를 JSON으로 변환하는 데 실패했습니다.", e);
        }
    }

    /**
     * AI 성격 분석 수행
     */
    private String performAiAnalysis(ResultCreateRequest request) {
        log.debug("AI 성격 분석 시작 - O:{}, C:{}, E:{}, A:{}, N:{}",
                request.getOpenness(),
                request.getConscientiousness(),
                request.getExtraversion(),
                request.getAgreeableness(),
                request.getNeuroticism());

        String systemPrompt = buildSystemPrompt();
        String userMessage = buildUserMessage(
                request.getOpenness(),
                request.getConscientiousness(),
                request.getExtraversion(),
                request.getAgreeableness(),
                request.getNeuroticism()
        );

        String analysis = geminiService.generateContent(systemPrompt, userMessage);
        log.debug("AI 성격 분석 완료 - 결과 길이: {}", analysis.length());

        return analysis;
    }

    /**
     * AI 분석용 시스템 프롬프트 생성
     */
    private String buildSystemPrompt() {
        return """
            당신은 AI임상 심리학자이자 성격 분석 전문가입니다.
            Big Five 이론에 기반하여 사용자의 성격을 분석해주세요.
            단순히 각 항목의 점수를 나열하지 말고, **가장 점수가 높은 2가지 특성과 낮은 특성이 서로 어떻게 상호작용하는지**에 주목하여 통찰력 있는 분석을 제공하세요.
            말투는 전문적이면서도 따뜻하고 격려하는 톤으로 작성해주세요.
            """;
    }

    /**
     * AI 분석용 사용자 메시지 생성
     */
    private String buildUserMessage(int openness, int conscientiousness,
                                     int extraversion, int agreeableness, int neuroticism) {
        return String.format(
            """
            [Big Five 검사 결과]
            - 개방성 (Openness): %d
            - 성실성 (Conscientiousness): %d
            - 외향성 (Extraversion): %d
            - 친화성 (Agreeableness): %d
            - 신경성 (Neuroticism): %d

            위 점수를 바탕으로 다음 내용을 포함하여 400자 이내로 요약해주세요:
            1. 성격의 핵심 요약 (특성 간의 조화 위주)
            2. 직업적/사회적 강점
            3. 보완하면 좋을 점 혹은 조언 1가지
            """,
            openness, conscientiousness, extraversion, agreeableness, neuroticism
        );
    }
}

