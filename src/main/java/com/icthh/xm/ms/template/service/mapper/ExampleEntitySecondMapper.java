package com.icthh.xm.ms.template.service.mapper;

import com.icthh.xm.ms.template.domain.ExampleEntitySecond;
import com.icthh.xm.ms.template.service.dto.ExampleEntitySecondDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ExampleEntitySecond} and its DTO {@link ExampleEntitySecondDto}.
 */
@Mapper(componentModel = "spring")
public interface ExampleEntitySecondMapper extends EntityMapper<ExampleEntitySecondDto, ExampleEntitySecond> {}
