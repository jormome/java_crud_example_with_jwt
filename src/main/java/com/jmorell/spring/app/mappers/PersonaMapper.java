package com.jmorell.spring.app.mappers;

import org.mapstruct.Mapper;

import com.jmorell.spring.app.domain.Persona;
import com.jmorell.spring.app.dto.request.PersonaRequestDto;
import com.jmorell.spring.app.dto.response.PersonaResponseDto;
import com.jmorell.spring.app.mappers.generics.GenericMapper;

@Mapper(componentModel = "spring")
public interface PersonaMapper extends GenericMapper<PersonaRequestDto, PersonaResponseDto, Persona, Long> {
}
