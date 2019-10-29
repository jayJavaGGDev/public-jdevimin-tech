package tech.jdevmin.web.controller.user.account.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.jpa.dto.LoginRequestModel;

@Controller
@RequestMapping("/login")
public class Login {

    @GetMapping
    public String login(Model model){
        model.addAttribute("request",new LoginRequestModel());
        return "mobile/home/landingPage.html";
    }
}
