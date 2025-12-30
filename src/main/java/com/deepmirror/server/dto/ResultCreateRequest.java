package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Result;
import java.util.Map;

public class ResultCreateRequest {

    private String nickname;
    private int openness;
    private int conscientiousness;
    private int extraversion;
    private int agreeableness;
    private int neuroticism;
    private Map<String, Integer> detailScores;

    // 기본 생성자
    public ResultCreateRequest() {
    }

    // 전체 생성자
    public ResultCreateRequest(String nickname, int openness, int conscientiousness,
                               int extraversion, int agreeableness, int neuroticism, Map<String, Integer> detailScores) {
        this.nickname = nickname;
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extraversion = extraversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
        this.detailScores = detailScores;
    }

    // Getters
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

    public Map<String, Integer> getDetailScores() {
        return detailScores;
    }

    // Setters (JSON 역직렬화를 위해 필요)
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

    public void setDetailScores(Map<String, Integer> detailScores) {
        this.detailScores = detailScores;
    }

    // DTO -> Entity 변환
    public Result toEntity() {
        return new Result(
                this.nickname,
                this.openness,
                this.conscientiousness,
                this.extraversion,
                this.agreeableness,
                this.neuroticism,
                null,  // aiAnalysis는 아직 생성 전
                null   // detailScores는 서비스에서 JSON 변환 후 설정
        );
    }
}
