package tech.jdevmin.web.service.groups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.groups.ChatGroupPost;
import tech.jdevmin.web.jpa.groups.ChatGroupPostRepository;
import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.groups.PublicChatGroupRepository;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class GroupPostServiceImpl implements GroupPostService {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PublicChatGroupRepository groupRepository;

    private ChatGroupPostRepository postRepository;

    @Override
    public ChatGroupPost createNewPost(UserRoot currentlyActiveUserRoot, CreateGroupPostDTO dto, String chatGroupName) {
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        String postOwner = currentlyActiveUserRoot.getIdentity();

        ChatGroupPost chatGroupPost = new ChatGroupPost();
        chatGroupPost.setSubject(dto.getSubject());
        chatGroupPost.setContent(dto.getContent());
        chatGroupPost.setPublicChatGroup(chatGroup);
        chatGroupPost.setPostOwner(postOwner);
        chatGroup.addPost(chatGroupPost);
        entityManager.getTransaction().begin();
        entityManager.persist(chatGroupPost);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.merge(chatGroup);
        entityManager.getTransaction().commit();
        return chatGroupPost;
    }

    @Override
    public List<ChatGroupPost> getAllPostsReversed(PublicChatGroup publicChatGroup) {
        List<ChatGroupPost> posts = publicChatGroup.getChatGroupPosts();
        Collections.reverse(posts);
        return posts;
    }

    @Override
    public boolean deletePostById(Long id, UserRoot currentlyActiveUserRoot, String chatGroupName) {


        log.info("asking groupRepository to findByChatGroupName with name ({}) ....", chatGroupName);
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        String foundGroupName = chatGroup.getChatGroupName();
        log.info("found group ({})", foundGroupName);

        ChatGroupPost hangingChildToRemove = chatGroup.removePost(id);


        log.info("transaction begin >");
        entityManager.getTransaction().begin();
        log.info("attempting to merge chatGroup >");
        entityManager.merge(chatGroup);
        log.info("attempting to commit transaction");
        entityManager.getTransaction().commit();
        log.info("first transaction complete!");
        log.info("beginning second transaction ....");
        entityManager.getTransaction().begin();
        log.info("attempting to remove post");
        entityManager.remove(hangingChildToRemove);
        log.info("attempting to commit transaction");
        entityManager.getTransaction().commit();
        log.info("transaction committed successfully!");
        log.info("returning true");
        return true;
    }
}
