package com.icthh.xm.ms.template.service;

import com.icthh.xm.commons.permission.annotation.FindWithPermission;
import com.icthh.xm.commons.permission.annotation.PrivilegeDescription;
import com.icthh.xm.commons.permission.repository.CriteriaPermittedRepository;
import com.icthh.xm.ms.template.domain.ExampleEntityFirst;
import com.icthh.xm.ms.template.domain.ExampleEntityFirst_;
import com.icthh.xm.ms.template.domain.ExampleEntitySecond;
import com.icthh.xm.ms.template.domain.ExampleEntitySecond_;
import com.icthh.xm.ms.template.repository.ExampleEntityFirstRepository;
import com.icthh.xm.ms.template.service.criteria.ExampleEntityFirstCriteria;
import com.icthh.xm.ms.template.service.dto.ExampleEntityFirstDto;
import com.icthh.xm.ms.template.service.mapper.ExampleEntityFirstMapper;
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
 * Service for executing complex queries for {@link ExampleEntityFirst} entities in the database.
 * The main input is a {@link ExampleEntityFirstCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExampleEntityFirstDto} or a {@link Page} of {@link ExampleEntityFirstDto} which fulfills the criteria.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExampleEntityFirstQueryService extends QueryService<ExampleEntityFirst> {

    private final CriteriaPermittedRepository permittedRepository;

    private final ExampleEntityFirstRepository exampleEntityFirstRepository;

    private final ExampleEntityFirstMapper exampleEntityFirstMapper;

    /**
     * Return a {@link Page} of {@link ExampleEntityFirstDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    @FindWithPermission("EXAMPLE_ENTITY_FIRST.GET_LIST")
    @PrivilegeDescription("Privilege to get all example entity first which matches the criteria from the database")
    public Page<ExampleEntityFirstDto> findByCriteria(ExampleEntityFirstCriteria criteria, Pageable page, String privilegeKey) {
        final Specification<ExampleEntityFirst> specification = createSpecification(criteria);
        return permittedRepository.findWithPermission(ExampleEntityFirst.class, specification, page, privilegeKey)
                .map(exampleEntityFirstMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    @FindWithPermission("EXAMPLE_ENTITY_SECOND.COUNT")
    @PrivilegeDescription("Privilege to get count of example entity second which matches the criteria from the database")
    public long countByCriteria(ExampleEntityFirstCriteria criteria, String privilegeKey) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExampleEntityFirst> specification = createSpecification(criteria);
        return permittedRepository.countByCondition(ExampleEntityFirst.class, specification, privilegeKey);
    }

    /**
     * Function to convert {@link ExampleEntityFirstCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExampleEntityFirst> createSpecification(ExampleEntityFirstCriteria criteria) {
        Specification<ExampleEntityFirst> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExampleEntityFirst_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ExampleEntityFirst_.name));
            }
            if (criteria.getExampleEntitySecondId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExampleEntitySecondId(),
                            root -> root.join(ExampleEntityFirst_.exampleEntitySecond, JoinType.LEFT).get(ExampleEntitySecond_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
