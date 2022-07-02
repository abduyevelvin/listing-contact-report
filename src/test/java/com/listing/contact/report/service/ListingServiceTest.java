package com.listing.contact.report.service;

import com.listing.contact.report.dto.SellerTypeAverageDTO;
import com.listing.contact.report.enums.APIErrorCode;
import com.listing.contact.report.enums.SellerType;
import com.listing.contact.report.exception.WrongParameterException;
import com.listing.contact.report.model.Listing;
import com.listing.contact.report.repository.ListingRepository;
import com.listing.contact.report.service.impl.ListingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ListingServiceTest {
    private ListingRepository listingRepository;
    private ListingService listingService;

    @BeforeEach
    void setUp() {
        listingRepository = Mockito.mock(ListingRepository.class);
        listingService = new ListingServiceImpl(listingRepository);
    }

    @Test
    void getListingsTest() {
        List<Listing> listings = new ArrayList<>();
        Listing firstListing = Listing.builder().id(1L).make("Audi").price(2234.56).mileage(1000L)
                .sellerType(SellerType.PRIVATE.name()).build();
        Listing secondListing = Listing.builder().id(2L).make("BMW").price(3334.56).mileage(500L)
                .sellerType(SellerType.DEALER.name()).build();
        listings.add(firstListing);
        listings.add(secondListing);

        Mockito.when(listingRepository.findAll()).thenReturn(listings);
        List<Listing> listingsFromService = listingService.getListings();

        Assertions.assertEquals(listings.size(), listingsFromService.size());
        Assertions.assertEquals(listings.get(0).getMake(), listingsFromService.get(0).getMake());
        Assertions.assertEquals(listings.get(0).getPrice(), listingsFromService.get(0).getPrice());
        Assertions.assertEquals(listings.get(1).getMileage(), listingsFromService.get(1).getMileage());
        Assertions.assertEquals(listings.get(1).getSellerType(), listingsFromService.get(1).getSellerType());
    }

    @Test
    void getAvgOfSellerTypeTest() {
        List<SellerTypeAverageDTO> sellerTypeAverageDTOS = new ArrayList<>();
        SellerTypeAverageDTO firstSellerTypeAverageDTO = SellerTypeAverageDTO.builder()
                .sellerType(SellerType.OTHER.name()).averageInEuro(23450.23).build();
        SellerTypeAverageDTO secondSellerTypeAverageDTO = SellerTypeAverageDTO.builder()
                .sellerType(SellerType.PRIVATE.name()).averageInEuro(13450.23).build();
        sellerTypeAverageDTOS.add(firstSellerTypeAverageDTO);
        sellerTypeAverageDTOS.add(secondSellerTypeAverageDTO);

        Mockito.when(listingRepository.getAvgOfSellerType()).thenReturn(sellerTypeAverageDTOS);
        List<SellerTypeAverageDTO> sellerTypeAverageDTOSFromService = listingService.getAvgOfSellerType();

        Assertions.assertEquals(sellerTypeAverageDTOS.size(), sellerTypeAverageDTOSFromService.size());
        Assertions.assertEquals(sellerTypeAverageDTOS.get(0).getSellerType(),
                sellerTypeAverageDTOSFromService.get(0).getSellerType());
        Assertions.assertEquals(sellerTypeAverageDTOS.get(1).getAverageInEuro(),
                sellerTypeAverageDTOSFromService.get(1).getAverageInEuro());
    }

    @Test
    void getTopContactedListingTest() {
        Integer month = 6;
        Integer year = 2020;
        List<Object[]> objects = new ArrayList<>();
        Object[] firstObjectArray = new Object[]{1L,"Audi", 2234.54, 20000L, 15};
        Object[] secondObjectArray = new Object[]{2L,"BMW", 3234.54, 10000L, 18};
        objects.add(firstObjectArray);
        objects.add(secondObjectArray);

        Mockito.when(listingRepository.getTopContactedListing(month, year)).thenReturn(objects);
        List<Object[]> objectsFromService = listingService.getTopContactedListing(month, year);

        Assertions.assertEquals(objects.size(), objectsFromService.size());
        Assertions.assertTrue(objectsFromService.equals(objects));
    }

    @Test
    void getTopContactedListingWrongParameterTest() {
        Integer month = 14;
        Integer year = 202;
        List<Object[]> objects = new ArrayList<>();
        Object[] firstObjectArray = new Object[]{1L,"Audi", 2234.54, 20000L, 15};
        Object[] secondObjectArray = new Object[]{2L,"BMW", 3234.54, 10000L, 18};
        objects.add(firstObjectArray);
        objects.add(secondObjectArray);

        Mockito.when(listingRepository.getTopContactedListing(month, year)).thenReturn(objects);

        Assertions.assertThrows(WrongParameterException.class,
                () -> listingService.getTopContactedListing(month, year), APIErrorCode.WRONG_PARAMETER.getMessage());
    }

    @Test
    void getMakeDistributionTest() {
        List<Object[]> objects = new ArrayList<>();
        Object[] firstObjectArray = new Object[]{"Audi", 55};
        Object[] secondObjectArray = new Object[]{"BMW", 43};
        objects.add(firstObjectArray);
        objects.add(secondObjectArray);

        Mockito.when(listingRepository.getDistributionOfMake()).thenReturn(objects);
        List<Object[]> objectsFromService = listingService.getMakeDistribution();
        Object[] firstObjectArrayFromService = objectsFromService.get(0);
        Object[] secondObjectArrayFromService = objectsFromService.get(1);

        Assertions.assertEquals(objects.size(), objectsFromService.size());
        Assertions.assertEquals(firstObjectArray[0], firstObjectArrayFromService[0]);
        Assertions.assertEquals(secondObjectArray[1], secondObjectArrayFromService[1]);
    }

    @Test
    void getTopThirtyPercentAvgTest() {
        Double topThirtyPercentAvg = 12345.67;

        Mockito.when(listingRepository.getTopThirtyPercent()).thenReturn(topThirtyPercentAvg);
        Double topThirtyPercentAvgFromService = listingService.getTopThirtyPercentAvg();

        Assertions.assertEquals(topThirtyPercentAvg, topThirtyPercentAvgFromService);
    }
}
