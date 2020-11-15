package com.dbapresents.dbperformance.employees.titles;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface TitlesRepository extends Repository<Title, TitleCompositeKey> {

    List<Title> findByTitle(String title);

}
