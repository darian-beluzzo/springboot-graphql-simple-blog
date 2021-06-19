package com.github.darianbeluzzo.graphqlsimpleblog.graphql.model;

import lombok.Data;

@Data
public class CommentInput {

    private String body;

    private String email;

    private Long id;

    private String name;

    private Long postId;
}
