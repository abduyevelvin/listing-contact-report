package com.listing.contact.report.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Listing {
    @Id
    private Long id;
    private String make;
    private Double price;
    private Long mileage;
    private String sellerType;
}