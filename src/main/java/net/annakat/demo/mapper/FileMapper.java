package net.annakat.demo.mapper;

import net.annakat.demo.dto.FileDto;
import net.annakat.demo.model.FileEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileDto map(FileEntity file);

    @InheritInverseConfiguration
    FileEntity map(FileDto dto);
}
