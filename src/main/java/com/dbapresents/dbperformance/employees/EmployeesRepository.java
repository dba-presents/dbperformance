package com.dbapresents.dbperformance.employees;

import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesRepository extends Repository<Employee, Integer> {

    List<Employee> findByHireDateAfterOrderByHireDateDesc(LocalDate hireDateFrom);

}
