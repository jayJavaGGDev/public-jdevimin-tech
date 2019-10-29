package tech.jdevmin.web.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jdevmin.web.service.user.authentication.UserAuthService;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {


    private UserAuthService userAuthService;
    private Environment environment;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public SecSecurityConfig(UserAuthService userAuthService, Environment environment, BCryptPasswordEncoder passwordEncoder) {
        this.userAuthService = userAuthService;
        this.environment = environment;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/restricted").hasAnyRole()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .failureForwardUrl("/authfailed")

                .and()
                .addFilter(getAuthenticationFilter());
//                .formLogin()
//                .loginPage("/user/login")
//                .successForwardUrl("/user/login/success")
//                .failureForwardUrl("/user/login/failure")
//                .and()
//                .addFilter(getAuthenticationFilter()
//                );// this is stored in application.properties
////                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/user/auth")
//                .and()
//                .addFilter(getAuthenticationFilter()); // register custom filter class from the /service package


//        http.headers().frameOptions().disable();


    }


    private AuthenticationFilter getAuthenticationFilter() throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(userAuthService, environment, authenticationManager());

        authenticationFilter.setFilterProcessesUrl("/user/auth");

        return authenticationFilter;
    }

}
