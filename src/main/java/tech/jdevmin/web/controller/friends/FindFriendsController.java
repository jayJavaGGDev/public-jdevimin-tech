package tech.jdevmin.web.controller.friends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.jpa.user.root.UserRootRepository;

import java.util.List;

@RequestMapping("/friends/find")
@Controller
@Slf4j
public class FindFriendsController {

    @Autowired
    private UserRootRepository userRootRepository;

    @GetMapping
    public String findFriendsPage(Model model) {
        List<UserRoot> userRoots = userRootRepository.findAll();
        model.addAttribute("identities", userRoots);
        userRoots.forEach(i ->
                i.getIdentity());
        return "mobile/friends/FriendsList";
    }

}
