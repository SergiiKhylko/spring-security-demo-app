package com.ajkko.spring.security.demo.app.repository;

import com.ajkko.spring.security.demo.app.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByRoleCode(String roleCode);
}
