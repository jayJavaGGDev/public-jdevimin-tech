package tech.jdevmin.web.jpa.user.notification;

import lombok.Data;

@Data
public class UserMessageDTO {

    private String subject;
    private String content;

    private String fromUser;
    private String toUser;

}
