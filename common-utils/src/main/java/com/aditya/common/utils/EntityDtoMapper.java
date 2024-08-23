package com.aditya.common.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EntityDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public <D, T> D toDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }


    public <D, T> T toEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }


    public <D, T> T updateEntity(D dto, T entity) {
        modelMapper.map(dto, entity);
        return entity;
    }
}
