package com.listing.contact.report.service;

import com.listing.contact.report.dto.*;
import com.listing.contact.report.model.Listing;

import java.util.List;

public interface ListingService {
    List<Listing> getListings();
    List<SellerTypeAverageDTO> getAvgOfSellerType();
    List<Object[]> getTopContactedListing(Integer month, Integer year);
    List<Object[]> getMakeDistribution();
    Double getTopThirtyPercentAvg();
}
