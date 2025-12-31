package com.deepmirror.server.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리기
 *
 * <p>애플리케이션 전체에서 발생하는 예외를 한 곳에서 처리합니다.
 * 각 예외 타입에 맞는 HTTP 상태 코드와 에러 메시지를 반환합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonSerializationException.class)
    public ResponseEntity<Map<String, Object>> handleJsonSerializationException(JsonSerializationException ex) {
        log.error("JSON 직렬화 오류 발생: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "데이터 변환 중 오류가 발생했습니다.",
                ex.getMessage()
        );
    }

    @ExceptionHandler(AiAnalysisException.class)
    public ResponseEntity<Map<String, Object>> handleAiAnalysisException(AiAnalysisException ex) {
        log.error("AI 분석 오류 발생: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "AI 분석 서비스에 일시적인 문제가 발생했습니다.",
                ex.getMessage()
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidRequestException(InvalidRequestException ex) {
        log.warn("잘못된 요청: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "잘못된 요청입니다.",
                ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("예상치 못한 오류 발생: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "서버 내부 오류가 발생했습니다.",
                "관리자에게 문의해주세요."
        );
    }

    /**
     * 에러 응답 객체 생성
     *
     * <p>일관된 형식의 에러 응답을 생성합니다.</p>
     *
     * @param status HTTP 상태 코드
     * @param message 사용자에게 보여줄 메시지
     * @param details 상세 오류 정보
     * @return 구조화된 에러 응답
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String message, String details) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("details", details);

        return ResponseEntity.status(status).body(errorResponse);
    }
}

