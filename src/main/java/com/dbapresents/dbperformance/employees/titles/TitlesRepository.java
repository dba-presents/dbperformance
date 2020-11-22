package com.dbapresents.dbperformance.employees.titles;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitlesRepository extends JpaRepository<Title, TitleCompositeKey> {

    List<Title> findByTitle(String title);

}
