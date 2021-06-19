package com.github.darianbeluzzo.graphqlsimpleblog.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Comment {

    @Column
    private String body;

    @Column
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comment")
    @SequenceGenerator(name = "seq_comment", sequenceName = "seq_comment", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Long postId;
}
