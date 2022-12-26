package com.example.location.mapper;

import com.example.location.dto.PortDTO;
import com.example.location.entity.JpaPort;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "default", uses = {CycleAvoidingMappingContext.class})
public interface PortMapper extends ICustomGenericMapper<PortDTO, JpaPort> {
    public static PortMapper INSTANCE = Mappers.getMapper( PortMapper.class );

}
