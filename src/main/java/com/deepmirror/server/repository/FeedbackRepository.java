package com.deepmirror.server.repository;

import com.deepmirror.server.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Feedback Entity의 데이터 접근 계층
 *
 * <p>Spring Data JPA를 사용하여 사용자 피드백의
 * CRUD 작업을 처리합니다.</p>
 *
 * <p>제공되는 기본 메서드:</p>
 * <ul>
 *   <li>save(Feedback) - 피드백 저장</li>
 *   <li>findById(Long) - ID로 피드백 조회</li>
 *   <li>findAll() - 모든 피드백 조회</li>
 *   <li>deleteById(Long) - ID로 피드백 삭제</li>
 *   <li>deleteByCreatedAtBefore(LocalDateTime) - 특정 날짜 이전 피드백 삭제</li>
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * 특정 날짜 이전에 생성된 모든 피드백을 삭제합니다.
     *
     * <p>주로 오래된 데이터 정리 작업에 사용됩니다.
     * 스케줄러에서 자동으로 호출되며, 트랜잭션 내에서 실행되어야 합니다.</p>
     *
     * @param expiryDate 삭제 기준 날짜 (이 날짜 이전의 데이터가 삭제됨)
     */
    void deleteByCreatedAtBefore(LocalDateTime expiryDate);
}

