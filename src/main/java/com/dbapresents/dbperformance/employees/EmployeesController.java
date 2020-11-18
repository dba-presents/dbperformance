package com.dbapresents.dbperformance.employees;

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

}
