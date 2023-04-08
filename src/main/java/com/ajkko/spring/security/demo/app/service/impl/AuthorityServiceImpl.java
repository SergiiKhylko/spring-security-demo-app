package com.ajkko.spring.security.demo.app.service.impl;

import com.ajkko.spring.security.demo.app.entity.Authority;
import com.ajkko.spring.security.demo.app.repository.AuthorityRepository;
import com.ajkko.spring.security.demo.app.service.AuthorityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl extends AbstractServiceImpl<Authority, Long> implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    protected JpaRepository<Authority, Long> getRepository() {
        return authorityRepository;
    }
}
