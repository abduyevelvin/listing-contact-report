package com.listing.contact.report.exception;

import com.listing.contact.report.enums.APIErrorCode;
import lombok.Data;

@Data
public class ResourceNotFoundException extends ListingReportAbstractException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(APIErrorCode apiErrorCode) {
        super(apiErrorCode.getCode(), apiErrorCode.getMessage());
    }
}
