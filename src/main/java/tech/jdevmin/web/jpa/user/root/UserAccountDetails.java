package tech.jdevmin.web.jpa.user.root;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class UserAccountDetails {

    @Id
    @GeneratedValue
    private long uacID;

    @OneToOne
    private UserRoot userRoot;

    @Column
    private LocalDate date;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String middleInitial;

    @Column
    private String lastName;

    @Column
    private String phoneNumber;

    @Column
    private String emailAddress;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String birthday;

    @Column
    private String secondaryEmail;

    @Column
    private String secondaryPhoneNumber;

    @Column
    private String occupation;



    public UserAccountDetails(){
        this.date = LocalDate.now();


    }
}
