package com.dbapresents.dbperformance.employees;

import com.dbapresents.dbperformance.employees.titles.Title;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @OneToMany
    @JoinColumn(name = "emp_no")
    private List<Title> titles;
}
