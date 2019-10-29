package tech.jdevmin.web.service.user.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.jdevmin.web.jpa.user.root.UserRoot;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface UserAuthService extends UserDetailsService {

    String authenticateUser(String username, String password, HttpServletRequest request) throws ServletException;

    void deAuthenticateUser(HttpServletRequest request) throws ServletException;

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    String getCurrentlyActiveUserName();

    UserRoot getCurrentlyActiveUserRoot();


}
