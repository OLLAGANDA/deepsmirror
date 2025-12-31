package com.deepmirror.server.exception;

/**
 * AI 분석 과정에서 발생하는 예외
 *
 * <p>Gemini API 호출 실패, 응답 파싱 오류,
 * 네트워크 문제 등 AI 분석과 관련된 모든 오류를 포함합니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
public class AiAnalysisException extends RuntimeException {

    /**
     * 메시지만 포함하는 예외 생성
     *
     * @param message 예외 메시지
     */
    public AiAnalysisException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인을 포함하는 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public AiAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}

