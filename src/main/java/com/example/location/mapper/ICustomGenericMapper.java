package com.example.location.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@MapperConfig(
        componentModel = "default",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG
)
public interface ICustomGenericMapper<D, E> {

    CycleAvoidingMappingContext cycleAvoidingMappingContext = new CycleAvoidingMappingContext();

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    @InheritInverseConfiguration
    List<D> toDto(List<E> entityList);

    default Page<D> toDtoPage(Page<E> entityPage){
        return entityPage.map(this::toDto);
    }

    void updateEntity(D dto, @MappingTarget E entity);


}
