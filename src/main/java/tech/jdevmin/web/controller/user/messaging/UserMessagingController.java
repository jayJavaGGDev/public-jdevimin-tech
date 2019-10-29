package tech.jdevmin.web.controller.user.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.jdevmin.web.jpa.user.notification.UserMessageDTO;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

@Controller
@RequestMapping("/messaging")
@Slf4j
public class UserMessagingController {

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping("/user/{user}")
    public String sendMessageForm(@PathVariable String user, Model model) {
        log.info("path variable = {}",user);
        log.info("sendMessageForm endpoint hit");

        log.info("building a UserPostMessageDTO");
        log.info("asking userAuthService for currently active user root > get identity ....");
        String fromUser = userAuthService.getCurrentlyActiveUserRoot().getIdentity();
        log.info("fromUser = {}", fromUser);
        String toUser = user;
        log.info("toUser = {}", toUser);

        UserMessageDTO userMessageDTO = new UserMessageDTO();
        userMessageDTO.setToUser(toUser);
        userMessageDTO.setFromUser(fromUser);


        model.addAttribute("message", userMessageDTO);

        log.info("added 'message' and fromUser/toUser");
        log.info("returning mobile/fragments/MessageUser");
        return "mobile/fragments/MessageUser";
    }

    @PostMapping("/message/{user}")
    public String sendMessage(@RequestParam String user, Model model) {

        return "mobile/fragments/MessageUser";
    }
}
