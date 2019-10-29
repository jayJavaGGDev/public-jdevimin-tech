package tech.jdevmin.web.jpa.user.notification;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class UserAlert {

    @ManyToOne
    @JoinColumn
    private UserAlertModule module;

    @Column
    @Id
    @GeneratedValue
    private long alertId;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String fromUsername;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private int acknowledged;

    public UserAlert(){
        this.dateTime=LocalDateTime.now();
        this.acknowledged=0;
    }





}
