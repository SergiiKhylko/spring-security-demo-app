package com.ajkko.spring.security.demo.app;

import com.ajkko.spring.security.demo.app.entity.Authority;
import com.ajkko.spring.security.demo.app.entity.User;
import com.ajkko.spring.security.demo.app.repository.AuthorityRepository;
import com.ajkko.spring.security.demo.app.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class SpringSecurityDemoAppApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    public SpringSecurityDemoAppApplication(PasswordEncoder passwordEncoder,
                                            UserService userService, AuthorityRepository authorityRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoAppApplication.class, args);
    }

    @PostConstruct
    protected void init() {
        authorityRepository.saveAll(List.of(new Authority("USER", "User role"),
                new Authority("ADMIN", "Admin role"), new Authority("MODER", "Moderator role")));

        Authority userAuthority = authorityRepository.findByRoleCode("USER");
        Authority adminAuthority = authorityRepository.findByRoleCode("ADMIN");
        Authority moderAuthority = authorityRepository.findByRoleCode("MODER");

        User user = new User();
        user.setUserName("admin_db");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setEnabled(true);
        user.setEmail("john@smith.org");
        user.setAuthorities(List.of(userAuthority, adminAuthority, moderAuthority));

        User user2 = new User();
        user2.setUserName("moder_db");
        user2.setFirstName("James");
        user2.setLastName("Brown");
        user2.setPassword(passwordEncoder.encode("pass"));
        user2.setEnabled(true);
        user2.setEmail("james@brown.org");

        user2.setAuthorities(List.of(userAuthority, moderAuthority));

        userService.save(user);
        userService.save(user2);
    }
}
