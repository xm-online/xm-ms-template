package com.itsmartflex.ms.eir.management.access;

import com.icthh.xm.commons.permission.access.AbstractResourceFactory;
import com.icthh.xm.commons.permission.access.repository.ResourceRepository;
import com.itsmartflex.ms.eir.management.repository.ExampleEntityFirstRepository;
import com.itsmartflex.ms.eir.management.repository.ExampleEntitySecondRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemplateResourceFactory extends AbstractResourceFactory {

    private final ExampleEntityFirstRepository exampleEntityFirstRepository;
    private final ExampleEntitySecondRepository exampleEntitySecondRepository;

    @Override
    protected Map<String, ? extends ResourceRepository<?, ?>> getRepositories() {
        return Map.of(
            "exampleEntityFirst", exampleEntityFirstRepository,
            "exampleEntitySecond", exampleEntitySecondRepository
        );
    }
}
