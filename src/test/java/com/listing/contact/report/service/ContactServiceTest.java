package com.listing.contact.report.service;

import com.listing.contact.report.model.Contact;
import com.listing.contact.report.repository.ContactRepository;
import com.listing.contact.report.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ContactServiceTest {
    private ContactRepository contactRepository;
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactRepository = Mockito.mock(ContactRepository.class);
        contactService = new ContactServiceImpl(contactRepository);
    }

    @Test
    void getContactsTest() {
        List<Contact> contacts = new ArrayList<>();
        Contact firstContact = Contact.builder().id(1).listingId(11L).contactDate(1592498493000L).build();
        Contact secondContact = Contact.builder().id(2).listingId(22L).contactDate(1582474057000L).build();
        contacts.add(firstContact);
        contacts.add(secondContact);

        Mockito.when(contactRepository.findAll()).thenReturn(contacts);
        List<Contact> contactsFromService = contactService.getContacts();

        Assertions.assertEquals(contacts.size(), contactsFromService.size());
        Assertions.assertEquals(contacts.get(0).getListingId(), contactsFromService.get(0).getListingId());
        Assertions.assertEquals(contacts.get(1).getContactDate(), contactsFromService.get(1).getContactDate());
    }
}
