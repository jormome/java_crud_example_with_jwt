package com.jmorell.spring.app.dto.response.generics;

import java.io.Serializable;

public interface GenericResponseDto<ID extends Serializable> {

    ID id();
}
