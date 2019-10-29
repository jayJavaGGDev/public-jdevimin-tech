package tech.jdevmin.web.controller.user.account.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.registration.AccountCreationImpl;
import tech.jdevmin.web.service.user.registration.UserAccountDetailsDTO;

@Controller
@RequestMapping("/user/account/registration")
@Slf4j
public class UserRegistrationController {

    @Autowired
    AccountCreationImpl accountCreationImpl;


    @GetMapping("/form")
    public String showRegistrationForm(Model model, RedirectAttributes redirectAttributes) {
        log.info("at /form");

        model.addAttribute("user", new UserAccountDetailsDTO());


        return "/mobile/user/account/signUp";
    }

    @PostMapping
    public String createUser(@ModelAttribute UserAccountDetailsDTO user) {

        log.info("at create user endpoint");

        UserRoot userRoot = accountCreationImpl.buildAll();

        log.info("adding user account details");

        accountCreationImpl.addUserAccountDetails(userRoot, user);


        log.info("redirecting to login endpoint");


        return "redirect:/";
    }
}
