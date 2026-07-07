package com.jmorell.spring.app.dao.generics;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao<E, ID extends Serializable> extends JpaRepository<E, ID> {
}
