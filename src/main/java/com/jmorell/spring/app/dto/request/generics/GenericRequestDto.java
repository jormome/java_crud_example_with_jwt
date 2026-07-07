package com.jmorell.spring.app.dto.request.generics;

import java.io.Serializable;

public interface GenericRequestDto<ID extends Serializable> {

    ID id();
}
