package tech.jdevmin.web.service.user.registration;

import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.time.LocalDate;

public class UserAccountDetailsDTO {


    private long uacID;

    private UserRoot userRoot;


    private LocalDate date;


    private String username;

    private String password;


    private String firstName;


    private String middleInitial;


    private String lastName;

    private String phoneNumber;


    private String emailAddress;


    private String city;


    private String state;


    private String country;


    private String birthday;


    private String secondaryEmail;


    private String secondaryPhoneNumber;


    private String occupation;

    public long getUacID() {
        return uacID;
    }

    public UserAccountDetailsDTO setUacID(long uacID) {
        this.uacID = uacID;
        return this;
    }

    public UserRoot getUserRoot() {
        return userRoot;
    }

    public UserAccountDetailsDTO setUserRoot(UserRoot userRoot) {
        this.userRoot = userRoot;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public UserAccountDetailsDTO setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAccountDetailsDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAccountDetailsDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserAccountDetailsDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public UserAccountDetailsDTO setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAccountDetailsDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserAccountDetailsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public UserAccountDetailsDTO setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserAccountDetailsDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public UserAccountDetailsDTO setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserAccountDetailsDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public UserAccountDetailsDTO setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public UserAccountDetailsDTO setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
        return this;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public UserAccountDetailsDTO setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        return this;
    }

    public String getOccupation() {
        return occupation;
    }

    public UserAccountDetailsDTO setOccupation(String occupation) {
        this.occupation = occupation;
        return this;
    }
}
