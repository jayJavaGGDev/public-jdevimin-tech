package tech.jdevmin.web.service.messenging;

import tech.jdevmin.web.jpa.user.notification.UserAlert;
import tech.jdevmin.web.jpa.user.notification.UserMessage;
import tech.jdevmin.web.jpa.user.notification.UserMessageDTO;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.util.List;

public interface UserMessagingService {

    UserMessage sendMessage(String fromUser, long pu_id, String subject, String content);

    UserMessage sendUserMessage(UserMessageDTO userMessageDTO);

    UserAlert acknowledgeAlert(long id);

    UserMessage acknowledgeMessage(long id, UserRoot userRoot);

    UserAlert getAlert(long id);

    UserMessage getMessage(long id, UserRoot userRoot);

    List<UserAlert> getAllAlertsReversed();

    List<UserMessage> getAllMessagesReversed(UserRoot currentlyActiveUserRoot);

    List<UserAlert> getAllUnreadAlerts(UserRoot currentlyActiveUserRoot);


}
