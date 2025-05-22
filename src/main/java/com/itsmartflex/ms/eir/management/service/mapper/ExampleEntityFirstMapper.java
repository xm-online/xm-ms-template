package com.itsmartflex.ms.eir.management.service.mapper;

import com.itsmartflex.ms.eir.management.domain.ExampleEntityFirst;
import com.itsmartflex.ms.eir.management.domain.ExampleEntitySecond;
import com.itsmartflex.ms.eir.management.dto.ExampleEntityFirstDto;
import com.itsmartflex.ms.eir.management.dto.ExampleEntitySecondDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ExampleEntityFirst} and its DTO {@link ExampleEntityFirstDto}.
 */
@Mapper(componentModel = "spring")
public interface ExampleEntityFirstMapper extends
    EntityMapper<ExampleEntityFirstDto, ExampleEntityFirst> {

    @Mapping(target = "exampleEntitySecond", source = "exampleEntitySecond", qualifiedByName = "exampleEntitySecondId")
    ExampleEntityFirstDto toDto(ExampleEntityFirst s);

    @Named("exampleEntitySecondId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ExampleEntitySecondDto toDtoExampleEntitySecondId(ExampleEntitySecond exampleEntitySecond);
}
