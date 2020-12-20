package com.dbapresents.dbperformance.employees.salaries;

import com.dbapresents.dbperformance.employees.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalariesRepository extends JpaRepository<Salary, SalaryCompositeKey> {

    List<Salary> findByEmployeeOrderByToDate(Employee employee);

}
