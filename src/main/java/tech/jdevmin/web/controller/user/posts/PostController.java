package tech.jdevmin.web.controller.user.posts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.jdevmin.web.jpa.dto.UserPostDto;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;
import tech.jdevmin.web.service.user.posts.PublicPostService;

@Controller
@RequestMapping("/user/posts")
@Slf4j
public class PostController {


    @Autowired
    private PublicPostService publicPostService;

    @Autowired
    private UserAccountAccessService userAccountAccessService;

    @Autowired
    private UserAuthService userAuthService;


    @GetMapping("/form")
    public String postForm(Model model) {
        model.addAttribute("newPostModel", new UserPostDto());


        return "/mobile/createNewPost";

    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute UserPostDto createPostModel) {

        log.info("inside /create ....");
        log.info("userAuthService.getCurrentlyActiveUserRoot() >");
        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String username = userRoot.getIdentity();
        log.info("currently active UserRoot username = {}", username);

        log.info("publicPostService.createPost() >");
        publicPostService.createPost(createPostModel.getSubject(), createPostModel.getContent(), userRoot);
        log.info("after creating post .... redirecting /user/home >");

        return "redirect:/user/home";
    }

    @GetMapping("/getAllPosts")
    public String getAllPosts(Model model) {

        publicPostService.getAllUserPosts(1);


        return "redirect:/";


    }

    @GetMapping("/getPost")
    public String getPost() {
        publicPostService.getPost(1, 17L);

        return "redirect:/";
    }

    @GetMapping("/deletePost")
    public String deletePost() {
        publicPostService.deletePost(1, 17L);

        return "redirect:/";
    }
}
