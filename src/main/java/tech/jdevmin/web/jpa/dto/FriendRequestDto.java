package tech.jdevmin.web.jpa.dto;

import lombok.Data;

@Data
public class FriendRequestDto {

    private long currentUserId;
    private long friendToAdd;
}
