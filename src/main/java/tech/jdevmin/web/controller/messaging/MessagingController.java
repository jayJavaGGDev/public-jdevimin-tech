package tech.jdevmin.web.controller.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.jdevmin.web.jpa.user.notification.UserMessageDTO;
import tech.jdevmin.web.service.messenging.UserMessagingService;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

@Controller
@RequestMapping("/messaging")
@Slf4j
public class MessagingController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserMessagingService messagingService;

    @Autowired
    private UserAccountAccessService userAccountAccessService;


    @GetMapping("/sendTo/user")
    public String sendUserMessageForm(@RequestParam String user, Model model) {
        log.info(" messaging endpoint hit with user = {}", user);
        model.addAttribute("user",user);

        log.info("getting identity of currently active user root from userAuthService ....");
        String fromUser = userAuthService.getCurrentlyActiveUserRoot().getIdentity();
        log.info("identity of currently active user root = fromUser = {}", fromUser);

        UserMessageDTO userMessageDTO = new UserMessageDTO();

        String toUser = user;
        log.info("toUser = pathVariable = {}", toUser);



        log.info("fromUser = {}", fromUser);


        userMessageDTO.setFromUser(fromUser);

        log.info("userMessageDTO fromUser set to{}", fromUser);
        log.info("userMessageDTO toUser set to{}", toUser);

        model.addAttribute("message", userMessageDTO);

        model.addAttribute("toUser", toUser);

        return "mobile/fragments/MessageUser";

    }



    @PostMapping("/sendMessage/send/{toUser}")
    public String sendMessage(@RequestParam String toUser, @ModelAttribute("message") UserMessageDTO message) {
        log.info("send message endpoint hit");
        String fromUser = userAuthService.getCurrentlyActiveUserRoot().getIdentity();
        log.info("fromUser = {}", fromUser);
        log.info("toUser = {}", toUser);

        String subject = message.getSubject();
        String content = message.getContent();
        message.setFromUser(fromUser);
        message.setToUser(toUser);


        log.info("message:" +
                "\n" +
                "subject = " + subject +
                "content = " + content +
                "fromUser = " + fromUser +
                "toUser = " + toUser);

        log.info("accessing MessagingService.sendUserMessage >");
        messagingService.sendUserMessage(message);
        log.info("redirecting /home");

        return "redirect:/user/home";
    }
}
