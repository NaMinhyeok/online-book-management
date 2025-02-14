package org.querypie.bookmanagement.common.support.error;

import lombok.Getter;


@Getter
public class CustomException extends RuntimeException {

    public static final CustomException INTERNAL_SERVER_ERROR =
        new CustomException(ErrorType.DEFAULT_ERROR);

    public static final CustomException DATE_PARSE_ERROR =
        new CustomException(ErrorType.DATE_PARSE_ERROR);
    public static final CustomException BOOK_NOT_FOUND = new CustomException(ErrorType.BOOK_NOT_FOUND);
    public static final CustomException INVALID_EMAIL_FORMAT = new CustomException(ErrorType.INVALID_EMAIL_FORMAT);
    public static final CustomException USER_NOT_FOUND = new CustomException(ErrorType.USER_NOT_FOUND);
    public static final CustomException RENTAL_NOT_FOUND = new CustomException(ErrorType.RENTAL_NOT_FOUND);
    public static final CustomException RENTAL_BOOK_NOT_FOUND = new CustomException(ErrorType.RENTAL_BOOK_NOT_FOUND);
    public static final CustomException RENTAL_USER_NOT_MATCHED = new CustomException(ErrorType.RENTAL_USER_NOT_MATCHED);
    public static final CustomException RENTAL_BOOKS_ALREADY_RENTED = new CustomException(ErrorType.RENTAL_BOOKS_ALREADY_RENTED);

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
