package com.deepmirror.server.exception;

/**
 * JSON 직렬화/역직렬화 실패 시 발생하는 예외
 *
 * <p>ObjectMapper를 사용한 JSON 변환 과정에서
 * 오류가 발생했을 때 던져집니다.</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
public class JsonSerializationException extends RuntimeException {

    /**
     * 메시지만 포함하는 예외 생성
     *
     * @param message 예외 메시지
     */
    public JsonSerializationException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인을 포함하는 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public JsonSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}

