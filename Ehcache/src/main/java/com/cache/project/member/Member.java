package com.cache.project.member;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_username")
    private String username;

    @Column(name = "member_email")
    private String email;
}
