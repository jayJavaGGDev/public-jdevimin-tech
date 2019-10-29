package tech.jdevmin.web.service.groups;

import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.util.List;

public interface GroupsService {

    PublicChatGroup createNewChatGroup(CreateNewChatGroupDTO dto, UserRoot currentlyActiveUserRoot);

    PublicChatGroup joinChatGroup(String chatGroupName, UserRoot currentlyActiveUserRoot);

    PublicChatGroup declareAdministrator(String chatGroupName, String username);

    PublicChatGroup removeAdministrator(String chatGroupName, String username);

    PublicChatGroup removeMember(String chatGroupName, String username);

    List<PublicChatGroup> getAllChatGroups();

    void removeChatGroup(String groupName);

    PublicChatGroup getChatGroupByID(long id);

    PublicChatGroup getChatGroupByName(String chatGroupName);


}
