package tech.jdevmin.web.jpa.user.profile;

import lombok.Data;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class PublicPost {

    @ManyToOne
    @JoinColumn
    private UserRoot userRoot;

    @Id
    @GeneratedValue
    private long ppID;

    @Column
    private String username;

    @Column
    private LocalDateTime datePosted;

    @Column
    private String subject;

    @Column
    private String content;

    public PublicPost(){
        datePosted = LocalDateTime.now();
    }
}