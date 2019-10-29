package tech.jdevmin.web.service.friends;

import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.util.List;

public interface FriendsService {

    void sendFriendRequest(String username);

    void acceptFriendRequest(long id, UserRoot currentlyActiveUserRoot);

    List<String> getFriendsList(UserRoot currentlyActiveUserRoot);


}
