package tech.jdevmin.web.controller.user.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.jdevmin.web.jpa.dto.UserPostDto;
import tech.jdevmin.web.jpa.user.notification.UserAlert;
import tech.jdevmin.web.jpa.user.notification.UserMessage;
import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.friends.FriendsService;
import tech.jdevmin.web.service.messenging.UserMessagingService;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;
import tech.jdevmin.web.service.user.posts.PublicPostService;

import java.util.List;


@Controller
@RequestMapping("/user/home")
@Slf4j
public class UserHome {

    @Autowired
    private PublicPostService publicPostService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserMessagingService userMessagingService;

    @Autowired
    private UserAccountAccessService userAccountAccessService;


    @GetMapping
    public String userHome(Model model) {
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String user = userRoot.getIdentity();
        List<UserAlert> alerts = userMessagingService.getAllUnreadAlerts(userRoot);
        List<UserMessage> messages = userMessagingService.getAllMessagesReversed(userRoot);
        List<PublicPost> posts = userRoot.getPublicPosts();
        int postsSize = posts.size();
        Boolean mail = false;
        if (alerts.size() != 0 | messages.size() != 0) {
            mail = true;
        }
        int size = alerts.size();
        log.info("alerts size = {}", size);
        log.info("mail = {}", mail);
        log.info("posts size = {}", postsSize);

        model.addAttribute("postForm", new UserPostDto());
        model.addAttribute("user", user);
        model.addAttribute("mail", mail);
        model.addAttribute("posts", posts);
        model.addAttribute("currentUser", user);


        return "Home";
    }


    @GetMapping("/{user}")
    public String userProfile(@RequestParam("user") String username, Model model) {


        log.info("getting userRoot by username: {}", username);
        UserRoot userRoot = userAccountAccessService.accessAccount(username);
        log.info("getting posts reversed");
        List<PublicPost> posts = publicPostService.getAllUserPostsReversed(userRoot.getUserRootId());
        log.info("calling userRoot.getAccountDetails");
        UserAccountDetails userAccountDetails = userRoot.getAccountDetails();

        log.info("adding attributes to model: feed, account");
        model.addAttribute("feed", posts);
        model.addAttribute("account", userAccountDetails);

        log.info("returning to browser /mobile/Profile");

        return "/mobile/Profile";
    }


    @PostMapping("/{username}")
    public String otherUserProfile(@RequestParam String username, Model model) {

        model.addAttribute("feed", publicPostService.getAllUserPostsByUsername(username));
        model.addAttribute("username", username);
        return "/mobile/Profile";
    }
}
