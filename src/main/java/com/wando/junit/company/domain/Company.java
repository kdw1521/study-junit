package com.wando.junit.company.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Company {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 20, nullable = false)
    private String companyName;

    @Column(length = 20, nullable = false)
    private String ceoName;

    @Builder
    public Company(Long id, String companyName, String ceoName) {
        this.id = id;
        this.companyName = companyName;
        this.ceoName = ceoName;
    }
}
