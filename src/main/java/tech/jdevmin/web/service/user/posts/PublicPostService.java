package tech.jdevmin.web.service.user.posts;

import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import java.util.List;

public interface PublicPostService {


    void createPost(String subject, String content, UserRoot userRoot);

    void updatePost(long postId);

//    List<PublicPost> getCurrentUserPosts();

    List<PublicPost> getAllUserPosts(long PID);
    List<PublicPost> getAllUserPostsByUsername(String username);

    List<PublicPost> getAllUserPostsReversed(long PID);

    List<PublicPost> reverseAllPosts(List<PublicPost> list);

    PublicPost getPost(long PID, long postId);

    void deletePost(long PID, long postId);
}
