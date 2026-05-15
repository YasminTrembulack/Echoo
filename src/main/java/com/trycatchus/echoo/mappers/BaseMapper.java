package com.trycatchus.echoo.mappers;

public interface BaseMapper<P, E, R> {
    R toResponse(E entity);
    E toEntity(P dto);
}