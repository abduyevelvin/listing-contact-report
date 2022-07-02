package com.listing.contact.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopContactedListingDTO {
    private Long listingId;
    private String make;
    private String price;
    private String mileage;
    private Integer totalCount;
}
