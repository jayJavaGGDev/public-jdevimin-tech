package tech.jdevmin.web.service.user.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.friends.FriendsList;
import tech.jdevmin.web.jpa.user.notification.UserAlertModule;
import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class AccountCreationImpl implements AccountCreationService{


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserRoot buildAll() {
        UserRoot userRoot = createNewUserRoot();
        createNewUserAccountDetails(userRoot);
        createNewFriendsList(userRoot);
        createNewUserAlertModule(userRoot);
        return userRoot;


    }


    public UserRoot createNewUserRoot() {
        entityManager.getTransaction().begin();
        UserRoot userRoot = new UserRoot();
        entityManager.persist(userRoot);
        entityManager.getTransaction().commit();
        return userRoot;
    }

    public UserRoot createNewUserAccountDetails(UserRoot userRoot) {
        entityManager.getTransaction().begin();
        UserAccountDetails userAccountDetails = new UserAccountDetails();
        userRoot.setAccountDetails(userAccountDetails);
        userAccountDetails.setUserRoot(userRoot);
        entityManager.persist(userAccountDetails);
        entityManager.getTransaction().commit();
        return userRoot;
    }

    public UserRoot createNewFriendsList(UserRoot userRoot) {
        entityManager.getTransaction().begin();
        FriendsList friendsList = new FriendsList();
        friendsList.setUserRoot(userRoot);
        userRoot.setFriendsList(friendsList);
        entityManager.persist(friendsList);
        entityManager.getTransaction().commit();


        return userRoot;
    }

    public UserRoot createNewUserAlertModule(UserRoot userRoot) {
        entityManager.getTransaction().begin();
        UserAlertModule userAlertModule = new UserAlertModule();
        userAlertModule.setUserRoot(userRoot);
        userRoot.setUserAlertModule(userAlertModule);
        entityManager.persist(userAlertModule);
        entityManager.getTransaction().commit();


        return userRoot;
    }


    public UserRoot addUserAccountDetails(UserRoot userRoot, UserAccountDetailsDTO userAccountDetailsDTO) {
        log.info("adding user account details -- transaction begin ....");
        entityManager.getTransaction().begin();
        UserAccountDetails accountDetails = userRoot.getAccountDetails();

        accountDetails.setUsername(userAccountDetailsDTO.getUsername());

        accountDetails.setPassword(bCryptPasswordEncoder.encode(userAccountDetailsDTO.getPassword()));
        accountDetails.setFirstName(userAccountDetailsDTO.getFirstName());
        accountDetails.setLastName(userAccountDetailsDTO.getLastName());
        accountDetails.setPhoneNumber(userAccountDetailsDTO.getPhoneNumber());
        accountDetails.setEmailAddress(userAccountDetailsDTO.getEmailAddress());
        accountDetails.setCity(userAccountDetailsDTO.getCity());
        accountDetails.setState(userAccountDetailsDTO.getState());
        accountDetails.setCountry(userAccountDetailsDTO.getCountry());

        log.info("persisting account details ....");
        entityManager.persist(accountDetails);
        log.info("committing account details ....");
        entityManager.getTransaction().commit();

        setUsername(userRoot);
        return userRoot;
    }

    private UserRoot setUsername(UserRoot userRoot) {
        log.info("setting username of UserRoot");
        String username = userRoot.getAccountDetails().getUsername();
        log.info("username = {}",username);
        log.info("transaction begin ....");
        entityManager.getTransaction().begin();
        userRoot.setIdentity(username);
        log.info("merging user root ....");
        entityManager.merge(userRoot);
        log.info("committing transaction ....");
        entityManager.getTransaction().commit();
        log.info("returning user root ....");
        return userRoot;
    }

//    private UserRoot createPublicPostList(UserRoot userRoot){
//
//        entityManager.getTransaction().begin();
//
//
//    }


    public void create() {


        entityManager.getTransaction().begin();


        System.out.println("\n===QUERY START===\n");

        /*BEGIN*/

        /*Building User Entity And All Related Components*/

        // --  STEP ONE --
        // Create a new UserRoot
        UserRoot userRoot = new UserRoot();
        // PERSIST IT
        // close connection and redirect.
        // Create a new UserAccountDetails
        UserAccountDetails accountDetails = new UserAccountDetails();
        // PERSIST IT
        // close connection and redirect
        // Set them to eachother
        accountDetails.setUserRoot(userRoot);
        userRoot.setAccountDetails(accountDetails);

        // build the account details from form input
        accountDetails.setUsername("admin");
        accountDetails.setPassword("admin");
        accountDetails.setFirstName("admin");
        accountDetails.setLastName("admin");
        accountDetails.setPhoneNumber("8888888888");
        accountDetails.setEmailAddress("email@email.com");
        accountDetails.setCity("city");
        accountDetails.setState("state");
        accountDetails.setCountry("country");
        //  PERSIST IT

        // create a new Friends list
        FriendsList friendsList = new FriendsList();
        // PERSIST IT
        // close connection and redirect
        // grab the userRoot and set the friends list to it
        userRoot.setFriendsList(friendsList);
        // set the user root to the friends list
        friendsList.setUserRoot(userRoot);

        // create a new UserAlertModule
        UserAlertModule alertModule = new UserAlertModule();
        alertModule.setUserRoot(userRoot);
        // PERSIST IT
        // close connection and redirect
        //grab the rootUser and add the alertModule to it
        userRoot.setUserAlertModule(alertModule);


        // create a new public identity

        // PERSIST IT
        // close connection and redirect
        // grab the user root and set the public identity to it

        // set the user root the the public identity


        entityManager.persist(userRoot);
        entityManager.persist(accountDetails);
        entityManager.persist(friendsList);
        entityManager.persist(alertModule);

        entityManager.getTransaction().

                commit();

        /*CLOSE*/

        System.out.println("\n===QUERY END===\n");

    }

    @Override
    public UserRoot createNewAccount(UserAccountDetailsDTO accountDetailsDTO) {
        return null;
    }



    @Override
    public void createNewTestAccounts() {
        UserAccountDetailsDTO user1 = new UserAccountDetailsDTO();
        user1
                .setUsername("user")
                .setPassword("user")
                .setFirstName("userFirstname")
                .setMiddleInitial("userMiddlename")
                .setLastName("userLastname")
                .setEmailAddress("user1@email.com")
                .setBirthday("Jan 1 1991")
                .setCity("phoenix")
                .setState("az")
                .setCountry("usa")
                .setOccupation("worker");
        UserAccountDetailsDTO user2 = new UserAccountDetailsDTO();
        user2
                .setUsername("user2")
                .setPassword("user2")
                .setFirstName("user2Firstname")
                .setMiddleInitial("user2Middlename")
                .setLastName("user2Lastname")
                .setEmailAddress("user2@email.com")
                .setBirthday("Jan 1 1991")
                .setCity("denver")
                .setState("colorado")
                .setCountry("usa")
                .setOccupation("worker");
        UserRoot userRoot1 = buildAll();
        UserRoot userRoot2 = buildAll();
        addUserAccountDetails(userRoot1,user1);
        addUserAccountDetails(userRoot2,user2);
    }
}
