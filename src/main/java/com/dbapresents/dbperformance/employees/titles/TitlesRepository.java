package com.dbapresents.dbperformance.employees.titles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface TitlesRepository extends JpaRepository<Title, TitleCompositeKey> {

    @Query("select t " +
            "from titles t" +
            "  join fetch t.employee " +
            "where t.title = :title")
    List<Title> findByTitle(String title);
    List<Title> findByTitleAndToDateAfter(String title, LocalDate afterDate);

    @Transactional
    @Modifying
    @Query("update titles t " +
            "set t.toDate = :newDate " +
            "where t.title = :title " +
            "  and t.toDate >= :afterDate ")
    void updateToDate(String title, LocalDate afterDate, LocalDate newDate);

}
