package tech.jdevmin.web.service.friends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.friends.FriendsList;
import tech.jdevmin.web.jpa.user.notification.UserAlert;
import tech.jdevmin.web.jpa.user.notification.UserAlertModule;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.jpa.user.root.UserRootRepository;
import tech.jdevmin.web.service.messenging.UserMessagingService;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserMessagingService userMessagingService;


    @Autowired
    private UserRootRepository userRootRepository;


    @Autowired
    private UserAccountAccessService userAccountAccessService;


    @Override
    public void sendFriendRequest(String username) {
        UserRoot fromUser = userAuthService.getCurrentlyActiveUserRoot();

        UserAlertModule sendToUser = userAccountAccessService.accessAccount(username).getUserAlertModule();
        UserAlert userAlert = new UserAlert();

        entityManager.getTransaction().begin();

        userAlert.setModule(sendToUser);
        userAlert.setFromUsername(fromUser.getAccountDetails().getUsername());
        userAlert.setSubject(fromUser.getAccountDetails().getUsername() + " is asking to be your friend!");
        userAlert.setContent(String.valueOf(fromUser.getUserRootId()));
        userAlert.setFromUsername(fromUser.getAccountDetails().getUsername());


        sendToUser.addNewAlert(userAlert);
        entityManager.persist(userAlert);
        entityManager.getTransaction().commit();


    }

    @Override
    public void acceptFriendRequest(long id, UserRoot currentlyActiveUserRoot) {

        String currentlyActiveUserRootIdentity = currentlyActiveUserRoot.getIdentity();
        log.info("currentlyActiveUserRootIdentity = {}", currentlyActiveUserRootIdentity);
        log.info("alertId to accept is = {}", id);
        log.info("accepting friend request ....");

        UserAlert alert = userMessagingService.acknowledgeAlert(id);


        String usernameToAdd = alert.getFromUsername();
        log.info("username to add = {}", usernameToAdd);
        String flIDtoAdd = alert.getSubject();
        log.info("finding user in userRootRepository by username = {}", usernameToAdd);
        UserRoot friendToAdd = userAccountAccessService.accessAccount(usernameToAdd);


        log.info("geting friends list from friend to add");
        FriendsList friendsFriendsList = friendToAdd.getFriendsList();
        log.info("getting currently active user root , getting friends list");
        FriendsList currentlyActiveUsersFriendsList = currentlyActiveUserRoot.getFriendsList();


        // beginning transaction
        log.info("beginning first transaction -- add self to New Friends' Friends List >");
        entityManager.getTransaction().begin();
        friendsFriendsList.addFriend(currentlyActiveUserRootIdentity);
        log.info("attempting merge of first transaction");
        entityManager.merge(friendsFriendsList);
        log.info("skipping flush of first transaction");
//        entityManager.flush();
        log.info("committing first transaction");
        entityManager.getTransaction().commit();

        log.info("beginning second transaction");
        entityManager.getTransaction().begin();


        log.info("adding friend to friends list");
        currentlyActiveUsersFriendsList.addFriend(usernameToAdd);

        log.info("attempting merge of second transaction");
        entityManager.merge(currentlyActiveUsersFriendsList);
        log.info("skipping flush of second transaction");
//        entityManager.flush();


        log.info("committing second transaction ....");
        entityManager.getTransaction().commit();


        log.info("second transaction successfully committed!");

        log.info("beginning test >");

        log.info("CURRENTLY ACTIVE USER ROOTS FRIENDS LIST CONTENTS: \n");
        String currentIdentity = currentlyActiveUsersFriendsList.getUserRoot().getIdentity();
        log.info("current identity: {}", currentIdentity);



        currentlyActiveUsersFriendsList.getUsersFriends().forEach(username->{
            System.out.println(username);
        });

        String friendsIdentity = friendsFriendsList.getUserRoot().getIdentity();
        log.info("friends identity = {}", friendsIdentity);
        friendsFriendsList.getUsersFriends().forEach(username ->
                System.out.println(username));

        log.info("test complete");
        log.info("accept friend request complete! returning ....");
    }

    @Override
    public List<String> getFriendsList(UserRoot currentlyActiveUserRoot) {
        currentlyActiveUserRoot.getFriendsList();
        return userAuthService.getCurrentlyActiveUserRoot().getFriendsList().getUsersFriends();
    }

}
