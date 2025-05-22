package com.itsmartflex.ms.eir.management.service.mapper;

import com.itsmartflex.ms.eir.management.domain.ExampleEntitySecond;
import com.itsmartflex.ms.eir.management.dto.ExampleEntitySecondDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ExampleEntitySecond} and its DTO {@link ExampleEntitySecondDto}.
 */
@Mapper(componentModel = "spring")
public interface ExampleEntitySecondMapper extends EntityMapper<ExampleEntitySecondDto, ExampleEntitySecond> {

}
