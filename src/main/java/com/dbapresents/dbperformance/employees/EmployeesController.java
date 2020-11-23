package com.dbapresents.dbperformance.employees;

import com.dbapresents.dbperformance.employees.salaries.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestController
public class EmployeesController {

    private final EmployeesRepository employeesRepository;
    private final SalaryDbService salaryDbService;
    private final SalariesRepository salariesRepository;

    @GetMapping(path = "/api/employees/recent/", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getRecentEmployees() {
        LocalDate hireDateFrom = LocalDate.of(1999, Month.JANUARY, 1);
        return employeesRepository.findByHireDateAfterOrderByHireDateDesc(hireDateFrom, PageRequest.of(0, 10)).stream()
                .map(employee -> EmployeeDto.builder()
                        .id(employee.getId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .hireDate(employee.getHireDate())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/api/employees/since1990/count/", produces= MediaType.APPLICATION_JSON_VALUE)
    public long countEmployeesHiredSince1990() {
        LocalDate hireDateSince = LocalDate.of(1990, Month.JANUARY, 1);
        return employeesRepository.findByHireDateAfter(hireDateSince).size();
    }

    @Transactional
    @PostMapping(path = "/api/employee/{empNo}/salary/rise10p/", produces= MediaType.APPLICATION_JSON_VALUE)
    public void riseSalary(@PathVariable Integer empNo) {
        Employee employee = employeesRepository.getOne(empNo);
        List<Salary> salaries = salariesRepository.findByEmployeeOrderByToDate(employee);
        Salary currentSalary = salaries.get(salaries.size() - 1);
        currentSalary = terminateCurrentSalary(currentSalary);
        Integer newSalaryValue = calculateNewSalary(currentSalary);
        addNewSalary(employee, newSalaryValue);
        notifyAccountSystem(employee, newSalaryValue);
        throw new RuntimeException("Throw it just to avoid necessity to roll it back manually.");
    }

    @GetMapping(path = "/api/employee/{empNo}/salary/", produces= MediaType.APPLICATION_JSON_VALUE)
    public CurrentSalaryDto getCurrentSalary(@PathVariable Integer empNo) {
        return salariesRepository.findCurrentSalary(empNo)
                .map(salary -> CurrentSalaryDto.builder()
                        .salary(salary.getSalary())
                        .build())
                .orElse(CurrentSalaryDto.builder().build());
    }

    @GetMapping(path = "/api/employees/recent/salaryhistory/", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeSalaryDto> getManagersSalaryHistory() {
        LocalDate hireDateFrom = LocalDate.of(1999, Month.JANUARY, 1);
        return employeesRepository.findByHireDateAfterOrderByHireDateDesc(hireDateFrom).stream()
                .limit(5)
                .map(employee -> EmployeeSalaryDto.builder()
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .salaries(getSalariesByEmployee(employee))
                        .build())
                .collect(Collectors.toList());
    }

    private List<SalaryDto> getSalariesByEmployee(Employee employee) {
        return salaryDbService.getSalaryHistory(employee.getId()).stream()
                .map(salary -> SalaryDto.builder()
                        .salary(salary.getSalary())
                        .fromDate(salary.getFromDate())
                        .toDate(salary.getToDate())
                        .build())
                .collect(Collectors.toList());
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

    private Integer calculateNewSalary(Salary currentSalary) {
        // complex logic simulated by sleep below
        log.info("long operation - calculateNewSalary");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (int) Math.floor(currentSalary.getSalary() * 1.1);
    }

    private void notifyAccountSystem(Employee employee, Integer newSalaryValue) {
        // notify accounting about a new salary, simulated by sleep below
        log.info("long operation - notifyAccountSystem");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
