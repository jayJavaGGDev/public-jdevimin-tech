package tech.jdevmin.web.jpa.user.notification;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class UserMessage {


    @ManyToOne
    @JoinColumn
    private UserAlertModule module;

    @Column
    @Id
    @GeneratedValue
    private long messageId;

    @Column
    private String fromUser;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private int acknowledged;

    public UserMessage() {
        this.dateTime = LocalDateTime.now();
        this.acknowledged = 0;
    }
}
