package com.mindzone.mapper;

public interface ModelMapperDtoToEntity<D, E> {
  E toEntity(D d);
}
