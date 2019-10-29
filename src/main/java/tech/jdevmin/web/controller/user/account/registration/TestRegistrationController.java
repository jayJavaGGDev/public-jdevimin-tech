package tech.jdevmin.web.controller.user.account.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.service.user.authentication.UserAuthService;
import tech.jdevmin.web.service.user.registration.AccountCreationService;

@Controller
@RequestMapping("/user/account/registration")
public class TestRegistrationController {

    @Autowired
    AccountCreationService accountCreationService;

    @Autowired
    UserAuthService authService;

    @GetMapping("/test")
    private String createUser() {
        accountCreationService.createNewTestAccounts();

        return "/landingPage";

    }

    @GetMapping("/addDetails")
    private String addUserAccountDetails() {

//        createNewUserAccountService.addUserAccountDetails(
//                authService.getUserAccountDetailsById(2).get().getUserRoot(),"admin","admin");
        return "/mobile/DesktopUserHome";
    }
}
