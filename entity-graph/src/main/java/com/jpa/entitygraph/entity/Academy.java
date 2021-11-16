package com.jpa.entitygraph.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity @Getter
@NoArgsConstructor
public class Academy {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "academy_id")
    private List<Subject> subjects = new ArrayList<>();

    @Builder
    public Academy(String name, List<Subject> subjects) {
        this.name = name;
        if(subjects != null){
            this.subjects = subjects;
        }
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
        subject.updateAcademy(this);
    }
}
