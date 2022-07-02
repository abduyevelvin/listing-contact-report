package com.listing.contact.report.dto;

import com.listing.contact.report.util.DoubleRounderUtil;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
public class SellerTypeAverageDTO {
    private String sellerType;
    private Double averageInEuro;

    public SellerTypeAverageDTO(String sellerType, Double averageInEuro) {
        this.sellerType = sellerType;
        this.averageInEuro = DoubleRounderUtil.roundDoubleTwoDecimal(averageInEuro);
    }
}