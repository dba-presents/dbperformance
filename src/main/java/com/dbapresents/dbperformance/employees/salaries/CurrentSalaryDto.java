package com.dbapresents.dbperformance.employees.salaries;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrentSalaryDto {
    private final Integer salary;
}
