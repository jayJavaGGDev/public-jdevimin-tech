package tech.jdevmin.web.controller.friends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.friends.FriendsService;
import tech.jdevmin.web.service.messenging.UserMessagingService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import java.util.List;

@Controller
@RequestMapping("/friends")
@Slf4j
public class FriendsController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserMessagingService userMessagingService;

    @GetMapping
    public String friends(Model model) {
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String currentlyActiveUsername = userRoot.getIdentity();
        List<String> friendsList = userAuthService.getCurrentlyActiveUserRoot().getFriendsList().getUsersFriends();
        log.info("currently active user root = {}", currentlyActiveUsername);

        log.info("friends list size = {}", friendsList.size());
        friendsList.forEach(friend ->

                System.out.println("friends username: " + friend)
        );


        model.addAttribute("friends", friendsList);
        model.addAttribute("currentUser",currentlyActiveUsername);
        return "mobile/friends/Friends";

    }

    @GetMapping("/add/{user}")
    public String add(@RequestParam("user") String user, RedirectAttributes redirectAttributes) {
        log.info("add user endpoint hit >");
        log.info("sending request ....");
        log.info("request param = {}", user);

        friendsService.sendFriendRequest(user);
        return "redirect:/friends/addFriend";
    }

    @GetMapping("/acceptRequest/{alertId}")
    public String accept(@RequestParam("alertId") String alertId) {
        log.info("request param alert id : {}", alertId);
        long alert = Long.valueOf(alertId);
        log.info("accepting request ....");
        log.info("getting user root from userAuthService > ");
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String username = userRoot.getIdentity();
        log.info("username = {}", username);
        log.info("accepting friend request id = 22, username = {}", username);
        friendsService.acceptFriendRequest(alert, userRoot);
        log.info("redirecting to /friends");

        return "redirect:/friends";
    }

    @GetMapping("/testAccept")
    public String test() {
        log.info("in /friends/test");


        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();

        log.info("accepting request ....");
        friendsService.acceptFriendRequest(9,userRoot);

        log.info("finished accepting request.... returning /friends >");
        return "redirect:/friends";
    }


    @GetMapping("/testGetAllFriends")
    public String testGetAllFriends() {
        log.info("in /friends/test");


        log.info("asking friends service for friends list....");
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        List<String> friendsList = userRoot.getFriendsList().getUsersFriends();


        int size = friendsList.size();
        log.info("friends list size = {}", size);
        String friend1 = friendsList.get(0);
        log.info("friend = {}",friend1);
        log.info("request complete .... redirecting /friends >");
        return "redirect:/friends";
    }


    @PutMapping("/addFriend")
    public String addFriend() {

        return "redirect:/home";
    }

    @PutMapping("/acceptRequest")
    public String acceptRequest() {


        return "redirect:/home";
    }
}
