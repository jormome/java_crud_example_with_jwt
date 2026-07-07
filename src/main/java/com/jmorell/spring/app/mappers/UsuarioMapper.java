package com.jmorell.spring.app.mappers;

import org.mapstruct.Mapper;

import com.jmorell.spring.app.domain.Usuario;
import com.jmorell.spring.app.dto.request.UsuarioRequestDto;
import com.jmorell.spring.app.dto.response.UsuarioResponseDto;
import com.jmorell.spring.app.mappers.generics.GenericMapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericMapper<UsuarioRequestDto, UsuarioResponseDto, Usuario, Long> {
}
