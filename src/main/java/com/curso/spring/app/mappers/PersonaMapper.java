package com.curso.spring.app.mappers;

import org.mapstruct.Mapper;

import com.curso.spring.app.domain.Persona;
import com.curso.spring.app.dto.request.PersonaRequestDto;
import com.curso.spring.app.dto.response.PersonaResponseDto;
import com.curso.spring.app.mappers.generics.GenericMapper;

@Mapper(componentModel = "spring")
public interface PersonaMapper extends GenericMapper<PersonaRequestDto, PersonaResponseDto, Persona, Long> {
}
