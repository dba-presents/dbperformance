package com.dbapresents.dbperformance.employees;

import com.dbapresents.dbperformance.employees.salaries.SalaryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EmployeeSalaryDto {
    private final String firstName;
    private final String lastName;
    private final List<SalaryDto> salaries;
}
