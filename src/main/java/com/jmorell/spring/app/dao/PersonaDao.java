package com.jmorell.spring.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.jmorell.spring.app.dao.generics.GenericDao;
import com.jmorell.spring.app.domain.Persona;

public interface PersonaDao extends GenericDao<Persona, Long> {
    // En PersonaDao o PersonaRepository
    @Query("SELECT COALESCE(SUM(p.saldo), 0) FROM Persona p")
    Double getTotalSaldo();
}
