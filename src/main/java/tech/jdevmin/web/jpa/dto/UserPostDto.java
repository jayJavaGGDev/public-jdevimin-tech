package tech.jdevmin.web.jpa.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class UserPostDto {

    @Getter(AccessLevel.NONE)
    public static final long serialVersionUID = -456530498728418264L;





    private String content;

    private String subject;

    public UserPostDto() {
    }

//    public UserPostDto(long postId, UserEntity userId, String date, String subject, String content) {
//        this.postId = postId;
//        this.userId = userId;
//        this.date = date;
//        this.subject = subject;
//        this.content = content;
//    }
}
