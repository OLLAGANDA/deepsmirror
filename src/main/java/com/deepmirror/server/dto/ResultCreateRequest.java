package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Result;
import java.util.Map;

/**
 * 성격 분석 결과 생성 요청 DTO
 *
 * <p>클라이언트로부터 받은 Big Five 성격 검사 점수를 담는 데이터 전송 객체입니다.
 * 이 DTO는 프론트엔드에서 계산한 점수를 서버로 전달하는 데 사용됩니다.</p>
 *
 * <h3>점수 산정 방식:</h3>
 * <ul>
 *   <li>각 특성은 0-100 범위의 점수를 가집니다.</li>
 *   <li>120개의 IPIP-NEO 문항에 대한 응답(1-5점 Likert Scale)을 평균하여 계산됩니다.</li>
 *   <li>역채점 문항(keyed='minus')은 6-answer로 변환됩니다.</li>
 *   <li>최종 점수는 ((평균 - 1) / 4) * 100 공식으로 0-100 범위로 정규화됩니다.</li>
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 * @see Result
 */
public class ResultCreateRequest {

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
     * 세부 패싯(Facet) 점수 맵
     * 키: 도메인+패싯 조합 (예: "N1", "E2", "O3")
     * 값: 해당 패싯의 점수 (0-100)
     */
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

    /**
     * DTO를 Result Entity로 변환
     *
     * <p>aiAnalysis와 detailScores(JSON)는 서비스 계층에서
     * 처리되므로 null로 설정됩니다.</p>
     *
     * @return 변환된 Result Entity
     */
    public Result toEntity() {
        return new Result(
                this.nickname,
                this.openness,
                this.conscientiousness,
                this.extraversion,
                this.agreeableness,
                this.neuroticism,
                null,  // AI 분석은 서비스에서 생성
                null   // detailScores는 서비스에서 JSON 변환 후 설정
        );
    }
}

