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
@Table(name = "DEPARTMENT")

public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employeeList = new HashSet<>();

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        headOfDepartment.getEmployeeList().add(employee);
    }
    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
        headOfDepartment.getEmployeeList().remove(employee);
    }

    @OneToOne(mappedBy = "department")
    private HeadOfDepartment headOfDepartment;

    @Column(name = "name_of_department")
    private String name;

    private String phoneNumber;
    private String email;

}


























