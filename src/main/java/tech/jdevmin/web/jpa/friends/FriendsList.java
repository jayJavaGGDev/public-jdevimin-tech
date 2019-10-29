package tech.jdevmin.web.jpa.friends;

import lombok.Data;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class FriendsList implements Serializable {

    public static final long serialVersionUID = -3108516247756660434L;


    @Id
    @GeneratedValue
    private Long flID;

    @OneToOne
    private UserRoot userRoot;

    @ElementCollection
    private List<String> usersFriends;

    @GeneratedValue
    private long listId;




    public void addFriend(String username) {
        usersFriends.add(username);
    }

    public FriendsList(){
        usersFriends = new ArrayList<>();
    }

}
