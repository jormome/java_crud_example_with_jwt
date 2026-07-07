package com.curso.spring.app.services.generics;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.curso.spring.app.dao.generics.GenericDao;
import com.curso.spring.app.dto.request.generics.GenericRequestDto;
import com.curso.spring.app.dto.response.generics.GenericResponseDto;
import com.curso.spring.app.mappers.generics.GenericMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GenericServiceImpl<RQ extends GenericRequestDto<ID>, RS extends GenericResponseDto<ID>, E, ID extends Serializable>
        implements GenericService<RQ, RS, ID> {

    protected final GenericDao<E, ID> dao;
    protected final GenericMapper<RQ, RS, E, ID> mapper;
    protected final MessageSource messageSource;

    public GenericServiceImpl(
            GenericDao<E, ID> dao,
            GenericMapper<RQ, RS, E, ID> mapper,
            MessageSource messageSource) {

        this.dao = dao;
        this.mapper = mapper;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RS> findAll() {
        return dao
                .findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RS> paginatedList(Pageable pageable) {
        return dao.findAll(pageable).map(mapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public RQ findById(ID id) {
        return dao
                .findById(id)
                .map(mapper::toRequestDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormat.format(
                                messageSource.getMessage(
                                        "servicio.entidad.no-encontrada", null, LocaleContextHolder.getLocale()),
                                id.toString())));
    }

    @Override
    @Transactional
    public void save(RQ dto) {
        dao.save(mapper.toEntity(dto));
    }

    @Override
    @Transactional
    public void delete(ID id) {
        dao.deleteById(id);
    }

}
