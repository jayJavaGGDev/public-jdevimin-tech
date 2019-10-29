package tech.jdevmin.web.jpa.user.root;

import lombok.Data;
import tech.jdevmin.web.jpa.friends.FriendsList;
import tech.jdevmin.web.jpa.user.notification.UserAlertModule;
import tech.jdevmin.web.jpa.user.profile.PublicPost;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserRoot implements Serializable {

    public static final long serialVersionUID = -2826973209355987711L;

    @Id
    @GeneratedValue
    private long userRootId;

    @Column
    private String identity;

    @OneToOne(mappedBy = "userRoot")
    @JoinColumn
    private UserAccountDetails accountDetails;

    @OneToOne(mappedBy = "userRoot")
    @JoinColumn
    private FriendsList friendsList;

    @OneToOne(mappedBy = "userRoot")
    @JoinColumn
    private UserAlertModule userAlertModule;

    @ElementCollection
    private List<PublicPost> publicPosts;

    @ElementCollection
    private List<String> groupsJoined;

    public UserRoot() {
        publicPosts = new ArrayList<>();
        groupsJoined = new ArrayList<>();
    }

    public PublicPost addPost(PublicPost publicPost) {
        publicPosts.add(publicPost);
        return publicPost;
    }

    public void joinGroup(String chatGroupName) {
        groupsJoined.add(chatGroupName);

    }


}
