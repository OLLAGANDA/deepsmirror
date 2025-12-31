package com.deepmirror.server.controller;

import com.deepmirror.server.dto.FeedbackRequestDTO;
import com.deepmirror.server.dto.FeedbackResponseDTO;
import com.deepmirror.server.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 피드백 관련 REST API Controller
 *
 * <p>사용자로부터 건의사항, 문의사항, 버그 리포트 등의
 * 피드백을 수집하는 엔드포인트를 제공합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "사용자 건의 및 문의 API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * 생성자 주입
     *
     * @param feedbackService 피드백 서비스
     */
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 피드백 제출
     *
     * <p>사용자가 입력한 피드백을 검증하고 데이터베이스에 저장합니다.
     * 유효성 검사 실패 시 InvalidRequestException이 발생합니다.</p>
     *
     * @param request 발신자 정보 및 피드백 내용
     * @return 저장된 피드백 정보 및 감사 메시지
     */
    @PostMapping
    @Operation(summary = "건의사항 제출", description = "사용자의 건의사항 및 문의를 저장합니다.")
    public ResponseEntity<FeedbackResponseDTO> submitFeedback(@RequestBody FeedbackRequestDTO request) {
        FeedbackResponseDTO response = feedbackService.submitFeedback(request);
        return ResponseEntity.ok(response);
    }
}

