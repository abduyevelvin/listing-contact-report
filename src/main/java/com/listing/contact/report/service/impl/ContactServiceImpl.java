package com.listing.contact.report.service.impl;

import com.listing.contact.report.dto.ContactDTO;
import com.listing.contact.report.mapper.Mapper;
import com.listing.contact.report.model.Contact;
import com.listing.contact.report.repository.ContactRepository;
import com.listing.contact.report.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    @Cacheable(cacheNames = "contacts")
    public List<Contact> getContacts() {
        LOG.info("Fetching Contacts from DB");
        List<Contact> contacts = contactRepository.findAll();

        return contacts;
    }
}
