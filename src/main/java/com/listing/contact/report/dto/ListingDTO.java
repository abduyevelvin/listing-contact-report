package com.listing.contact.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListingDTO {
    private Long id;
    private String make;
    private Double price;
    private Long mileage;
    private String sellerType;
}
