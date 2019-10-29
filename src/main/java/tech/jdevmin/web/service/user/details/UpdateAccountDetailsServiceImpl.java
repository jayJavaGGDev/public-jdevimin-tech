package tech.jdevmin.web.service.user.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.service.user.authentication.UserAuthService;
import tech.jdevmin.web.service.user.registration.UserAccountDetailsDTO;

import javax.persistence.EntityManager;

@Service
public class UpdateAccountDetailsServiceImpl implements UpdateUserAccountDetailsService {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private EntityManager entityManager;

    //TODO finish seperating different build stages and verifications.
    @Override
    public UserAccountDetails updateAccountDetails(UserAccountDetailsDTO DTO) {
        UserAccountDetails accountDetails = userAuthService.getCurrentlyActiveUserRoot().getAccountDetails();


        String username = DTO.getUsername();
        String password = DTO.getPassword();
        String firstName = DTO.getFirstName();
        String lastName = DTO.getLastName();
        String middleInitial = DTO.getMiddleInitial();
        String phoneNumber = DTO.getPhoneNumber();
        String secondaryEmail = DTO.getSecondaryEmail();
        String city = DTO.getCity();
        String state = DTO.getState();
        String country = DTO.getCountry();
        String birthday = DTO.getBirthday();
        String secondaryPhoneNumber = DTO.getSecondaryPhoneNumber();
        String occupation = DTO.getOccupation();

//        accountDetails.setUsername(username);
//        accountDetails.setPassword(password);
//        accountDetails.setFirstName(firstName);
//        accountDetails.setLastName(lastName);
//        accountDetails.setMiddleInitial(middleInitial;
        accountDetails.setPhoneNumber(phoneNumber);
//        accountDetails.setEmailAddress(emailAddress);
        accountDetails.setCity(city);
        accountDetails.setState(state);
        accountDetails.setCountry(country);
        accountDetails.setBirthday(birthday);
        accountDetails.setSecondaryPhoneNumber(secondaryPhoneNumber);
        accountDetails.setSecondaryEmail(secondaryEmail);
        accountDetails.setOccupation(occupation);
        accountDetails.setMiddleInitial(middleInitial);



        entityManager.getTransaction().begin();
        entityManager.merge(accountDetails);
        entityManager.getTransaction().commit();

        return accountDetails;

    }
}
