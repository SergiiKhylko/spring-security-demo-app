package com.ajkko.spring.security.demo.app.repository;

import com.ajkko.spring.security.demo.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
