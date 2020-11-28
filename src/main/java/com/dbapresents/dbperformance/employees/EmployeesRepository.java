package com.dbapresents.dbperformance.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByHireDateAfterOrderByHireDateDesc(LocalDate hireDateFrom);

    @Query("select count(e) " +
            "from employees e " +
            "where e.hireDate >= :hireDateFrom ")
    long countByHireDateAfter(LocalDate hireDateFrom);

}
