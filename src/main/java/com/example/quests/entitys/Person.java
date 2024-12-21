package com.example.quests.entitys;

import com.example.quests.entitys.enums.UserRoles;
import com.example.quests.exceptions.IncorrectDataException;
import com.example.quests.exceptions.IncorrectPasswordException;
import com.example.quests.exceptions.MailOrPhoneAlreadyExistsException;
import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person extends BaseEntityId {
    private String email;
    private String password;
    private UserRoles role;
    private User user;
    private Organizer organizer;

    public Person(String email, String userEmail, String password, String passwordHash, UserRoles role) {
        setEmail(email, userEmail);
        setPassword(password, passwordHash);
        setRole(role);
    }

    protected Person() {
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email == null && !email.contains("@"))
            throw new IncorrectDataException(email);
        this.email = email;
    }

    public void setEmail(String email, String userEmail) {
        if(userEmail != null)
            throw new MailOrPhoneAlreadyExistsException(email);
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword(String password, String passwordHash) {
        if(password == null || password.length() < 6 || password.length() > 40)
            throw new IncorrectPasswordException();
        this.password = passwordHash;
    }

    @Column(name = "role", nullable = false)
    @Enumerated
    public UserRoles getRole() {
        return role;
    }
    public void setRole(UserRoles role) {
        this.role = role;
    }

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
}

