package com.example.Spring_Boot_Rest_Contacts.controller;

import com.example.Spring_Boot_Rest_Contacts.dto.contact.*;
import com.example.Spring_Boot_Rest_Contacts.entity.contact.Contact;
import com.example.Spring_Boot_Rest_Contacts.sevice.contact.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

// @RestController - комбінація @Controller і @ResponseBody,
// що означає, що замість рендерингу сторінок він просто відповідає
// даними, які ми йому надали.
// Це природно для REST API, повертати інформацію після
// потрапляння в кінцеву точку API.
// @GetMapping, @DeleteMapping, @PostMapping
// зазначають типи HTTP-запитів, які оброблюють методи.
// Це похідні варіанти анотації @RequestMapping з методом
// RequestMethod.METHOD, встановленим для відповідних типів.
// @RequestMapping зіставляє REST-запити з контролером або
// методами оброблювача.
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {

    @Autowired
    private ContactServiceImpl service;

    @PostMapping
    public ResponseEntity<ContactDtoCreateResponse> createContact(
            @RequestBody ContactDtoRequest request) {
        Contact contact = service.create(request);
        // ternary operator usage
        return (contact != null) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoCreateResponse.of(true,
                                contact)) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoCreateResponse.of(false,
                                null));
    }

    @GetMapping
    public ResponseEntity<ContactDtoListResponse> getAllContacts() {
        Optional<List<Contact>> optional = service.getAll();
        if (optional.isPresent()) {
            List<Contact> list = optional.get();
            return (!list.isEmpty()) ?
                    ResponseEntity.status(HttpStatus.OK)
                            .body(ContactDtoListResponse.of(false,
                                    list)) :
                    ResponseEntity.status(HttpStatus.OK)
                            .body(ContactDtoListResponse.of(true,
                                    Collections.emptyList()));
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ContactDtoListResponse.of(true,
                            Collections.emptyList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDtoGetByIdResponse> getContactById(
            @PathVariable("id") Long id) {
        Contact contact = service.getById(id);
        return (contact != null) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoGetByIdResponse.of(id, true,
                                contact)) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoGetByIdResponse.of(id, false,
                                null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDtoUpdateResponse> updateContactById(
            @PathVariable("id") Long id,
            @RequestBody ContactDtoRequest request) {
        return (service.getById(id) != null) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoUpdateResponse.of(id, true,
                                service.updateById(id, request))) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoUpdateResponse.of(id, false,
                                null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactDtoDeleteResponse> deleteContactById(
            @PathVariable(value = "id") Long id) {
        return (service.deleteById(id)) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoDeleteResponse.of(id, true)) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ContactDtoDeleteResponse.of(id, false));
    }
}
