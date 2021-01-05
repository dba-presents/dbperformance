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

    public CurrentSalaryDto getAvgSalaryOfAssistantEngineer() {
        List<Salary> salaries = entityManager.createQuery(
                "select s " +
                        "from salaries s " +
                        "   join s.employee e " +
                        "   join e.titles t " +
                        "where s.toDate = '9999-01-01' " +
                        "  and t.toDate = '9999-01-01' " +
                        "  and t.title = 'Assistant Engineer' ",
                Salary.class)
                .getResultList();

        if (salaries.isEmpty()) {
            return new CurrentSalaryDto(0);
        }

        long sumOfSalaries = salaries.stream()
                .mapToLong(Salary::getSalary)
                .sum();

        return new CurrentSalaryDto((int) (sumOfSalaries / salaries.size()));
    }

}
