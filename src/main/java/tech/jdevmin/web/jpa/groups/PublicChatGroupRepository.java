package tech.jdevmin.web.jpa.groups;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicChatGroupRepository extends CrudRepository<PublicChatGroup, Long> {

    PublicChatGroup findByChatGroupName(String chatGroupName);
    List<PublicChatGroup> findAll();
    List<PublicChatGroup> findAllByChatGroupName(String chatGroupName);
}
