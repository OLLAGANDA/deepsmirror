package com.deepmirror.server.repository;

import com.deepmirror.server.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}

