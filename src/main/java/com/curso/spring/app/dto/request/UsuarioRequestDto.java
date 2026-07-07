package com.curso.spring.app.dto.request;

import java.util.List;

import com.curso.spring.app.domain.Rol;
import com.curso.spring.app.dto.request.generics.GenericRequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDto implements GenericRequestDto<Long> {

    private Long idUsuario;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private List<Rol> roles;

    @Override
    public Long id() {
        return idUsuario;
    }

}
