package com.jazva.challenge.entity;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
public class Location {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
