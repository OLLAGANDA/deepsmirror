package com.deepmirror.server.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 성격 분석 결과를 저장하는 Entity
 *
 * <p>Big Five 성격 이론(개방성, 성실성, 외향성, 친화성, 신경성)에 기반한
 * 성격 검사 결과와 AI 분석 내용을 저장합니다.</p>
 *
 * <h3>Big Five 성격 특성:</h3>
 * <ul>
 *   <li><b>개방성(Openness)</b>: 새로운 경험과 아이디어에 대한 개방성</li>
 *   <li><b>성실성(Conscientiousness)</b>: 목표 지향성과 책임감</li>
 *   <li><b>외향성(Extraversion)</b>: 사회적 상호작용과 에너지 수준</li>
 *   <li><b>친화성(Agreeableness)</b>: 타인에 대한 공감과 협력성</li>
 *   <li><b>신경성(Neuroticism)</b>: 정서적 안정성과 스트레스 대처</li>
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Entity
@Table(name = "results")
public class Result {

    /**
     * 고유 식별자 (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * 사용자 닉네임
     */
    @Column(nullable = false)
    private String nickname;

    /**
     * 개방성 점수 (0-100)
     * 높을수록 새로운 경험과 아이디어에 개방적
     */
    @Column(nullable = false)
    private int openness;

    /**
     * 성실성 점수 (0-100)
     * 높을수록 목표 지향적이고 책임감이 강함
     */
    @Column(nullable = false)
    private int conscientiousness;

    /**
     * 외향성 점수 (0-100)
     * 높을수록 사교적이고 활동적
     */
    @Column(nullable = false)
    private int extraversion;

    /**
     * 친화성 점수 (0-100)
     * 높을수록 타인에게 공감적이고 협력적
     */
    @Column(nullable = false)
    private int agreeableness;

    /**
     * 신경성 점수 (0-100)
     * 높을수록 정서적으로 불안정하고 스트레스에 취약
     */
    @Column(nullable = false)
    private int neuroticism;

    /**
     * AI가 생성한 성격 분석 결과 (자연어)
     */
    @Column(columnDefinition = "TEXT")
    private String aiAnalysis;

    /**
     * 세부 점수 데이터 (JSON 형식)
     * 각 성격 특성의 하위 패싯(Facet) 점수를 포함
     */
    @Column(columnDefinition = "TEXT")
    private String detailScores;

    /**
     * 결과 생성 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * JPA 기본 생성자 (protected로 외부 생성 방지)
     */
    protected Result() {
    }

    /**
     * 전체 생성자
     *
     * @param nickname 사용자 닉네임
     * @param openness 개방성 점수 (0-100)
     * @param conscientiousness 성실성 점수 (0-100)
     * @param extraversion 외향성 점수 (0-100)
     * @param agreeableness 친화성 점수 (0-100)
     * @param neuroticism 신경성 점수 (0-100)
     * @param aiAnalysis AI 분석 결과 텍스트
     * @param detailScores 세부 점수 JSON 문자열
     */
    public Result(String nickname, int openness, int conscientiousness,
                  int extraversion, int agreeableness, int neuroticism,
                  String aiAnalysis, String detailScores) {
        this.nickname = nickname;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
        this.aiAnalysis = aiAnalysis;
        this.detailScores = detailScores;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 엔티티 저장 전 생성일시 자동 설정
     */
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

    // Setter (필요한 경우에만 사용)
    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public void setDetailScores(String detailScores) {
        this.detailScores = detailScores;
    }
}
