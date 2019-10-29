package tech.jdevmin.web.service.groups;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.groups.PublicChatGroup;
import tech.jdevmin.web.jpa.groups.PublicChatGroupRepository;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private PublicChatGroupRepository groupRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public PublicChatGroup createNewChatGroup(CreateNewChatGroupDTO dto, UserRoot currentlyActiveUserRoot) {
        String chatGroupName = dto.getChatGroupName();
        PublicChatGroup publicChatGroup = new PublicChatGroup();
        publicChatGroup.setChatGroupName(chatGroupName);
        String ownersUsername = currentlyActiveUserRoot.getIdentity();
        publicChatGroup.addAdministrator(ownersUsername);
        publicChatGroup.addMember(ownersUsername);
        currentlyActiveUserRoot.joinGroup(chatGroupName);

        entityManager.getTransaction().begin();
        entityManager.persist(publicChatGroup);
        entityManager.merge(currentlyActiveUserRoot);
        entityManager.getTransaction().commit();
        return publicChatGroup;
    }

    @Override
    public PublicChatGroup joinChatGroup(String chatGroupName, UserRoot currentlyActiveUserRoot) {
        log.info("inside GroupsServiceImpl.joinChatGroup()");

        log.info("groups repository is fining chat group by name {}", chatGroupName);
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        log.info("group found");
        String username = currentlyActiveUserRoot.getIdentity();
        log.info("currently active user identity = {}", username);
        currentlyActiveUserRoot.joinGroup(chatGroupName);
        chatGroup.addMember(username);

        log.info("transaction begin");
        entityManager.getTransaction().begin();
        log.info("attempting go merging currentlyActiveUserRoot");
        entityManager.merge(currentlyActiveUserRoot);
        log.info("attempting to merging chatGroup");
        entityManager.merge(chatGroup);
        log.info("attempting to commit transaction");
        entityManager.getTransaction().commit();
        log.info("transaction complete. returning chatGroup");
        return chatGroup;
    }

    @Override
    public PublicChatGroup declareAdministrator(String chatGroupName, String username) {
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        chatGroup.addAdministrator(username);
        entityManager.getTransaction().begin();
        entityManager.merge(chatGroup);
        entityManager.getTransaction().commit();
        return chatGroup;
    }

    @Override
    public PublicChatGroup removeAdministrator(String chatGroupName, String username) {
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        chatGroup.removeAdministrator(username);
        entityManager.getTransaction().begin();
        entityManager.merge(chatGroup);
        entityManager.getTransaction().commit();
        return chatGroup;
    }

    @Override
    public PublicChatGroup removeMember(String chatGroupName, String username) {
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        chatGroup.removeMember(username);
        entityManager.getTransaction().begin();
        entityManager.merge(chatGroup);
        entityManager.getTransaction().commit();
        return chatGroup;
    }

    @Override
    public List<PublicChatGroup> getAllChatGroups() {
        List<PublicChatGroup> allChatGroups = groupRepository.findAll();
        return allChatGroups;
    }


    @Override
    public PublicChatGroup getChatGroupByID(long id) {
        PublicChatGroup group = groupRepository.findById(id).get();
        return group;
    }

    @Override
    public PublicChatGroup getChatGroupByName(String chatGroupName) {
        PublicChatGroup chatGroup = groupRepository.findByChatGroupName(chatGroupName);
        return chatGroup;
    }

    @Override
    public void removeChatGroup(String groupName) {

        log.info("asking groups repository to find all groups with name = {}", groupName);
        List<PublicChatGroup> allGroupsByName = groupRepository.findAllByChatGroupName(groupName);
        PublicChatGroup group1 = allGroupsByName.get(0);
        PublicChatGroup group2 = allGroupsByName.get(1);
        entityManager.getTransaction().begin();
        groupRepository.delete(group1);
        groupRepository.delete(group2);

        entityManager.getTransaction().commit();
    }
}
