package tech.jdevmin.web.jpa.groups;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class PublicChatGroup {

    @Id
    @GeneratedValue
    private long groupId;

    @Column
    private LocalDate dateCreated;

    @Column(unique = true)
    private String chatGroupName;

    @ElementCollection
    private List<String> groupAdministrators;

    @ElementCollection
    private List<String> usersJoined;

    @OneToMany(mappedBy = "publicChatGroup")
    private List<ChatGroupPost> chatGroupPosts;

    public PublicChatGroup() {
        dateCreated = LocalDate.now();
        chatGroupPosts = new ArrayList<>();
        groupAdministrators = new ArrayList<>();
        usersJoined = new ArrayList<>();
    }

    public ChatGroupPost addPost(ChatGroupPost chatGroupPost) {
        chatGroupPosts.add(chatGroupPost);
        return chatGroupPost;

    }

    public ChatGroupPost removePost(Long id){
        ChatGroupPost postFound = new ChatGroupPost();
        for (ChatGroupPost post : chatGroupPosts){
            if (post.getChatGroupPostId() == id){
                postFound = post;
            }
        }
        chatGroupPosts.remove(postFound);
        return postFound;

    }

    public PublicChatGroup addAdministrator(String user) {
        groupAdministrators.add(user);
        return this;

    }

    public PublicChatGroup addMember(String user) {
        usersJoined.add(user);
        return this;

    }

    public PublicChatGroup removeMember(String user) {
        usersJoined.remove(user);
        return this;

    }

    public PublicChatGroup removeAdministrator(String user) {
        groupAdministrators.remove(user);
        return this;

    }
}
