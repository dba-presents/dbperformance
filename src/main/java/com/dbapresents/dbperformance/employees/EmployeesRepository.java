package com.dbapresents.dbperformance.employees;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByHireDateAfterOrderByHireDateDesc(LocalDate hireDateFrom, Pageable pageable);

    List<Employee> findByHireDateAfter(LocalDate hireDateFrom);

}
