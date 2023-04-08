package com.ajkko.spring.security.demo.app.service.impl;

import com.ajkko.spring.security.demo.app.entity.BaseEntity;
import com.ajkko.spring.security.demo.app.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class AbstractServiceImpl<T extends BaseEntity, ID extends Long> implements CrudService<T, ID> {
    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepository().findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Entity with id %d not found", id)));
    }

    @Override
    public T save(T object) {
        return getRepository().save(object);
    }

    @Override
    public void delete(T object) {
        getRepository().delete(object);
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    protected abstract JpaRepository<T, ID> getRepository();
}
