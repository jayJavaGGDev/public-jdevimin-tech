package tech.jdevmin.web.controller.groups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.groups.CreateGroupPostDTO;
import tech.jdevmin.web.service.groups.GroupPostService;
import tech.jdevmin.web.service.groups.GroupsService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

@Controller
@Slf4j
@RequestMapping("/groups/post")
public class GroupPostController {

    @Autowired
    private GroupPostService groupPostService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping("/{postToGroupId}")
    public String postToGroup(@RequestParam("postToGroupId") String postToGroupId, @ModelAttribute CreateGroupPostDTO createGroupPostDTO) {
        log.info("post to group id endpoint hit!");

        log.info("asking userAuthService for currentlyActiveUserRoot >");
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();

        log.info("asking groupsService to get chatGroupByGroupId = {}", postToGroupId);
        PublicChatGroup currentlyActiveChatGroup = groupsService.getChatGroupByID(Long.valueOf(postToGroupId));
        String chatGroupName = currentlyActiveChatGroup.getChatGroupName();
        log.info("found chatGroup with name = {}", chatGroupName);
        log.info("createChatGroupPostDTO contents = ");
        log.info("subject: {}", createGroupPostDTO.getSubject());
        log.info("content: {}", createGroupPostDTO.getContent());
        groupPostService.createNewPost(currentlyActiveUserRoot, createGroupPostDTO, chatGroupName);

        return "redirect:/groups/get/group?groupName=" + chatGroupName;

    }

    @RequestMapping("/delete/post")
    @ResponseBody
    public String deletePostById(@RequestParam("postId") String postId, @RequestParam("chatGroupName") String chatGroupName) {
        log.info("delete post by id endpoint hit!");
        log.info("deleting post by id of: {}", postId);
        log.info("chat group name = {}", chatGroupName);
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();
        log.info("asking groupPostService to delete post ....");
        boolean deleted = groupPostService.deletePostById(Long.valueOf(postId), currentlyActiveUserRoot,chatGroupName);
        if (deleted) {
            return "okay!";
        } else {
            return "this is not your post to delete!";
        }

    }


}
