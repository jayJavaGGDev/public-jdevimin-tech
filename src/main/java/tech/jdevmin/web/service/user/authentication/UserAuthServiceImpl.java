package tech.jdevmin.web.service.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.jpa.user.root.UserAccountDetailsRepository;
import tech.jdevmin.web.jpa.user.root.UserRoot;
import tech.jdevmin.web.jpa.user.root.UserRootRepository;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {


    @Autowired
    private UserAccountDetailsRepository userAccountDetailsRepository;

    @Autowired
    private UserAccountAccessService userAccountAccessService;

    private UserRootRepository userRootRepository;

    @Autowired
    private EntityManager entityManager;


    @Override
    public String authenticateUser(String username, String password, HttpServletRequest request) {

        String nextEndpoint = "redirect:/auth/postauth";
        log.info("trying to authenticate user {}", username);
        try {
            request.login(username, password);
            log.info("request successful!");
        } catch (ServletException e) {
            log.info("request NOT successful!");
            SecurityContextHolder.clearContext();
            nextEndpoint = "redirect:/auth/failed";
        }
        return nextEndpoint;
    }


    @Override
    public void deAuthenticateUser(HttpServletRequest request) throws ServletException {
        request.logout();
        SecurityContextHolder.clearContext();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserAccountDetails uAccD = userAccountDetailsRepository.findByUsername(s);

        return new User(
                uAccD.getUsername(),
                uAccD.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
    }


    @Override
    public UserRoot getCurrentlyActiveUserRoot() {
        String username = getCurrentlyActiveUserName();

        log.info("getting currently active user root by username");

        UserRoot userRoot = userAccountDetailsRepository.findByUsername(username).getUserRoot();
        return  userRoot;


    }

    private String getAccess() {
        return getCurrentlyActiveUserName();

    }


    @Override
    public String getCurrentlyActiveUserName() {
        log.info("getting currently active username");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            log.info("principal is instanceof UserDetails");
            username = ((UserDetails) principal).getUsername();
            log.info("((UserDetails) principal).getUsername() = {}", username);
        } else {
            username = ((UserDetails) principal).getUsername();
            log.info("principal is NOT instanceof UserDetails");
            log.info("principal.toString() = {}", username);
        }

        return username;
    }


}
