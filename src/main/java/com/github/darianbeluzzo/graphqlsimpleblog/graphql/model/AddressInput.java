package com.github.darianbeluzzo.graphqlsimpleblog.graphql.model;

import lombok.Data;

@Data
public class AddressInput {

    private String city;

    private String state;

    private String street;

    private String suite;

    private String zipcode;
}
