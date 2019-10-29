package tech.jdevmin.web.jpa.user.notification;

import lombok.Data;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class UserAlertModule {

    @Id
    @GeneratedValue
    private long uamID;

    @OneToOne
    private UserRoot userRoot;

    @OneToMany(mappedBy = "module")
    private List<UserAlert> userAlerts;

    @OneToMany(mappedBy = "module")
    private List<UserMessage> userMessages;

    public void addNewAlert(UserAlert userAlert){
        userAlerts.add(userAlert);
    }

    public void addNewMessage(UserMessage userMessage){
        userMessages.add(userMessage);
    }
}
