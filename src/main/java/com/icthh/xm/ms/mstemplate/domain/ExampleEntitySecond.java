package com.icthh.xm.ms.mstemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A ExampleEntitySecond.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "example_entity_second")
@ToString
public class ExampleEntitySecond implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "exampleEntitySecond")
    @JsonIgnoreProperties(value = { "exampleEntitySecond" }, allowSetters = true)
    private Set<ExampleEntityFirst> exampleEntityFirsts = new HashSet<>();

    public ExampleEntitySecond addExampleEntityFirst(ExampleEntityFirst exampleEntityFirst) {
        this.exampleEntityFirsts.add(exampleEntityFirst);
        exampleEntityFirst.setExampleEntitySecond(this);
        return this;
    }

    public ExampleEntitySecond removeExampleEntityFirst(ExampleEntityFirst exampleEntityFirst) {
        this.exampleEntityFirsts.remove(exampleEntityFirst);
        exampleEntityFirst.setExampleEntitySecond(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExampleEntitySecond)) {
            return false;
        }
        return id != null && id.equals(((ExampleEntitySecond) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
