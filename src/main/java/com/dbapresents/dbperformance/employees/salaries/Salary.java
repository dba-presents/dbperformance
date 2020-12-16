package com.dbapresents.dbperformance.employees.salaries;

import com.dbapresents.dbperformance.employees.Employee;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Entity(name = "salaries")
@IdClass(SalaryCompositeKey.class)
public class Salary {

    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Id
    @Column(name = "salary")
    private Integer salary;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

}

class SalaryCompositeKey implements Serializable {
    private Integer employee;
    private Integer salary;
    private LocalDate fromDate;
}