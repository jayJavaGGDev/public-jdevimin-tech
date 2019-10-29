package tech.jdevmin.web.jpa.groups;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ChatGroupPost {

    @Id
    @GeneratedValue
    private Long chatGroupPostId;

    @ManyToOne
    private PublicChatGroup publicChatGroup;

    @Column
    private String postOwner;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private LocalDateTime datePosted;

    public ChatGroupPost() {
        this.datePosted = LocalDateTime.now();
    }
}
