package com.curso.spring.app.mappers.generics;

import java.io.Serializable;

import com.curso.spring.app.dto.request.generics.GenericRequestDto;
import com.curso.spring.app.dto.response.generics.GenericResponseDto;

public interface GenericMapper<RQ extends GenericRequestDto<ID>, RS extends GenericResponseDto<ID>, E, ID extends Serializable> {

    RQ toRequestDto(E entity);

    E toEntity(RQ dto);

    RS toResponseDto(E Entity);
}
