package tech.jdevmin.web.service.user.posts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.user.profile.PublicPost;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.persistence.EntityManager;

@Service
@Slf4j
public class UserPostCreationServiceImpl implements UserPostCreationService {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private EntityManager entityManager;

    @Override
    public PublicPost createPublicPost(PublicPost publicPost, UserRoot userRoot) {




        log.info("adding public post to currently active user root");
        userRoot.getPublicPosts().add(publicPost);
        String username = userRoot.getIdentity();
        log.info("currently active user root = {}", username);

        entityManager.getTransaction().begin();
        entityManager.merge(username);
        return publicPost;
    }


    @Override
    public PublicPost beginCreatePublicPost(UserRoot fromUser) {
        log.info("inside begin create public post");
        String fromUserUsername = fromUser.getIdentity();
        log.info("from username = {}",fromUserUsername);
        PublicPost publicPost = new PublicPost();
        publicPost.setSubject("this is the subject");
        publicPost.setContent("this is the content");
        publicPost.setUsername(fromUserUsername);
        publicPost.setUserRoot(fromUser);
        log.info("transaction begin");
        entityManager.getTransaction().begin();
        log.info("persisting public post");
        entityManager.persist(publicPost);
        log.info("commiting public post");
        entityManager.getTransaction().commit();
        createNextStep(publicPost,fromUser);





        return publicPost;

    }

    private PublicPost createNextStep(PublicPost publicPost, UserRoot fromUser){

        log.info("transaction begin");
        entityManager.getTransaction().begin();
        log.info("adding public post to fromUser PublicPostList");
        fromUser.getPublicPosts().add(publicPost);
        log.info("rolling back....");
        entityManager.merge(fromUser);
        entityManager.getTransaction().commit();
        log.info("returning public post");
        return publicPost;

    }
}
