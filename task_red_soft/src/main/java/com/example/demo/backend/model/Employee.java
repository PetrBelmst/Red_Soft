package com.example.demo.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private HeadOfDepartment headOfDepartment;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(name = "employee_firstName")
    private String firstName;

    @Column(name = "employee_lastName")
    private String lastName;

}
