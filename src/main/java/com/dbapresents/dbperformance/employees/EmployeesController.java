package com.dbapresents.dbperformance.employees;

import com.dbapresents.dbperformance.employees.salaries.SalaryDbService;
import com.dbapresents.dbperformance.employees.salaries.SalaryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class EmployeesController {

    private final EmployeesRepository employeesRepository;
    private final SalaryDbService salaryDbService;

    @GetMapping(path = "/api/employees/recent/", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> getRecentEmployees() {
        LocalDate hireDateFrom = LocalDate.of(1999, Month.JANUARY, 1);
        return employeesRepository.findByHireDateAfterOrderByHireDateDesc(hireDateFrom).stream()
                .limit(10)
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

}
