package com.github.darianbeluzzo.graphqlsimpleblog.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Address {

    @Column
    private String city;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
    @SequenceGenerator(name = "seq_address", sequenceName = "seq_address", allocationSize = 1)
    private Long id;

    @Column
    private String state;

    @Column
    private String street;

    @Column
    private String suite;

    @Column
    private String zipcode;
}
