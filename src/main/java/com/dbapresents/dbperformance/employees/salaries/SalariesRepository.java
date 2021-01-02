package com.dbapresents.dbperformance.employees.salaries;

import com.dbapresents.dbperformance.employees.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalariesRepository extends JpaRepository<Salary, SalaryCompositeKey> {

    List<Salary> findByEmployeeOrderByToDate(Employee employee);

    @Query("select s " +
            "from salaries s " +
            "where s.employee.id = :empNo " +
            "  and s.toDate = '9999-01-01'")
    Optional<Salary> findCurrentSalary(int empNo);

}
