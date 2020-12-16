package com.dbapresents.dbperformance.employees.salaries;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
@Service
public class SalaryDbService {

    private final EntityManager entityManager;

    public List<Salary> getSalaryHistory(Integer empNo) {
        return entityManager.createQuery(
                "select s " +
                        "from salaries s " +
                        "where s.employee.id = " + empNo + " " +
                        "order by s.fromDate",
                    Salary.class)
                .getResultList();
    }

}
