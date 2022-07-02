package com.listing.contact.report.exception;

import com.listing.contact.report.enums.APIErrorCode;
import lombok.Data;

@Data
public class WrongParameterException extends ListingReportAbstractException {
    private static final long serialVersionUID = 1L;

    public WrongParameterException(APIErrorCode apiErrorCode) {
        super(apiErrorCode.getCode(), apiErrorCode.getMessage());
    }
}
