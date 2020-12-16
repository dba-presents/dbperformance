package com.dbapresents.dbperformance.employees.titles;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
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

    @PostMapping(path = "/api/title/{titleToLayoff}/groupLayoff/")
    public void groupLayoff(@PathVariable String titleToLayoff) {
        List<Title> titles = titlesRepository.findByTitleAndToDateAfter(titleToLayoff, LocalDate.now());
        titles.forEach(
                title -> title.setToDate(LocalDate.now())
        );
        titlesRepository.saveAll(titles);
    }

    @PostMapping(path = "/api/title/{titleToHireBack}/hireBack/")
    public void hireBack(@PathVariable String titleToHireBack) {
        titlesRepository.updateToDate(titleToHireBack, LocalDate.now().minus(20, ChronoUnit.DAYS), LocalDate.of(9999, Month.JANUARY, 1));
    }

}
