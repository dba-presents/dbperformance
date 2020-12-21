package com.dbapresents.dbperformance.employees.salaries;

import com.dbapresents.dbperformance.employees.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@AllArgsConstructor
@Service
public class SalaryDbService {

    private final EntityManager entityManager;
    private final SalariesRepository salariesRepository;

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

    @Transactional
    public void updateSalary(Employee employee, Salary currentSalary, Integer newSalaryValue) {
        terminateCurrentSalary(currentSalary);
        addNewSalary(employee, newSalaryValue);
        throw new RuntimeException("Throw it just to avoid necessity to roll it back manually.");
    }

    private Salary terminateCurrentSalary(Salary currentSalary) {
        currentSalary.setToDate(LocalDate.now());
        return salariesRepository.saveAndFlush(currentSalary);
    }

    private void addNewSalary(Employee employee, Integer salary) {
        Salary currentSalary = new Salary();
        currentSalary.setEmployee(employee);
        currentSalary.setSalary(salary);
        currentSalary.setFromDate(LocalDate.now());
        currentSalary.setToDate(LocalDate.of(9999, Month.JANUARY, 1));
        salariesRepository.saveAndFlush(currentSalary);
    }

}
