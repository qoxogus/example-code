package com.jpa.entitygraph.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter
@NoArgsConstructor
public class Teacher {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    public Teacher(String name) {
        this.name = name;
    }
}