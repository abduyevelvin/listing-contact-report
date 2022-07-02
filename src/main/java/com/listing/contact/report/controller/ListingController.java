package com.listing.contact.report.controller;

import com.listing.contact.report.dto.*;
import com.listing.contact.report.mapper.ListingMapper;
import com.listing.contact.report.mapper.Mapper;
import com.listing.contact.report.service.ListingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Api("Listing Controller")
public class ListingController {
    private final ListingMapper listingMapper = Mappers.getMapper(ListingMapper.class);
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/listings")
    @ApiOperation(value = "return the listings list")
    public ResponseEntity<List<ListingDTO>> getListings() {
        List<ListingDTO> listings = listingMapper.listingsToDTOs(listingService.getListings());

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/listings/avg")
    @ApiOperation(value = "return the list of the average listing seller price in euro per the seller type")
    public ResponseEntity<List<SellerTypeAverageDTO>> getAvgOfSellerType() {
        List<SellerTypeAverageDTO> sellerTypeAverageDTOS = listingService.getAvgOfSellerType();

        return new ResponseEntity<>(sellerTypeAverageDTOS, HttpStatus.OK);
    }

    @GetMapping("/listings/make")
    @ApiOperation(value = "return the list of the percentual distribution per the make")
    public ResponseEntity<List<MakeDistributionDTO>> getMakeDistribution() {
        List<MakeDistributionDTO> makeDistributionDTOS = Mapper
                .toMakeDistributionDTOS(listingService.getMakeDistribution());

        return new ResponseEntity<>(makeDistributionDTOS, HttpStatus.OK);
    }

    @GetMapping("/listings/thirty")
    @ApiOperation(value = "return the average price(format: € #,-) of the 30% most contacted listings")
    public ResponseEntity<TopThirtyPercentAvgDTO> getTopThirtyPercentAvg() {
        TopThirtyPercentAvgDTO topThirtyPercentAvgDTO = Mapper
                .toTopThirtyPercentAvgDTO(listingService.getTopThirtyPercentAvg());

        return new ResponseEntity<>(topThirtyPercentAvgDTO, HttpStatus.OK);
    }

    @GetMapping("/listings/top")
    @ApiOperation(value = "return the listings list that had more contacts in the provided month and year,",
                    notes = "need to provide month and year for getting the listings")
    public ResponseEntity<List<TopContactedListingDTO>> getTopContactedListing(
            @RequestParam("month")  Integer month,
            @RequestParam("year") Integer year) {
        List<TopContactedListingDTO> topContactedListingDTOS = Mapper
                .toTopContactedListingDTOS(listingService.getTopContactedListing(month, year));

        return new ResponseEntity<>(topContactedListingDTOS, HttpStatus.OK);
    }
}
