package com.github.darianbeluzzo.graphqlsimpleblog.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Geo {

    @Column
    private Double latitude;

    @Column
    private Double longitude;
}
