package tech.jdevmin.web.controller.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import java.util.List;

@RequestMapping("/fragments")
@Controller
@Slf4j
public class FragmentTestController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserAccountAccessService userAccountAccessService;

    @GetMapping
    public String fragmentTest(Model model) {
        log.info("asking userAuthService for currently active user root");
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String identity = userRoot.getIdentity();
        log.info("getting freinds list ....");
        List<String> friendsList = userRoot.getFriendsList().getUsersFriends();


        model.addAttribute("identity",identity);


        model.addAttribute("friends",friendsList);

        return "Main";
    }
}
