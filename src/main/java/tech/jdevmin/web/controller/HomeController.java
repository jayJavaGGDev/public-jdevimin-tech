package tech.jdevmin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
@Controller
public class HomeController {

    @GetMapping
    public String home(Model model) {


        return "redirect:/login";
    }

    @GetMapping("/newHomeTest")
    public String test() {
        return "Home";
    }
}
