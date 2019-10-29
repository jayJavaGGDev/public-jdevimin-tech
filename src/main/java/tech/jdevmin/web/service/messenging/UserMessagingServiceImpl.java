package tech.jdevmin.web.service.messenging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jdevmin.web.jpa.user.notification.UserAlert;
import tech.jdevmin.web.jpa.user.notification.UserAlertModule;
import tech.jdevmin.web.jpa.user.notification.UserMessage;
import tech.jdevmin.web.jpa.user.notification.UserMessageDTO;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.jpa.user.root.UserRootRepository;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class UserMessagingServiceImpl implements UserMessagingService {

    @Autowired
    private UserRootRepository userRootRepository;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserAccountAccessService userAccountAccessService;

    @Override
    public UserMessage sendMessage(String fromUser, long pu_id, String subject, String content) {

        UserRoot userRoot = userRootRepository.findByUserRootId(pu_id);
        UserAlertModule sendToAlertModule = userRoot.getUserAlertModule();

        entityManager.getTransaction().begin();

        UserMessage userMessage = new UserMessage();
        userMessage.setModule(sendToAlertModule);
        userMessage.setFromUser(fromUser);
        userMessage.setSubject(subject);
        userMessage.setContent(content);
        sendToAlertModule.addNewMessage(userMessage);

        entityManager.persist(userMessage);
        entityManager.getTransaction().commit();

        return userMessage;


    }

    @Override
    public UserMessage sendUserMessage(UserMessageDTO userMessageDTO) {
        log.info("sending User Message");
        String fromUser = userMessageDTO.getFromUser();
        String toUser = userMessageDTO.getToUser();
        String subject = userMessageDTO.getSubject();
        String content = userMessageDTO.getContent();

        UserRoot toUserUserRoot = userAccountAccessService.accessAccount(toUser);

        UserAlertModule sendToAlertModule = toUserUserRoot.getUserAlertModule();

        log.info("transaction begin >");
        entityManager.getTransaction().begin();

        UserMessage userMessage = new UserMessage();
        userMessage.setModule(sendToAlertModule);
        userMessage.setFromUser(fromUser);
        userMessage.setSubject(subject);
        userMessage.setContent(content);
        sendToAlertModule.addNewMessage(userMessage);

        log.info("persisting userMessage");
        entityManager.persist(userMessage);
        log.info("committing transaction");
        entityManager.getTransaction().commit();
        log.info("returning userMessage");
        return userMessage;

    }

    @Override
    public UserAlert acknowledgeAlert(long alertId) {
        log.info("acknowledging alert with alertId= {}", alertId);
        UserAlert alert = getAlert(alertId);
        log.info("beginning transaction");

        entityManager.getTransaction().begin();
        alert.setAcknowledged(1);
        entityManager.merge(alert);
        entityManager.getTransaction().commit();
        return alert;
    }

    @Override
    public UserMessage acknowledgeMessage(long id, UserRoot userRoot) {

        entityManager.getTransaction().begin();
        UserMessage userMessage = getMessage(id, userRoot);
        userMessage.setAcknowledged(1);
        entityManager.merge(userMessage);
        return userMessage;
    }

    @Override
    @Transactional
    public UserAlert getAlert(long id) {
        List<UserAlert> alerts = getAllAlerts();

        Predicate<UserAlert> postFilter = alert -> alert.getAlertId() != id;
        alerts.removeIf(postFilter);

        UserAlert userAlert = alerts.get(0);

        log.info("found alert with id {}", id);
        log.info("alert: \n ");
        System.out.println(userAlert.getSubject() + ", " + userAlert.getContent() + " " + userAlert.getAlertId());


        return userAlert;


    }

    @Override
    public UserMessage getMessage(long id, UserRoot userRoot) {
        log.info("getting message by id: {}", id);
        List<UserMessage> messages = getAllMessages(userRoot);

        Predicate<UserMessage> messageFilter = message -> message.getMessageId() != id;
        messages.removeIf(messageFilter);

        UserMessage userMessage = messages.get(0);


        return userMessage;


    }


    public List<UserAlert> getAllAlertsReversed() {
        log.info("reversing all alerts");

        List<UserAlert> toReverse = getAllAlerts();
        Collections.reverse(toReverse);

        return toReverse;

    }

    public List<UserMessage> getAllMessagesReversed(UserRoot currentlyActiveUserRoot) {
        log.info("reversing all messages");

        List<UserMessage> toReverse = getAllMessages(currentlyActiveUserRoot);
        Collections.reverse(toReverse);

        return toReverse;

    }

    @Override
    public List<UserAlert> getAllUnreadAlerts(UserRoot currentlyActiveUserRoot) {
        List<UserAlert> allAlerts = currentlyActiveUserRoot.getUserAlertModule().getUserAlerts();
        Predicate<UserAlert> alertFilter = alert -> alert.getAcknowledged() == 1;
        allAlerts.removeIf(alertFilter);
        return allAlerts;
    }


    private List<UserAlert> getAllAlerts() {
        return userAuthService.getCurrentlyActiveUserRoot().getUserAlertModule().getUserAlerts();
    }

    private List<UserMessage> getAllMessages(UserRoot currentlyActiveUserRoot) {
       return currentlyActiveUserRoot.getUserAlertModule().getUserMessages();


    }


}
