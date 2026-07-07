package com.curso.spring.app.dto.request;

import com.curso.spring.app.dto.request.generics.GenericRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaRequestDto implements GenericRequestDto<Long> {

    private Long idPersona;

    @NotBlank(message = "{persona.nombre.empty}")
    private String nombre;

    @NotBlank(message = "{persona.apellido.empty}")
    private String apellido;

    @NotBlank(message = "{persona.email.empty}")
    @Email(message = "{persona.email.invalid}")
    private String email;

    private String telefono;

    @NotNull(message = "{persona.saldo.empty}")
    private Double saldo;

    @Override
    public Long id() {
        return idPersona;
    }
}
