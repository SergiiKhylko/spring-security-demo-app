package com.ajkko.spring.security.demo.app.service;

import com.ajkko.spring.security.demo.app.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CrudService<User, Long>, UserDetailsService {
}
