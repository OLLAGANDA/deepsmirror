package com.deepmirror.server.repository;

import com.deepmirror.server.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Result Entity의 데이터 접근 계층
 *
 * <p>Spring Data JPA를 사용하여 성격 분석 결과의
 * CRUD 작업을 처리합니다.</p>
 *
 * <p>제공되는 기본 메서드:</p>
 * <ul>
 *   <li>save(Result) - 결과 저장/수정</li>
 *   <li>findById(UUID) - ID로 결과 조회</li>
 *   <li>findAll() - 모든 결과 조회</li>
 *   <li>deleteById(UUID) - ID로 결과 삭제</li>
 * </ul>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
}

