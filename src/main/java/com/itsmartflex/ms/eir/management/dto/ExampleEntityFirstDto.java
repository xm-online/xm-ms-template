package com.itsmartflex.ms.eir.management.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ExampleEntityFirstDto implements Serializable {

    private Long id;

    private String name;

    private ExampleEntitySecondDto exampleEntitySecond;
}
