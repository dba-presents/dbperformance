package com.dbapresents.dbperformance.employees.salaries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CurrentSalaryDto {
    private final Integer salary;
}
