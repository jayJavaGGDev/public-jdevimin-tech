package tech.jdevmin.web.service.groups;

import tech.jdevmin.web.jpa.groups.ChatGroupPost;
import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.util.List;

public interface GroupPostService {

    ChatGroupPost createNewPost(UserRoot currentlyActiveUserRoot, CreateGroupPostDTO dto,String chatGroupName);

    List<ChatGroupPost> getAllPostsReversed(PublicChatGroup publicChatGroup);

    boolean deletePostById(Long id, UserRoot currentlyActiveUserRoot, String chatGroupName);

}
