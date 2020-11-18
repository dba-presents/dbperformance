package com.dbapresents.dbperformance.employees;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeDto {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final LocalDate hireDate;
}
