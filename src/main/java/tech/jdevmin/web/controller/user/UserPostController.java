package tech.jdevmin.web.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.jdevmin.web.jpa.dto.UserPostDto;
import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.authentication.UserAuthService;
import tech.jdevmin.web.service.user.posts.UserPostCreationService;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@RequestMapping("/posts")
@Slf4j
public class UserPostController {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserPostCreationService userPostCreationService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/get")
    @ResponseBody
    public String getAllPostsTest() {
        log.info("in /get ....");
        log.info("userRoot = userAuthService.getCurrentlyActiveUserRoot() >");

        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        log.info("after getting userRoot");

        String username = userRoot.getIdentity();
        log.info("username = {}", username);

        log.info("getting list of public posts ....");
        List<PublicPost> posts = userRoot.getPublicPosts();

        if (posts == null) {
            log.info("posts were null!");
        } else {
            log.info("posts size = {}", posts.size());
            PublicPost post = posts.get(0);
            String content = post.getContent();
            log.info("content = {}", content);
        }


        log.info("logging posts");
        posts.forEach(publicPost -> {
            System.out.println(publicPost.getSubject() + " " + publicPost.getContent());
        });


        return "OKay";
    }

    @GetMapping("/create")
    @ResponseBody
    public String createNewPost(@ModelAttribute UserPostDto userPostDto) {
        log.info("getting currently active user root from userAuthService");


        UserRoot userRoot = userAuthService.getCurrentlyActiveUserRoot();
        String username = userRoot.getIdentity();
        log.info("userRoot identity = {}", username);

        log.info("currently active user root = {}", username);
        log.info("creating new public post....");
        PublicPost publicPost = userPostCreationService.beginCreatePublicPost(userRoot);
        String userRootIdentity = publicPost.getUserRoot().getIdentity();
        log.info("getting list of public posts through userRoot.getPublicPosts()");
        List<PublicPost> publicPosts = userRoot.getPublicPosts();

        if (publicPosts == null) {
            log.info("posts were null!");
        } else {
            log.info("posts size = {}", publicPosts.size());
            PublicPost post = publicPosts.get(0);
            String content = post.getContent();
            log.info("content = {}", content);
        }


        return publicPost.getSubject() + " " + publicPost.getContent() + "username = " + userRootIdentity;
    }
}
