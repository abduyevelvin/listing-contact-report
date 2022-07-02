package com.listing.contact.report.exception;

import lombok.Data;

@Data
public class ListingReportAbstractException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    protected final Integer errorCode;
    protected final String message;

    public ListingReportAbstractException(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
