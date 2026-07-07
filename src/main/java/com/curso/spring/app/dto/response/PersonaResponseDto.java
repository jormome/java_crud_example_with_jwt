package com.curso.spring.app.dto.response;

import com.curso.spring.app.dto.response.generics.GenericResponseDto;

public record PersonaResponseDto(
                Long idPersona,
                String nombre,
                String apellido,
                String email,
                String telefono,
                Double saldo) implements GenericResponseDto<Long> {

        public PersonaResponseDto() {
                this(null, "", "", "", "", 0.0);
        }

        @Override
        public Long id() {
                return idPersona;
        }
}
