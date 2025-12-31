package com.deepmirror.server.scheduler;

import com.deepmirror.server.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 오래된 데이터를 자동으로 정리하는 스케줄러
 *
 * <p>일정 기간이 지난 Feedback 데이터를 자동으로 삭제하여
 * 데이터베이스 용량을 관리하고 개인정보 보호 정책을 준수합니다.</p>
 *
 * <p>실행 주기: 매일 새벽 3시 (cron: 0 0 3 * * *)</p>
 * <p>삭제 기준: 생성일로부터 1년이 지난 피드백</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@Component
public class DataCleanupScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DataCleanupScheduler.class);

    private final FeedbackRepository feedbackRepository;

    /**
     * 생성자 주입을 통한 의존성 주입
     *
     * @param feedbackRepository 피드백 데이터 접근 계층
     */
    public DataCleanupScheduler(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * 1년이 지난 피드백 데이터를 삭제합니다.
     *
     * <p>매일 새벽 3시에 자동 실행되며, 트랜잭션 내에서 안전하게 삭제합니다.
     * 삭제 작업 전후로 로그를 남겨 모니터링할 수 있습니다.</p>
     *
     * <p>cron 표현식: "0 0 3 * * *"</p>
     * <ul>
     *   <li>초: 0</li>
     *   <li>분: 0</li>
     *   <li>시: 3 (새벽 3시)</li>
     *   <li>일: * (매일)</li>
     *   <li>월: * (매월)</li>
     *   <li>요일: * (모든 요일)</li>
     * </ul>
     */
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupOldFeedback() {
        logger.info("====================================================");
        logger.info("오래된 피드백 데이터 정리 작업 시작");
        logger.info("====================================================");

        try {
            // 1년 전 날짜 계산
            LocalDateTime expiryDate = LocalDateTime.now().minusYears(1);
            logger.info("삭제 기준 날짜: {} 이전", expiryDate);

            // 삭제 전 카운트 조회 (선택적)
            long totalCountBefore = feedbackRepository.count();
            logger.info("작업 전 총 피드백 개수: {}", totalCountBefore);

            // 1년이 지난 피드백 삭제
            feedbackRepository.deleteByCreatedAtBefore(expiryDate);

            // 삭제 후 카운트 조회
            long totalCountAfter = feedbackRepository.count();
            long deletedCount = totalCountBefore - totalCountAfter;

            logger.info("작업 후 총 피드백 개수: {}", totalCountAfter);
            logger.info("삭제된 피드백 개수: {}", deletedCount);
            logger.info("====================================================");
            logger.info("오래된 피드백 데이터 정리 작업 완료");
            logger.info("====================================================");

        } catch (Exception e) {
            logger.error("====================================================");
            logger.error("피드백 데이터 정리 작업 중 오류 발생", e);
            logger.error("====================================================");
            throw e;
        }
    }
}
