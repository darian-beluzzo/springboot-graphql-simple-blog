package com.github.darianbeluzzo.graphqlsimpleblog.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorInput {

    private String email;

    private Long id;

    private String name;

    private String username;

}
