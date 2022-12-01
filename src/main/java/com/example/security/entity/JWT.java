package com.example.security.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class JWT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userd_id", referencedColumnName = "id")
    private User user;

    private String token;
}
