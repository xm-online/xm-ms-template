package com.icthh.xm.ms.mstemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.icthh.xm.ms.mstemplate.domain.converter.MapToStringConverter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.persistence.Convert;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "exampleEntitySecond")
    @JsonIgnoreProperties(value = { "exampleEntitySecond" }, allowSetters = true)
    private Set<ExampleEntityFirst> exampleEntityFirsts = new HashSet<>();

//    /**
//     * Content value as byte array
//     */
//    @NotNull
//    // @Lob // Do not use this annotation as it has different behaviour in Postgres and H2.
//    @Column(name = "jhi_value", nullable = false, columnDefinition="BLOB")
//    private byte[] jhiValue;
//
//    @JsonDeserialize(using = UntypedObjectDeserializer.class)
//    @Convert(converter = MapToStringConverter.class)
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(name = "data")
//    private Map<String, Object> data = new HashMap<>();

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
