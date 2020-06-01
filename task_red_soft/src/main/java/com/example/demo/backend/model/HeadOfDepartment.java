package com.example.demo.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HEAD_OF_DEPARTMENT")

public class HeadOfDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "head_of_department", fetch = FetchType.EAGER)
    private Set<Employee> employeeList = new HashSet<>();

    @Column(name = "head_of_department_firstName")
    private String firstName;

    @Column(name = "head_of_department_lastName")
    private String lastName;

}
