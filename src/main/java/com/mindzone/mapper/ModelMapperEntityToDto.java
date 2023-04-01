package com.mindzone.mapper;

public interface ModelMapperEntityToDto<E, D> {

  D toDto(E e);
}
