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

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
   
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/images/*.png")).permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll()
        .and().authorizeRequests().requestMatchers(new AntPathRequestMatcher("/images/*.jpg")).permitAll()
        .and().authorizeRequests().antMatchers("/api/**").permitAll();
        super.configure(http);
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
