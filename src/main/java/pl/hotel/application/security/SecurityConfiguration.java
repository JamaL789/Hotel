package pl.hotel.application.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import pl.hotel.application.views.login.LoginView;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    public static final String LOGOUT_URL = "/";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }/*
    private static class SimpleInMemoryUserDetailsManager extends InMemoryUserDetailsManager {
        public SimpleInMemoryUserDetailsManager() {
            createUser(new User("user",
                "{noop}userpass",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            ));
            createUser(new User("admin",
                "{noop}userpass",
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            ));
        }
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new SimpleInMemoryUserDetailsManager(); 
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll()
        .and().authorizeRequests().requestMatchers(new AntPathRequestMatcher("/images/*.jpg")).permitAll()
        .and().authorizeRequests().antMatchers("/rooms/**").permitAll()
        .and().authorizeRequests().antMatchers("/reservations/**").permitAll()
        .and().authorizeRequests().antMatchers("/newreservation/**").permitAll();
        super.configure(http);
     //   setLoginView(http, LoginView.class, LOGOUT_URL);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
