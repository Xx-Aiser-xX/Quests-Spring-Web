package com.example.quests.services.impl;

import com.example.quests.dto.OrganizerDto;
import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.PersonOrganizerDto;
import com.example.quests.entitys.Organizer;
import com.example.quests.entitys.Person;
import com.example.quests.entitys.enums.UserRoles;
import com.example.quests.repositories.OrganizerRepository;
import com.example.quests.repositories.PersonRepository;
import com.example.quests.services.OrganizerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository, PersonRepository personRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.organizerRepository = organizerRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void create(OrganizerRegistrationDto ord) {
        if(ord.getPassword().equals(ord.getConfirmPassword())){
            Person person = createPerson(ord);
            personRepository.create(person);
            Organizer organizer = createOrganizer(ord, person);
            organizerRepository.create(organizer);
        }
        else {
            throw new RuntimeException("Повторный пароль не правильный");
        }
    }

    private Person createPerson(OrganizerRegistrationDto ord) {
        String email = personRepository.findEmail(ord.getEmail());
        return new Person(ord.getEmail(), email, ord.getPassword(), passwordEncoder.encode(ord.getPassword()), UserRoles.ORGANIZER);
    }

    private Organizer createOrganizer(OrganizerRegistrationDto ord, Person person) {
        String phone = organizerRepository.organizerPhone(ord.getPhone());
        return new Organizer(ord.getName(), ord.getPhone(), phone, ord.getCity(), ord.getDescription(), ord.getPhotoUrl(), person);
    }

    @Override
    public Page<OrganizerDto> getAll(int page, int size) {
        Page<Organizer> organizers = organizerRepository.getEntitys(Organizer.class, page - 1, size, false);
        return organizers.map(organizer -> modelMapper.map(organizer, OrganizerDto.class));
    }

    @Override
    public OrganizerDto findById(int id) {
        Organizer organizer = organizerRepository.findById(Organizer.class, id);
        return modelMapper.map(organizer, OrganizerDto.class);
    }

    @Override
    @Transactional
    public void update(OrganizerDto ord) {
        Organizer o = organizerRepository.findById(Organizer.class, ord.getId());
        o.setName(ord.getName());
        String p = organizerRepository.organizerPhone(ord.getPhone());
        if(!p.equals(ord.getPhone()))
            o.setPhone(ord.getPhone());
        o.setCity(ord.getCity());
        o.setDescription(ord.getDescription());
        o.setRating(ord.getRating());
        o.setPhotoUrl(ord.getPhotoUrl());
        organizerRepository.update(o);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Organizer o = organizerRepository.findById(Organizer.class, id);
        o.setDeleted(true);
        organizerRepository.update(o);
    }

    @Override
    public PersonOrganizerDto findByOrganizerAndEmail(String email){
        Organizer organizer = organizerRepository.personAndOrganizer(email);
        return modelMapper.map(organizer, PersonOrganizerDto.class);
    }
}
