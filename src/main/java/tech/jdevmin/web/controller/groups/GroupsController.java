package tech.jdevmin.web.controller.groups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.jdevmin.web.jpa.groups.ChatGroupPost;
import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.groups.CreateGroupPostDTO;
import tech.jdevmin.web.service.groups.CreateNewChatGroupDTO;
import tech.jdevmin.web.service.groups.GroupPostService;
import tech.jdevmin.web.service.groups.GroupsService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import java.util.List;

@Controller
@RequestMapping("/groups")
@Slf4j
public class GroupsController {

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupPostService groupPostService;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping
    public String getJoinedGroups(Model model) {
        log.info("getJoinedGroups endpoint hit!");
        log.info("asking userAuthService for currently active user Root");
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();
        String currentUsername = currentlyActiveUserRoot.getIdentity();
        log.info("current user found username = {}", currentUsername);
        List<String> groupsJoined = currentlyActiveUserRoot.getGroupsJoined();
        model.addAttribute("groupsJoined", groupsJoined);

        return "UserGroups";
    }


    @RequestMapping("/find")
    public String findAllGroups(Model model) {
        List<PublicChatGroup> allChatGroups = groupsService.getAllChatGroups();
        int chatGroupsSize = allChatGroups.size();
        log.info("chat groups size = " + chatGroupsSize);
        log.info("getting all chat groups >");

        allChatGroups.forEach(publicChatGroup -> {
            String chatGroupName = publicChatGroup.getChatGroupName();
            System.out.println("chat group name = " + chatGroupName);
        });

        model.addAttribute("groups", allChatGroups);
        return "GetAllGroups";
    }


    @RequestMapping("/get/group{groupName}")
    public String getGroupByGroupName(@RequestParam("groupName") String groupName, Model model) {
        log.info("get group by group name endpoint hit.");
        log.info("asking groups serviced to get chat group by name = {}", groupName);
        PublicChatGroup chatGroup = groupsService.getChatGroupByName(groupName);
        List<String> administrators = chatGroup.getGroupAdministrators();
        List<String> usersJoined = chatGroup.getUsersJoined();
        model.addAttribute("group", chatGroup);
        List<ChatGroupPost> posts = groupPostService.getAllPostsReversed(chatGroup);
        int size = chatGroup.getUsersJoined().size();


        boolean isUserAlreadyMemberOfGroup = false;
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();
        String currentlyActiveUsername = currentlyActiveUserRoot.getIdentity();
        List<String> groupsJoined = currentlyActiveUserRoot.getGroupsJoined();
        if (groupsJoined.contains(groupName)){
            isUserAlreadyMemberOfGroup = true;

        }

        model.addAttribute("posts", posts);
        model.addAttribute("usersJoined", usersJoined);
        model.addAttribute("administrators", administrators);
        model.addAttribute("postRequest", new CreateGroupPostDTO());
        model.addAttribute("size", size);
        model.addAttribute("isMember",isUserAlreadyMemberOfGroup);
        model.addAttribute("user",currentlyActiveUsername);


        return "Group";
    }


    @RequestMapping("/join/group{groupId}")
    public String joinGroup(@RequestParam("groupId") String groupId, Model model) {
        log.info("get group endpoint hit.");
        log.info("asking groups serviced to get chat group by name = {}", groupId);
        PublicChatGroup chatGroup = groupsService.getChatGroupByID(Long.valueOf(groupId));

        String chatGroupName = chatGroup.getChatGroupName();
        log.info("found chat group with name {}", chatGroupName);
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();
        String currentUsername = currentlyActiveUserRoot.getIdentity();
        log.info("current user = {}", currentUsername);
        log.info("asking groups service to join current user to chat group ....");
        groupsService.joinChatGroup(chatGroupName, currentlyActiveUserRoot);


        return "redirect:/groups/get/group?groupName=" + chatGroupName;
    }

    @RequestMapping("/groupTest")
    @ResponseBody
    public String addNewGroupTest() {
        CreateNewChatGroupDTO dto = new CreateNewChatGroupDTO();
        dto.setChatGroupName("another test group");
        log.info("asking user auth service for currently active user root >");
        UserRoot currentlyActiveUserRoot = userAuthService.getCurrentlyActiveUserRoot();

        log.info("asking groups service to create a new chat group with currently active userroot + group name = {}", dto.getChatGroupName());
        groupsService.createNewChatGroup(dto, currentlyActiveUserRoot);

        return "Okay";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String removeChatGroup() {
//        log.info("remove chat group endpoint hit");
//        groupsService.removeChatGroup("another test group");
        return "Okay";
    }
}
