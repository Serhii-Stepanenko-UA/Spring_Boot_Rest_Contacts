package com.example.Spring_Boot_Rest_Contacts.sevice.contact;


import com.example.Spring_Boot_Rest_Contacts.dto.contact.ContactDtoRequest;
import com.example.Spring_Boot_Rest_Contacts.entity.contact.Contact;
import com.example.Spring_Boot_Rest_Contacts.sevice.BaseService;

import java.util.List;
import java.util.Optional;

public interface ContactService extends BaseService<Contact, ContactDtoRequest> {
    Contact create(ContactDtoRequest request);
    Optional<List<Contact>> getAll();
    Contact getById(Long id);
    Contact updateById(Long id, ContactDtoRequest request);
    boolean deleteById(Long id);
}
