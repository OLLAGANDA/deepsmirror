package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Result;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResultResponse {

    private UUID id;
    private String clientIp;
    private int openness;
    private int conscientiousness;
    private int extraversion;
    private int agreeableness;
    private int neuroticism;
    private String aiAnalysis;
    private String detailScores;
    private LocalDateTime createdAt;

    // 기본 생성자
    public ResultResponse() {
    }

    // 전체 생성자
    public ResultResponse(UUID id, String clientIp, int openness, int conscientiousness,
                          int extraversion, int agreeableness, int neuroticism,
                          String aiAnalysis, String detailScores, LocalDateTime createdAt) {
        this.id = id;
        this.clientIp = clientIp;
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

    public String getClientIp() {
        return clientIp;
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

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
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

    // Entity -> DTO 변환 (정적 팩토리 메서드)
    public static ResultResponse fromEntity(Result result) {
        return new ResultResponse(
                result.getId(),
                result.getClientIp(),
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
