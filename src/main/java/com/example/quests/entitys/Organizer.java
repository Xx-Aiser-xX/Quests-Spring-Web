package com.example.quests.entitys;

import com.example.quests.exceptions.IncorrectDataException;
import com.example.quests.exceptions.MailOrPhoneAlreadyExistsException;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "organizers")
public class Organizer extends BaseEntityId {
    private String name;
    private String phone;
    private String city;
    private String description;
    private double rating;
    private String photoUrl;
    private Set<Quest> quest;
    private Person person;
    private boolean deleted;

    public Organizer(String name, String phone, String organizerPhone, String city, String description, String photoUrl, Person person) {
        setName(name);
        setPhone(phone, organizerPhone);
        setCity(city);
        setDescription(description);
        setRating(0);
        setPhotoUrl(photoUrl);
        setPerson(person);
        setDeleted(false);
    }

    protected Organizer() {}

    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(name == null || name.length() < 2 || name.length() > 100)
            throw new IncorrectDataException(name);
        this.name = name;
    }

    @Column(name = "phone", nullable = false, unique = true, length = 11)
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        if (phone == null || phone.length() != 11)
            throw new IncorrectDataException(phone);
        this.phone = phone;
    }

    public void setPhone(String phone, String organizerPhone) {
        if (phone == null || phone.length() != 11)
            throw new IncorrectDataException(phone);
        if(organizerPhone != null)
            throw new MailOrPhoneAlreadyExistsException(phone);
        this.phone = phone;
    }

    @Column(name = "city", nullable = false, length = 40)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        if (city == null || city.length() < 2 || city.length() > 40)
            throw new IncorrectDataException(city);
        this.city = city;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        if (description == null || description.length() < 20)
            throw new IncorrectDataException(description);
        this.description = description;
    }

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(name = "photo_url", length = 150)
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        if (photoUrl != null && photoUrl.length() > 150)
            throw new IncorrectDataException(photoUrl);
        this.photoUrl = photoUrl;
    }

    @OneToMany(mappedBy = "organizer", fetch = FetchType.LAZY)
    public Set<Quest> getQuest() {
        return quest;
    }
    public void setQuest(Set<Quest> quest) {
        this.quest = quest;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
