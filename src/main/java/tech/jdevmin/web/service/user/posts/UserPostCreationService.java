package tech.jdevmin.web.service.user.posts;

import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserRoot;

public interface UserPostCreationService {

    PublicPost createPublicPost(PublicPost publicPost, UserRoot userRoot);

    PublicPost beginCreatePublicPost(UserRoot fromUser);

}
