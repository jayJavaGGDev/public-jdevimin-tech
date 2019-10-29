package tech.jdevmin.web.controller.user.home;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;

import java.util.List;

@Controller
@RequestMapping("/user/profile")
@Slf4j
public class UserProfileController {

    @Autowired
    private UserAccountAccessService userAccountAccessService;

    @GetMapping("/{user}")
    public String getUserProfile(@RequestParam("user") String username, Model model) {
        log.info("user profile endpoint hit >");
        UserRoot accountOwner = userAccountAccessService.accessAccount(username);
        List<PublicPost> posts = accountOwner.getPublicPosts();

        model.addAttribute("username", username);
        model.addAttribute("feed",posts);


        log.info("username = {}", username);
        log.info("returning Profile.html");

        return "/mobile/user/home/Profile";

    }

}
