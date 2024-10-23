package com.example.Spring_Boot_Rest_Contacts.sevice.contact;

import com.example.Spring_Boot_Rest_Contacts.dto.contact.ContactDtoRequest;
import com.example.Spring_Boot_Rest_Contacts.entity.contact.Contact;
import com.example.Spring_Boot_Rest_Contacts.entity.contact.ContactMapper;
import com.example.Spring_Boot_Rest_Contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service("ContactServiceImpl")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactMapper mapper;
    @Autowired
    private ContactRepository repository;

    @Override
    public Contact create(ContactDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        Contact contact = mapper.dtoCreateToEntity(request);
        return repository.save(contact);
    }

    @Override
    public Optional<List<Contact>> getAll() {
        Iterable<Contact> iterable = repository.findAll();
        // Конвертуємо Iterable в List,
        // оскільки interface CrudRepository<T, ID>
        // має саме метод Iterable<T> findAll();
        List<Contact> list =
                StreamSupport.stream(iterable.spliterator(), false)
                        .toList();
        // Запаковуємо List в Optional та повертаємо
        return Optional.of(list);
    }

    @Override
    public Contact getById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        return repository.findById(id)
                .orElse(null);
    }

    @Override
    public Contact updateById(Long id, ContactDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided!");
        }
        Optional<Contact> optional = repository.findById(id);
        if (optional.isPresent()) {
            Contact contactToUpdate =
                    mapper.dtoUpdateToEntity(id, request,
                            optional.get());
            repository.save(contactToUpdate);
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
