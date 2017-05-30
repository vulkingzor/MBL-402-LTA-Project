package edu.phoenix.mbl402.LTA;

/**
 * Created by usyag on 5/29/2017.
 */

public class User {

    private String userID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public User(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
