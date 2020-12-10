package com.dbapresents.dbperformance.employees.titles;

import com.dbapresents.dbperformance.employees.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "titles")
@IdClass(TitleCompositeKey.class)
public class Title {
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Id
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}

class TitleCompositeKey implements Serializable {
    private Integer employee;
    private String title;
    private LocalDate fromDate;
}