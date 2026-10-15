package com.icthh.xm.ms.mstemplate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.icthh.xm.commons.permission.annotation.FindWithPermission;
import com.icthh.xm.ms.mstemplate.AbstractUnitTest;
import com.icthh.xm.ms.mstemplate.service.criteria.ExampleEntityFirstCriteria;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

class ExampleEntityFirstQueryServiceUnitTest extends AbstractUnitTest {

    @Test
    void findByCriteriaIsGuardedByOwnEntityPrivilege() throws NoSuchMethodException {
        Method method = ExampleEntityFirstQueryService.class
            .getMethod("findByCriteria", ExampleEntityFirstCriteria.class, Pageable.class, String.class);

        assertEquals("EXAMPLE_ENTITY_FIRST.GET_LIST", privilegeKeyOf(method));
    }

    @Test
    void countByCriteriaIsGuardedByOwnEntityPrivilege() throws NoSuchMethodException {
        Method method = ExampleEntityFirstQueryService.class
            .getMethod("countByCriteria", ExampleEntityFirstCriteria.class, String.class);

        assertEquals("EXAMPLE_ENTITY_FIRST.COUNT", privilegeKeyOf(method));
    }

    private static String privilegeKeyOf(Method method) {
        FindWithPermission annotation = method.getAnnotation(FindWithPermission.class);
        assertNotNull(annotation, method.getName() + " must be annotated with @FindWithPermission");
        return annotation.value();
    }
}
