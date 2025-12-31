package com.deepmirror.server.dto;

/**
 * 피드백 제출 요청 DTO
 *
 * <p>클라이언트로부터 받은 사용자 피드백 정보를 담는 데이터 전송 객체입니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
public class FeedbackRequestDTO {

    /**
     * 발신자 이름
     */
    private String senderName;

    /**
     * 발신자 이메일 주소
     */
    private String email;

    /**
     * 피드백 내용
     */
    private String content;

    // 기본 생성자
    public FeedbackRequestDTO() {
    }

    // 생성자
    public FeedbackRequestDTO(String senderName, String email, String content) {
        this.senderName = senderName;
        this.email = email;
        this.content = content;
    }

    // Getters
    public String getSenderName() {
        return senderName;
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
