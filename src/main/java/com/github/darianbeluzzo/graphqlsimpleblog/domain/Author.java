package com.github.darianbeluzzo.graphqlsimpleblog.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table
@Data
public class Author {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Column
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_author")
    @SequenceGenerator(name = "seq_author", sequenceName = "seq_author", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Collection<Post> posts;

    @Column
    private String username;

}
