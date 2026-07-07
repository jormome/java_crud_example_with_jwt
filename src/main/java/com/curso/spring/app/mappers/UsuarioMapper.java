package com.curso.spring.app.mappers;

import org.mapstruct.Mapper;

import com.curso.spring.app.domain.Usuario;
import com.curso.spring.app.dto.request.UsuarioRequestDto;
import com.curso.spring.app.dto.response.UsuarioResponseDto;
import com.curso.spring.app.mappers.generics.GenericMapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericMapper<UsuarioRequestDto, UsuarioResponseDto, Usuario, Long> {
}
