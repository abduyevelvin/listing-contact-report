package com.listing.contact.report.controller;

import com.listing.contact.report.dto.ContactDTO;
import com.listing.contact.report.mapper.Mapper;
import com.listing.contact.report.service.ContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@Api("Contact Controller")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    @ApiOperation(value = "return the contacts list")
    public ResponseEntity<List<ContactDTO>> getContacts() {
        List<ContactDTO> contacts = Mapper.toContactDTOS(contactService.getContacts());

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
