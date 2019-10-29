package tech.jdevmin.web.controller.user.inbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.jpa.user.notification.UserAlert;
import tech.jdevmin.web.jpa.user.notification.UserMessage;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.messenging.UserMessagingService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import java.util.List;

@Controller
@RequestMapping("/user/inbox")
public class InboxController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserMessagingService userMessagingService;


    @GetMapping
    public String getUserAlertInbox(Model model) {
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();

        List<UserAlert> unreadAlerts = userMessagingService.getAllUnreadAlerts(userRoot);

        List<UserMessage> unreadMessages = userMessagingService.getAllMessagesReversed(userRoot);
        model.addAttribute("userRoot", userRoot);
        model.addAttribute("alerts", unreadAlerts);
        model.addAttribute("messages",unreadMessages);

        return "mobile/messaging/Inbox";


    }
}
