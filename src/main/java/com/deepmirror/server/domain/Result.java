package com.deepmirror.server.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String clientIp;

    @Column(nullable = false)
    private int openness;

    @Column(nullable = false)
    private int conscientiousness;

    @Column(nullable = false)
    private int extraversion;

    @Column(nullable = false)
    private int agreeableness;

    @Column(nullable = false)
    private int neuroticism;

    @Column(columnDefinition = "TEXT")
    private String aiAnalysis;

    @Column(columnDefinition = "TEXT")
    private String detailScores;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // JPA 기본 생성자 (protected)
    protected Result() {
    }

    // 전체 생성자
    public Result(String clientIp, int openness, int conscientiousness,
                  int extraversion, int agreeableness, int neuroticism, String aiAnalysis, String detailScores) {
        this.clientIp = clientIp;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
        this.aiAnalysis = aiAnalysis;
        this.detailScores = detailScores;
        this.createdAt = LocalDateTime.now();
    }

    // 생성 시점 자동 설정
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
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

    // Setter (필요한 경우에만 사용)
    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public void setDetailScores(String detailScores) {
        this.detailScores = detailScores;
    }
}
