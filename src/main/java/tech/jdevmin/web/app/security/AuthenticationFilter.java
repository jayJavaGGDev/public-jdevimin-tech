package tech.jdevmin.web.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.jdevmin.web.jpa.dto.LoginRequestModel;
import tech.jdevmin.web.jpa.user.root.UserAccountDetails;
import tech.jdevmin.web.service.user.account.UserAccountAccessService;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    Environment environment;
    UserAuthService userAuthService;
    @Autowired
    UserAccountAccessService userAccountAccessService;


    @Autowired
    public AuthenticationFilter(UserAuthService userAuthService, Environment environment, AuthenticationManager authenticationManager) {

        this.userAuthService = userAuthService;
        this.environment = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        log.info("attempting to authenticate user");
        try {
            log.info("trying to map object");
            LoginRequestModel creds = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestModel.class);


            log.info("username: {}", creds.getUsername());
            log.info("password: {}", creds.getPassword());


            // first we create a UsernamePasswordAuthenticationToken and pass it
            // into the authenticate method from the AuthenticationManager class
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {

            log.info("the exception was caught!");
            log.info("the headers::");
            log.info(request.getHeader("username"));
            log.info(request.getHeader("password"));
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("AUTHENTICATION SUCCESS :)");
        // 1.) read user details from authResult(sent from attemptAuthentication() method ^above^
        // 2.) use user details to create a JWT token
        // 3.) add JWT Token to HTTP response header.
        // 4.) spring framework will send a response back to the browser with the headers specified -- (token, userId)


        // attemptAuthentication() method returns authResult >
        String userName = ((User) authResult.getPrincipal()).getUsername();
        log.info("found userName in database : {}", userName);

        UserAccountDetails userDetails = userAccountAccessService.accessAccount(userName).getAccountDetails();


        // generate JSON WEB TOKEN...
        log.info("GENERATING JSON WEB TOKEN ....");

        String token = Jwts.builder()
                .setSubject(String.valueOf(userDetails.getUserRoot().getUserRootId()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();


        response.addHeader("token", token);

        response.addHeader("userId", String.valueOf(userDetails.getUserRoot().getUserRootId()));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        log.error("AUTHENTICATION FAILED :(");
    }


}
