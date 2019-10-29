package tech.jdevmin.web.controller.user.account.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.jdevmin.web.jpa.dto.LoginRequestModel;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    UserAuthService authService;

    @PostMapping(value = "/preauth")
    public String preAuth(@ModelAttribute LoginRequestModel loginRequestModel,
                          HttpServletRequest request)
            throws IOException, ServletException, InterruptedException {

        log.info("username to test: {}", loginRequestModel.getUsername());
        log.info("password to test: {}", loginRequestModel.getPassword());
        System.out.println(loginRequestModel.getUsername());
        System.out.println(loginRequestModel.getPassword());

        String nextEndpoint = authService.authenticateUser(loginRequestModel.getUsername(), loginRequestModel.getPassword(), request);


        log.info("redirecting to post auth endpoint");

        return nextEndpoint;

    }

    @GetMapping("/postauth")
    public String postAuthentication(Model model) {


        log.info("redirected to post auth");

        UserRoot userEntity = authService.getCurrentlyActiveUserRoot();

        model.addAttribute("user", userEntity.getAccountDetails());
        return "redirect:/user/home/";
    }

    @GetMapping("/failed")
    @ResponseBody
    public String failedAttempt(){
        return "Failed Attempt!";
    }


    @GetMapping("/whichUser")
    @ResponseBody
    public String whichUser() {
        log.info("getting currently active username ");
        return authService.getCurrentlyActiveUserName();

    }

}
