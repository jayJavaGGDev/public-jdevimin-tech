package tech.jdevmin.web.jpa.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    public static final long serialVersionUID = -8235323499918014236L;


    private Long id;


    private String firstName;


    private String lastName;


    private String username;

    private String email;

    private String password;

}
