package com.example.quests.services.impl;

import com.example.quests.dto.OrganizerRegistrationDto;
import com.example.quests.dto.UserRegistrationDto;
import com.example.quests.entitys.Organizer;
import com.example.quests.entitys.Person;
import com.example.quests.entitys.User;
import com.example.quests.entitys.enums.UserRoles;
import com.example.quests.repositories.OrganizerRepository;
import com.example.quests.repositories.PersonRepository;
import com.example.quests.repositories.UserRepository;
import com.example.quests.services.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, OrganizerRepository organizerRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "'email-' + #regDto.email")
    public void register(UserRegistrationDto regDto){
        if(!regDto.getPassword().equals(regDto.getConfirmPassword())){
            throw new RuntimeException("Повторный пароль не правильный");
        }
        Person person = createPerson(regDto);
        personRepository.create(person);
        User user = createUser(regDto, person);
        userRepository.create(user);
    }

    private Person createPerson(UserRegistrationDto regDto) {
        String e = personRepository.findEmail(regDto.getEmail());
        return new Person(regDto.getEmail(), e, regDto.getPassword(), passwordEncoder.encode(
                regDto.getPassword()), UserRoles.USER);
    }

    private User createUser(UserRegistrationDto regDto, Person person) {
        String p = userRepository.userPhone(regDto.getPhone());
        return new User(regDto.getName(), regDto.getPhone(), p, regDto.getPhotoUrl(), person);
    }

    @Override
    @Transactional
    public void registerOrganizer(OrganizerRegistrationDto regDto){
        if(!regDto.getPassword().equals(regDto.getConfirmPassword())){
            throw new RuntimeException("Повторный пароль не правильный");
        }
        Person person = createPerson(regDto);
        personRepository.create(person);
        Organizer organizer = createOrganizer(regDto,person);
        organizerRepository.create(organizer);
    }

    private Person createPerson(OrganizerRegistrationDto regDto) {
        String e = personRepository.findEmail(regDto.getEmail());
        return new Person(regDto.getEmail(), e, regDto.getPassword(),
                passwordEncoder.encode(regDto.getPassword()), UserRoles.ORGANIZER);
    }

    private Organizer createOrganizer(OrganizerRegistrationDto regDto, Person person) {
        String p = organizerRepository.organizerPhone(regDto.getPhone());
        return new Organizer(regDto.getName(), regDto.getPhone(), p,
                regDto.getCity(), regDto.getDescription(), regDto.getPhotoUrl(), person);
    }

}
