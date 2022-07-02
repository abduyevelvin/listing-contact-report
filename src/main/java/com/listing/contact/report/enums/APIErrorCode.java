package com.listing.contact.report.enums;

import lombok.Getter;

@Getter
public enum APIErrorCode {

    RESOURCE_NOT_FOUND(111, "resource_not_found"),
    WRONG_PARAMETER(222, "wrong_parameter"),
    WRONG_DOUBLE_FORMAT(333, "wrong_double_format");

    private int code;
    private String message;

    APIErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
