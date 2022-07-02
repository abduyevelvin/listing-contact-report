package com.listing.contact.report.processor;

import com.listing.contact.report.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DBContactLogProcessor implements ItemProcessor<Contact, Contact> {
    private static final Logger LOG = LoggerFactory.getLogger(DBContactLogProcessor.class);

    @Override
    public Contact process(Contact contact) {
        LOG.info("Inserting Contact: " + contact);
        return contact;
    }
}
