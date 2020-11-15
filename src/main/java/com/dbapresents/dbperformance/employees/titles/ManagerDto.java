package com.dbapresents.dbperformance.employees.titles;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ManagerDto {
    private final String firstName;
    private final String lastName;
    private final LocalDate fromDate;
    private final LocalDate toDate;
}
