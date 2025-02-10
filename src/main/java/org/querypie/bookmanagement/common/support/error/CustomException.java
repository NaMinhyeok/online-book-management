package org.querypie.bookmanagement.common.support.error;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    public static final CustomException INTERNAL_SERVER_ERROR =
        new CustomException(ErrorType.DEFAULT_ERROR);

    private final ErrorType errorType;

    private final Object data;

    public CustomException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = null;
    }

    public CustomException(ErrorType errorType, Object data) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.data = data;
    }
}
