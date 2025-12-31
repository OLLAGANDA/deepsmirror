package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Result;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 성격 분석 결과 응답 DTO
 *
 * <p>서버에서 클라이언트로 전달하는 성격 분석 결과 데이터를 담는 객체입니다.
 * Entity를 직접 노출하지 않고 필요한 정보만 선택적으로 전달합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 * @see Result
 */
public class ResultResponse {

    /**
     * 결과 고유 식별자
     */
    private UUID id;

    /**
     * 사용자 닉네임
     */
    private String nickname;

    /**
     * 개방성 점수 (0-100)
     */
    private int openness;

    /**
     * 성실성 점수 (0-100)
     */
    private int conscientiousness;

    /**
     * 외향성 점수 (0-100)
     */
    private int extraversion;

    /**
     * 친화성 점수 (0-100)
     */
    private int agreeableness;

    /**
     * 신경성 점수 (0-100)
     */
    private int neuroticism;

    /**
     * AI가 생성한 성격 분석 텍스트
     */
    private String aiAnalysis;

    /**
     * 세부 점수 JSON 문자열
     */
    private String detailScores;

    /**
     * 결과 생성 일시
     */
    private LocalDateTime createdAt;

    // 기본 생성자
    public ResultResponse() {
    }

    // 전체 생성자
    public ResultResponse(UUID id, String nickname, int openness, int conscientiousness,
                          int extraversion, int agreeableness, int neuroticism,
                          String aiAnalysis, String detailScores, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
        this.aiAnalysis = aiAnalysis;
        this.detailScores = detailScores;
        this.createdAt = createdAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getOpenness() {
        return openness;
    }

    public int getConscientiousness() {
        return conscientiousness;
    }

    public int getExtraversion() {
        return extraversion;
    }

    public int getAgreeableness() {
        return agreeableness;
    }

    public int getNeuroticism() {
        return neuroticism;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public String getDetailScores() {
        return detailScores;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOpenness(int openness) {
        this.openness = openness;
    }

    public void setConscientiousness(int conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public void setExtraversion(int extraversion) {
        this.extraversion = extraversion;
    }

    public void setAgreeableness(int agreeableness) {
        this.agreeableness = agreeableness;
    }

    public void setNeuroticism(int neuroticism) {
        this.neuroticism = neuroticism;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public void setDetailScores(String detailScores) {
        this.detailScores = detailScores;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Result Entity를 ResultResponse DTO로 변환하는 정적 팩토리 메서드
     *
     * @param result 변환할 Result Entity
     * @return 변환된 ResultResponse DTO
     */
    public static ResultResponse fromEntity(Result result) {
        return new ResultResponse(
                result.getId(),
                result.getNickname(),
                result.getOpenness(),
                result.getConscientiousness(),
                result.getExtraversion(),
                result.getAgreeableness(),
                result.getNeuroticism(),
                result.getAiAnalysis(),
                result.getDetailScores(),
                result.getCreatedAt()
        );
    }
}

