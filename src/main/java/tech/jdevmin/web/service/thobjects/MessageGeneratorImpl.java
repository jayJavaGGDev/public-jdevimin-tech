package tech.jdevmin.web.service.thobjects;

import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.dto.UserDto;


@Service
public class MessageGeneratorImpl implements MessageGenerator {

    @Override
    public String generateMessage() {
        UserDto userDto = new UserDto();
        userDto.setId(-99999L);
        String username = "empty";

        try {
//            userDto = usersService.getCurrentUserEntityAsDto();
            username = userDto.getUsername();

        } catch (NullPointerException e) {
            e.getLocalizedMessage();
            e.getCause();
        }


        return userDto.getId() == -99999L ? "User Not logged in!" : "Welcome back, " + username + "!";

    }
}
