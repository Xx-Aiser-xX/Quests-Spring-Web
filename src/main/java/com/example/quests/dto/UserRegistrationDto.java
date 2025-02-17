package com.example.quests.dto;

public class UserRegistrationDto {
    private String name;
    private String email;
    private String phone;
    private String photoUrl;
    private String password;
    private String confirmPassword;

    public UserRegistrationDto(String name, String email, String phone, String photoUrl, String password, String confirmPassword) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
