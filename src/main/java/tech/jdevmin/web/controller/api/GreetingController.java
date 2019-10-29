package tech.jdevmin.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.jpa.dto.Greeting;
import tech.jdevmin.web.jpa.dto.LoginRequestModel;

@Controller
@RequestMapping(value = "/greeting")
public class GreetingController {

    @GetMapping
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        model.addAttribute("loginRequestModel", new LoginRequestModel());
        return "greeting";
    }

    @PostMapping
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
        return "result";
    }

}
