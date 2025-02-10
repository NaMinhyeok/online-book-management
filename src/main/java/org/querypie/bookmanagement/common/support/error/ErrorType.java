package org.querypie.bookmanagement.common.support.error;

import lombok.Getter;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {
    DEFAULT_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E500, "예기치 않은 오류가 발생했습니다.", LogLevel.ERROR),
    METHOD_ARGUMENT_NOT_VALID(
        HttpStatus.BAD_REQUEST, ErrorCode.E400, "입력하신 데이터가 올바르지 않습니다.", LogLevel.DEBUG),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "입력 형식이 올바르지 않습니다.", LogLevel.WARN),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "요청하신 리소스를 찾을 수 없습니다.", LogLevel.WARN),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.E405, "지원하지 않는 메소드입니다.", LogLevel.WARN),

    DATE_PARSE_ERROR(HttpStatus.BAD_REQUEST, ErrorCode.E400, "날짜 형식이 올바르지 않습니다.", LogLevel.WARN),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "해당 도서를 찾을 수 없습니다. ", LogLevel.WARN);

    private final HttpStatus status;

    private final ErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }
}
