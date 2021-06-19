package com.github.darianbeluzzo.graphqlsimpleblog.graphql.model;

import lombok.Data;

@Data
public class PostInput {

    private Long authorId;

    private String body;

    private Long id;

    private String title;
}
