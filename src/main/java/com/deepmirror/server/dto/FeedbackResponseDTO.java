package com.deepmirror.server.dto;

import com.deepmirror.server.domain.Feedback;

import java.time.LocalDateTime;

/**
 * 피드백 제출 응답 DTO
 *
 * <p>피드백 제출 후 클라이언트에게 반환하는 응답 데이터를 담는 객체입니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
public class FeedbackResponseDTO {

    /**
     * 피드백 고유 식별자
     */
    private Long id;

    /**
     * 발신자 이름
     */
    private String senderName;

    /**
     * 발신자 이메일
     */
    private String email;

    /**
     * 피드백 생성 일시
     */
    private LocalDateTime createdAt;

    /**
     * 사용자에게 전달할 메시지
     */
    private String message;

    public FeedbackResponseDTO() {
    }

    public FeedbackResponseDTO(Long id, String senderName, String email, LocalDateTime createdAt, String message) {
        this.id = id;
        this.senderName = senderName;
        this.email = email;
        this.createdAt = createdAt;
        this.message = message;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Feedback Entity를 FeedbackResponseDTO로 변환하는 정적 팩토리 메서드
     *
     * @param feedback 변환할 Feedback Entity
     * @param message 사용자에게 전달할 메시지
     * @return 변환된 FeedbackResponseDTO
     */
    public static FeedbackResponseDTO fromEntity(Feedback feedback, String message) {
        return new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getSenderName(),
                feedback.getEmail(),
                feedback.getCreatedAt(),
                message
        );
    }
}

