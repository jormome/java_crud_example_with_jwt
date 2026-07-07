package com.curso.spring.app.services.generics;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.curso.spring.app.dto.request.generics.GenericRequestDto;
import com.curso.spring.app.dto.response.generics.GenericResponseDto;

public interface GenericService<RQ extends GenericRequestDto<ID>, RS extends GenericResponseDto<ID>, ID extends Serializable> {

    List<RS> findAll();

    Page<RS> paginatedList(Pageable pageable);

    RQ findById(ID id);

    void save(RQ dto);

    void delete(ID id);
}
