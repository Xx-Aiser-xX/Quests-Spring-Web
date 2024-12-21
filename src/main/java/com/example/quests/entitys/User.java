package com.example.quests.entitys;

import com.example.quests.exceptions.IncorrectDataException;
import com.example.quests.exceptions.MailOrPhoneAlreadyExistsException;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntityId {
    private String name;
    private String phone;
    private int completedQuests;
    private String photoUrl;
    private Set<Booking> booking;
    private Person person;
    private boolean deleted;

    public User(String name, String phone, String userPhone, String photoUrl, Person person) {
        setName(name);
        setPhone(phone, userPhone);
        setCompletedQuests(0);
        setPhotoUrl(photoUrl);
        setPerson(person);
        setDeleted(false);
    }

    protected User() {}

    @Column(name = "name", nullable = false, length = 30)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.length() < 2 || name.length() > 30)
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

    public void setPhone(String phone, String userPhone) {
        if (phone == null || phone.length() != 11)
            throw new IncorrectDataException(phone);
        if(userPhone != null)
            throw new MailOrPhoneAlreadyExistsException(phone);
        this.phone = phone;
    }

    @Column(name = "completed_quests")
    public int getCompletedQuests() {
        return completedQuests;
    }
    public void setCompletedQuests(int completedQuests) {
        this.completedQuests = completedQuests;
    }

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        if (photoUrl == null || photoUrl.length() < 2 || photoUrl.length() > 200)
            throw new IncorrectDataException(photoUrl);
        this.photoUrl = photoUrl;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public Set<Booking> getBooking() {
        return booking;
    }
    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
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
