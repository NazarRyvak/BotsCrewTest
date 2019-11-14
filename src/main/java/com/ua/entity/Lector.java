package com.ua.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = {"departments"})
@ToString(exclude = {"departments"})
@NoArgsConstructor
@AllArgsConstructor
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double salary;

    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "department_has_lector",
            joinColumns = @JoinColumn(name = "lector_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments;
}
