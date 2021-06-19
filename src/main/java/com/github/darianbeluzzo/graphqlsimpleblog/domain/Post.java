package com.github.darianbeluzzo.graphqlsimpleblog.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
@Data
public class Post {

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Author.class)
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private Author author;

    @Column(name = "author_id", updatable = true, insertable = true)
    private Long authorId;

    @Column
    private String body;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Collection<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_post")
    @SequenceGenerator(name = "seq_post", sequenceName = "seq_post", allocationSize = 1)
    private Long id;

    @Column
    private String title;
}
