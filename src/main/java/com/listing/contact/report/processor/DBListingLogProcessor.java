package com.listing.contact.report.processor;

import com.listing.contact.report.model.Listing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DBListingLogProcessor implements ItemProcessor<Listing, Listing> {
    private static final Logger LOG = LoggerFactory.getLogger(DBListingLogProcessor.class);

    @Override
    public Listing process(Listing listing) {
        LOG.info("Inserting Listing: " + listing);

        return listing;
    }
}
