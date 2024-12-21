package com.example.quests.entitys;

import com.example.quests.exceptions.IncorrectDataException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntityId {
    private String name;
    private int rating;
    private String reviewText;
    private LocalDate reviewDate;
    private Booking booking;
    private boolean deleted;

    public Review(String name, int rating, String reviewText, Booking booking) {
        setName(name);
        setRating(rating);
        setReviewText(reviewText);
        setReviewDate(LocalDate.now());
        setBooking(booking);
        setDeleted(false);
    }

    protected Review() {}

    @Column(name = "author", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.length() < 2 || name.length() > 40)
            throw new IncorrectDataException(name);
        this.name = name;
    }

    @Column(name = "rating", nullable = false)
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        if (rating < 0 || rating > 5)
            throw new IncorrectDataException(String.valueOf(rating));
        this.rating = rating;
    }

    @Column(name = "review_text", nullable = false, length = 2000)
    public String getReviewText() {
        return reviewText;
    }
    public void setReviewText(String reviewText) {
        if (reviewText == null || reviewText.length() < 10 || reviewText.length() > 2000)
            throw new IncorrectDataException(reviewText);
        this.reviewText = reviewText;
    }

    @Column(name = "review_date", nullable = false)
    public LocalDate getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(LocalDate reviewDate) {
        if (reviewDate == null)
            throw new IncorrectDataException(String.valueOf(reviewDate));
        this.reviewDate = reviewDate;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
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
