package com.jmorell.spring.app.dao;

import java.util.Optional;

import com.jmorell.spring.app.dao.generics.GenericDao;
import com.jmorell.spring.app.domain.Usuario;

public interface UsuarioDao extends GenericDao<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
