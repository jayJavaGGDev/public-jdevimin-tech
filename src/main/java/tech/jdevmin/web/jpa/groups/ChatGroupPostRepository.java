package tech.jdevmin.web.jpa.groups;

import org.springframework.data.repository.CrudRepository;

public interface ChatGroupPostRepository extends CrudRepository<ChatGroupPost, Long> {

    ChatGroupPost findByChatGroupPostId(Long chatGroupPostId);
}
