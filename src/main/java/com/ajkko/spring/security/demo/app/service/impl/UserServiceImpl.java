package com.ajkko.spring.security.demo.app.service.impl;

import com.ajkko.spring.security.demo.app.entity.User;
import com.ajkko.spring.security.demo.app.repository.UserRepository;
import com.ajkko.spring.security.demo.app.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not Found with userName: " + username);
        }

        return user;
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }
}
