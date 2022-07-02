package com.listing.contact.report.service.impl;

import com.listing.contact.report.dto.SellerTypeAverageDTO;
import com.listing.contact.report.enums.APIErrorCode;
import com.listing.contact.report.exception.WrongParameterException;
import com.listing.contact.report.model.Listing;
import com.listing.contact.report.repository.ListingRepository;
import com.listing.contact.report.service.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {
    private static final Logger LOG = LoggerFactory.getLogger(ListingServiceImpl.class);

    private final ListingRepository listingRepository;

    public ListingServiceImpl(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @Override
    @Cacheable(cacheNames = "listings", cacheManager = "alternateCacheManager")
    public List<Listing> getListings() {
        LOG.info("Fetching Listing from DB");
        List<Listing> listings = listingRepository.findAll();

        return listings;
    }

    @Override
    @Cacheable(cacheNames = "listingsAVG", cacheManager = "listingAvgCacheManager")
    public List<SellerTypeAverageDTO> getAvgOfSellerType() {
        LOG.info("Fetching the list of the average listing seller price in euro per the seller type from DB");
        List<SellerTypeAverageDTO> sellerTypeAverageDTOS = listingRepository.getAvgOfSellerType();

        return sellerTypeAverageDTOS;
    }

    @Override
    @Cacheable(cacheNames = "listingsMake", cacheManager = "listingMakeCacheManager")
    public List<Object[]> getMakeDistribution() {
        LOG.info("Fetching the list of the percentual distribution per the make from DB");
        List<Object[]> distributionOfMake = listingRepository.getDistributionOfMake();

        return distributionOfMake;
    }

    @Override
    @Cacheable(cacheNames = "listingsTopThirty", cacheManager = "listingTopThirtyCacheManager")
    public Double getTopThirtyPercentAvg() {
        LOG.info("Fetching the average price of the 30% most contacted listings from DB");
        Double topThirtyPercentAvg = listingRepository.getTopThirtyPercent();

        return topThirtyPercentAvg;
    }

    @Override
    @Cacheable(cacheNames = "listingsTop", cacheManager = "listingTopCacheManager")
    public List<Object[]> getTopContactedListing(Integer month, Integer year) {
        LOG.info("Fetching the listings list that had more contacts in the provided month: {} and year: {}"
                        ,month, year);

        if (( month < 1 || month > 12) || (Math.floor(Math.log10(year) + 1) != 4)) {
            LOG.error("Wrong Parameter month: {} or year: {}", month, year);
            throw  new WrongParameterException(APIErrorCode.WRONG_PARAMETER);
        }

        return listingRepository.getTopContactedListing(month, year);
    }
}
