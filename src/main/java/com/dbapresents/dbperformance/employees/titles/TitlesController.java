package com.dbapresents.dbperformance.employees.titles;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class TitlesController {

    private final TitlesRepository titlesRepository;

    @GetMapping(path = "/api/titles/manager/", produces= MediaType.APPLICATION_JSON_VALUE)
    public List<ManagerDto> getManagers() {
        return titlesRepository.findByTitle("Manager").stream()
                .map(title -> ManagerDto.builder()
                        .firstName(title.getEmployee().getFirstName())
                        .lastName(title.getEmployee().getLastName())
                        .fromDate(title.getFromDate())
                        .toDate(title.getToDate())
                        .build())
                .collect(Collectors.toList());
    }

}
