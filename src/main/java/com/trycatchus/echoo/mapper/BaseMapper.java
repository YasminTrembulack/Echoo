package com.trycatchus.echoo.mapper;

public interface BaseMapper<P, E, R> {
    R toResponse(E entity);
    E toEntity(P dto);
}