package com.deepmirror.server.service;

import com.deepmirror.server.domain.Feedback;
import com.deepmirror.server.dto.FeedbackRequestDTO;
import com.deepmirror.server.dto.FeedbackResponseDTO;
import com.deepmirror.server.exception.InvalidRequestException;
import com.deepmirror.server.repository.FeedbackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedbackService {

    private static final Logger log = LoggerFactory.getLogger(FeedbackService.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * 피드백 저장
     * @param request 피드백 요청 DTO
     * @return 저장된 피드백 응답 DTO
     */
    public FeedbackResponseDTO submitFeedback(FeedbackRequestDTO request) {
        log.info("피드백 제출 요청 - 발신자: {}, 이메일: {}", request.getSenderName(), request.getEmail());

        validateFeedbackRequest(request);

        Feedback feedback = createFeedbackEntity(request);
        Feedback savedFeedback = feedbackRepository.save(feedback);

        log.info("피드백 저장 완료 - ID: {}", savedFeedback.getId());

        return FeedbackResponseDTO.fromEntity(savedFeedback, "소중한 의견 감사합니다!");
    }

    /**
     * 피드백 요청 유효성 검증
     */
    private void validateFeedbackRequest(FeedbackRequestDTO request) {
        if (request.getSenderName() == null || request.getSenderName().trim().isEmpty()) {
            throw new InvalidRequestException("발신자 이름은 필수입니다.");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new InvalidRequestException("이메일은 필수입니다.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new InvalidRequestException("내용은 필수입니다.");
        }
        if (!isValidEmail(request.getEmail())) {
            throw new InvalidRequestException("유효하지 않은 이메일 형식입니다.");
        }
    }

    /**
     * 이메일 형식 검증 (간단한 정규식)
     */
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * DTO에서 Entity 생성
     */
    private Feedback createFeedbackEntity(FeedbackRequestDTO request) {
        return new Feedback(
                request.getSenderName().trim(),
                request.getEmail().trim(),
                request.getContent().trim()
        );
    }
}
