package com.listing.contact.report.mapper;

import com.listing.contact.report.dto.ListingDTO;
import com.listing.contact.report.model.Listing;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ListingMapper {
    ListingDTO getListingDto(Listing listing);
    Listing getListing(ListingDTO listingDTO);
    List<ListingDTO> listingsToDTOs(List<Listing> listings);
}
