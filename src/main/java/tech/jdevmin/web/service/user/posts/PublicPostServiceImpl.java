package tech.jdevmin.web.service.user.posts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.profile.PublicPostRepository;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
@Slf4j
public class PublicPostServiceImpl implements PublicPostService {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserAuthService userAuthenticationService;

    @Autowired
    private PublicPostRepository publicPostRepository;

    @Autowired
    private UserAccountAccessService userAccountAccessService;


    @Override
    public void createPost(String subject, String content, UserRoot userRoot) {
        log.info("creating post");
        log.info("subject = {}", subject);
        log.info("content = {}", content);

        log.info("pre-building PostEntity >");
        PublicPost post = preBuildPostEntity(userRoot);

        log.info("starting prebuildStepTWo");
        preBuildStepTwo(post, userRoot);


        entityManager.getTransaction().begin();
        post.setSubject(subject);
        post.setContent(content);
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        log.info("beginning test: ");
        List<PublicPost> postsList = userRoot.getPublicPosts();


        postsList.forEach(publicPost ->
                System.out.println(
                        publicPost.getSubject() + " " +
                                publicPost.getContent()));

        int postListSize = postsList.size();

        log.info("post list size: {}",postListSize);


    }


    private PublicPost preBuildPostEntity(UserRoot userRoot) {
        log.info("pre building post entity");
        String username = userRoot.getIdentity();

        PublicPost publicPost = new PublicPost();

        log.info("begin transaction");
        entityManager.getTransaction().begin();


        log.info("publicPost.setPublicIdentity(publicIdentity");
        publicPost.setUserRoot(userRoot);
        publicPost.setUsername(username);
        log.info("publicPost.setUsername(publicIdentity.getUsername()");

        log.info("persisting public post");
        entityManager.persist(publicPost);
        log.info("committing transaction");
        entityManager.getTransaction().commit();


        return publicPost;
    }

    private PublicPost preBuildStepTwo(PublicPost publicPost, UserRoot userRoot) {
        log.info("adding pre-built post to userRoot postList");

        log.info("transaction begin ");
        entityManager.getTransaction().begin();
        log.info("adding public post to userRoot postList");
        userRoot.getPublicPosts().add(publicPost);
        entityManager.merge(userRoot);
        entityManager.getTransaction().commit();
        return publicPost;

    }


    @Override
    public void updatePost(long postId) {
        //TODO complete this update method.
//        PublicIdentity publicIdentity = userAuthenticationService.getCurrentlyActiveUserPublicIdentity();

    }


    //TODO -- create a new method of posting. not involving pid
//    @Override
//    public List<PublicPost> getCurrentUserPosts() {
//        return userAuthenticationService.getCurrentlyActiveUserPublicIdentity().getPublicPosts();
//    }

    @Override
    public List<PublicPost> getAllUserPosts(long PID) {
        log.info("getting all user posts");
        List<PublicPost> publicPostsList = userAccountAccessService.accessAccount(PID).getPublicPosts();

        publicPostsList.forEach(publicPost -> {
            System.out.println(
                    "POST INFO: \n" +
                            publicPost.getPpID() + "\n" +
                            publicPost.getDatePosted() + "\n" +
                            publicPost.getSubject() + "\n" +
                            publicPost.getContent() + "\n"


            );
        });


        return publicPostsList;
    }

    @Override
    public List<PublicPost> getAllUserPostsByUsername(String username) {
        return userAccountAccessService.accessAccount(username).getPublicPosts();
    }

    @Override
    public List<PublicPost> getAllUserPostsReversed(long PID) {

        List<PublicPost> allUserPosts = getAllUserPosts(PID);

//        Collections.reverse(allUserPosts);
        return allUserPosts;

    }

    @Override
    public List<PublicPost> reverseAllPosts(List<PublicPost> list) {
        Collections.reverse(list);
        return list;
    }

    @Override
    public PublicPost getPost(long PID, long postId) {
        log.info("getting user post where PID = {}", PID);
        log.info("and where postId = {}", postId);

        List<PublicPost> posts = getAllUserPosts(PID);


        log.info("posts size before filter = {}", posts.size());
        log.info("filtering posts");
        Predicate<PublicPost> postFilter = p -> p.getPpID() != postId;
        posts.removeIf(postFilter);
        log.info("filter complete");
        log.info("posts size after filter = {}", posts.size());

        PublicPost publicPost = posts.get(0);

        log.info("\n\npublic post:\n ");
        log.info("subject = {}", publicPost.getDatePosted());
        log.info("subject = {}", publicPost.getSubject());
        log.info("subject = {}", publicPost.getContent());


        return publicPost;


    }

    @Override
    public void deletePost(long PID, long postId) {

        entityManager.getTransaction().begin();
        publicPostRepository.delete(getPost(PID, postId));
        entityManager.getTransaction().commit();


    }
}

