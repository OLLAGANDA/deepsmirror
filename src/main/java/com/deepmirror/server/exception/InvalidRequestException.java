package com.deepmirror.server.exception;

/**
 * 잘못된 요청 시 발생하는 예외
 *
 * <p>클라이언트가 보낸 요청 데이터가 유효성 검증을 통과하지 못했을 때
 * 던져집니다. (필수 필드 누락, 잘못된 형식 등)</p>
 *
 * @author DeepMirror Team
 * @version 1.0
 */
public class InvalidRequestException extends RuntimeException {

    /**
     * 메시지만 포함하는 예외 생성
     *
     * @param message 예외 메시지
     */
    public InvalidRequestException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인을 포함하는 예외 생성
     *
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

