package com.curso.spring.app.dao;

import java.util.Optional;

import com.curso.spring.app.dao.generics.GenericDao;
import com.curso.spring.app.domain.Usuario;

public interface UsuarioDao extends GenericDao<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
