package com.jmorell.spring.app.dto.response;

import java.util.List;

import com.jmorell.spring.app.domain.Rol;
import com.jmorell.spring.app.dto.response.generics.GenericResponseDto;

public record UsuarioResponseDto(
        Long idUsuario,
        String username,
        String password,
        List<Rol> roles) implements GenericResponseDto<Long> {

    public UsuarioResponseDto() {
        this(null, "", "", null);
    }

    @Override
    public Long id() {
        return idUsuario;
    }

}
