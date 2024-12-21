package com.example.quests.entitys;

import com.example.quests.exceptions.IncorrectDataException;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "quests")
public class Quest extends BaseEntityId {
    private String photoUrl;
    private String name;
    private String description;
    private String location;
    private int duration;
    private int maxParticipants;
    private double price;
    private int difficulty;
    private double rating;
    private int ageRestriction;
    private String genre;
    private Organizer organizer;
    private Set<Booking> booking;
    private boolean deleted;

    public Quest(String photoUrl, String name, String description, String location, int duration, int maxParticipants, double price, int difficulty, int ageRestriction, String genre, Organizer organizer) {
        setPhotoUrl(photoUrl);
        setName(name);
        setDescription(description);
        setLocation(location);
        setDuration(duration);
        setMaxParticipants(maxParticipants);
        setPrice(price);
        setDifficulty(difficulty);
        setRating(0);
        setAgeRestriction(ageRestriction);
        setGenre(genre);
        setOrganizer(organizer);
        setDeleted(false);
    }

    protected Quest() {}

    @Column(name = "photo_url", length = 200)
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        if (photoUrl == null || photoUrl.length() < 2 || photoUrl.length() > 30)
            throw new IncorrectDataException(photoUrl);
        this.photoUrl = photoUrl;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.length() < 2 || name.length() > 200)
            throw new IncorrectDataException(name);
        this.name = name;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "location", nullable = false, length = 200)
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        if (location == null || location.length() < 2 || location.length() > 200)
            throw new IncorrectDataException(location);
        this.location = location;
    }

    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        if (duration < 15 || duration > 1440)
            throw new IncorrectDataException(location);
        this.duration = duration;
    }

    @Column(name = "max_participants", nullable = false)
    public int getMaxParticipants() {
        return maxParticipants;
    }
    public void setMaxParticipants(int maxParticipants) {
        if (maxParticipants < 1)
            throw new IncorrectDataException(String.valueOf(maxParticipants));
        this.maxParticipants = maxParticipants;
    }

    @Column(name = "price", nullable = false)
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (price < 1)
            throw new IncorrectDataException(String.valueOf(price));
        this.price = price;
    }

    @Column(name = "difficulty", nullable = false)
    public int getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(int difficulty) {
        if (difficulty < 0 || difficulty > 10)
            throw new IncorrectDataException(String.valueOf(difficulty));
        this.difficulty = difficulty;
    }

    @Column(name = "rating")
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Column(name = "age_restriction")
    public int getAgeRestriction() {
        return ageRestriction;
    }
    public void setAgeRestriction(int ageRestriction) {
        if (ageRestriction < 0)
            throw new IncorrectDataException(String.valueOf(ageRestriction));
        this.ageRestriction = ageRestriction;
    }

    @Column(name = "genre")
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        if (genre == null || genre.length() < 2 || genre.length() > 30)
            throw new IncorrectDataException(String.valueOf(ageRestriction));
        this.genre = genre;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @OneToMany(mappedBy = "quest", fetch = FetchType.LAZY)
    public Set<Booking> getBooking() {
        return booking;
    }
    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
