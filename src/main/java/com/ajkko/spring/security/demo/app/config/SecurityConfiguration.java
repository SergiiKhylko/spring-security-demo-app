package com.ajkko.spring.security.demo.app.config;

import com.ajkko.spring.security.demo.app.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final static String ROLE_ADMIN = "ADMIN";
    private final static String ROLE_MODER = "MODER";
    private final static String ROLE_USER = "USER";

    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin").hasAuthority(ROLE_ADMIN)
                .antMatchers("/h2-console/**").hasAuthority(ROLE_ADMIN)
                .antMatchers("/moder").hasAnyAuthority(ROLE_ADMIN, ROLE_MODER)
                .antMatchers("/user").hasAnyAuthority(ROLE_ADMIN, ROLE_MODER, ROLE_USER)
                .antMatchers("/hello").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable().headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("pass"))
                .authorities(ROLE_ADMIN, ROLE_MODER, ROLE_USER)
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("pass"))
                .authorities(ROLE_USER)
                .and()
                .withUser("moder")
                .password(passwordEncoder().encode("pass"))
                .authorities(ROLE_MODER, ROLE_USER);


        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
