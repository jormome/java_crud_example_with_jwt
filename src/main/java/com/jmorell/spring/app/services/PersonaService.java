package com.jmorell.spring.app.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.jmorell.spring.app.dao.PersonaDao;
import com.jmorell.spring.app.domain.Persona;
import com.jmorell.spring.app.dto.request.PersonaRequestDto;
import com.jmorell.spring.app.dto.response.PersonaResponseDto;
import com.jmorell.spring.app.mappers.generics.GenericMapper;
import com.jmorell.spring.app.services.generics.GenericServiceImpl;

@Service
public class PersonaService extends GenericServiceImpl<PersonaRequestDto, PersonaResponseDto, Persona, Long> {

    private final PersonaDao personaDao;

    public PersonaService(
            PersonaDao dao,
            GenericMapper<PersonaRequestDto, PersonaResponseDto, Persona, Long> mapper,
            MessageSource messageSource) {

        super(dao, mapper, messageSource);
        this.personaDao = dao;
    }

    public Double getTotalSaldo() {
        return personaDao.getTotalSaldo();
    }

}
