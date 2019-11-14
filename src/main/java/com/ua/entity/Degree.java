package com.ua.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"lectors"})
@ToString(exclude = {"lectors"})
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "degree", fetch = FetchType.LAZY)
    private List<Lector> lectors;
}
