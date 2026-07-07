package com.curso.spring.app.services;

import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.app.dao.UsuarioDao;
import com.curso.spring.app.domain.Usuario;
import com.curso.spring.app.dto.request.UsuarioRequestDto;
import com.curso.spring.app.dto.response.UsuarioResponseDto;
import com.curso.spring.app.mappers.generics.GenericMapper;
import com.curso.spring.app.services.generics.GenericServiceImpl;

// @Service("userDetailsService") // este sera el nombre
@Service
public class UsuarioService extends GenericServiceImpl<UsuarioRequestDto, UsuarioResponseDto, Usuario, Long>
        implements UserDetailsService {

    // le inyecto el UsuarioDao para acceder al metodo añadido y NO USO el dao del
    // generico
    private final UsuarioDao usuarioDao;

    public UsuarioService(UsuarioDao usuarioDao,
            GenericMapper<UsuarioRequestDto, UsuarioResponseDto, Usuario, Long> mapper, MessageSource messageSource) {
        super(usuarioDao, mapper, messageSource);
        this.usuarioDao = usuarioDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        var authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .toList();

        return new User(usuario.getUsername(), usuario.getPassword(), authorities);

    }

}
