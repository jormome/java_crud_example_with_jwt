package com.jmorell.spring.app.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @OneToMany
    @JoinColumn(name = "id_usuario") // es el nombre del campo a relacionar de la tabla roles
    private List<Rol> roles;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (idUsuario == null) {
            if (other.idUsuario != null)
                return false;
        } else if (!idUsuario.equals(other.idUsuario))
            return false;
        return true;
    }

}
