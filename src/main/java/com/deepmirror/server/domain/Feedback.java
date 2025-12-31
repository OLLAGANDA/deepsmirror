package com.deepmirror.server.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자 피드백을 저장하는 Entity
 *
 * <p>사용자가 제출한 건의사항, 문의사항, 버그 리포트 등의
 * 피드백을 데이터베이스에 저장합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Entity
@Table(name = "feedback")
public class Feedback {

    /**
     * 고유 식별자 (자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 발신자 이름
     */
    @Column(nullable = false, length = 100)
    private String senderName;

    /**
     * 발신자 이메일 주소
     */
    @Column(nullable = false, length = 255)
    private String email;

    /**
     * 피드백 내용
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 피드백 생성 일시
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * JPA 기본 생성자
     */
    public Feedback() {
    }

    /**
     * 전체 생성자
     *
     * @param senderName 발신자 이름
     * @param email 발신자 이메일
     * @param content 피드백 내용
     */
    public Feedback(String senderName, String email, String content) {
        this.senderName = senderName;
        this.email = email;
        this.content = content;
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
    public Long getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getEmail() {
        return email;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
