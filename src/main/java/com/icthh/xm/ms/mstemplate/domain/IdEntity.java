//package com.icthh.xm.ms.mstemplate.domain;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.MappedSuperclass;
//import jakarta.persistence.SequenceGenerator;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.io.Serializable;
//
//@Getter
//@Setter
//@MappedSuperclass
//public class IdEntity<PK extends Serializable> {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    @Column(name = "id")
//    private PK id;
//}
