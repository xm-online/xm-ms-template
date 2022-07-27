package com.icthh.xm.ms.template.service;

import com.icthh.xm.commons.permission.annotation.FindWithPermission;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.commons.permission.repository.CriteriaPermittedRepository;
import com.icthh.xm.ms.template.domain.ExampleEntityFirst_;
import com.icthh.xm.ms.template.domain.ExampleEntitySecond;
import com.icthh.xm.ms.template.domain.ExampleEntitySecond_;
import com.icthh.xm.ms.template.repository.ExampleEntitySecondRepository;
import com.icthh.xm.ms.template.service.criteria.ExampleEntitySecondCriteria;
import com.icthh.xm.ms.template.service.dto.ExampleEntitySecondDto;
import com.icthh.xm.ms.template.service.mapper.ExampleEntitySecondMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link ExampleEntitySecond} entities in the database.
 * The main input is a {@link ExampleEntitySecondCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExampleEntitySecondDto} or a {@link Page} of {@link ExampleEntitySecondDto} which fulfills the criteria.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExampleEntitySecondQueryService extends QueryService<ExampleEntitySecond> {

    private final CriteriaPermittedRepository permittedRepository;

    private final ExampleEntitySecondRepository exampleEntitySecondRepository;

    private final ExampleEntitySecondMapper exampleEntitySecondMapper;

    /**
     * Return a {@link Page} of {@link ExampleEntitySecondDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @FindWithPermission("EXAMPLE_ENTITY_SECOND.GET_LIST")
    @PrivilegeDescription("Privilege to get all example entity second which matches the criteria from the database")
    public Page<ExampleEntitySecondDto> findByCriteria(ExampleEntitySecondCriteria criteria, Pageable page, String privilegeKey) {
        final Specification<ExampleEntitySecond> specification = createSpecification(criteria);
        return permittedRepository.findWithPermission(ExampleEntitySecond.class, specification, page, privilegeKey)
                .map(exampleEntitySecondMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    @FindWithPermission("EXAMPLE_ENTITY_SECOND.COUNT")
    @PrivilegeDescription("Privilege to get count of example entity second which matches the criteria from the database")
    public long countByCriteria(ExampleEntitySecondCriteria criteria, String privilegeKey) {
        final Specification<ExampleEntitySecond> specification = createSpecification(criteria);
        return permittedRepository.countByCondition(ExampleEntitySecond.class, specification, privilegeKey);
    }

    /**
     * Function to convert {@link ExampleEntitySecondCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExampleEntitySecond> createSpecification(ExampleEntitySecondCriteria criteria) {
        Specification<ExampleEntitySecond> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExampleEntitySecond_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ExampleEntitySecond_.name));
            }
            if (criteria.getExampleEntityFirstId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExampleEntityFirstId(),
                            root -> root.join(ExampleEntitySecond_.exampleEntityFirsts, JoinType.LEFT).get(ExampleEntityFirst_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
