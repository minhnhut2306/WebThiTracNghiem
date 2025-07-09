package com.tttn.webthitracnghiem.repository;

import com.tttn.webthitracnghiem.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
        @Query(value = "SELECT r.*, u.*, e.* " +
                        "FROM result r " +
                        "INNER JOIN ( " +
                        "    SELECT username, id_exam, MAX(mark) AS max_mark " +
                        "    FROM result " +
                        "    GROUP BY username, id_exam " +
                        ") sub ON r.username = sub.username AND r.id_exam = sub.id_exam AND r.mark = sub.max_mark " +
                        "LEFT JOIN user u ON r.username = u.id " +
                        "LEFT JOIN exam e ON r.id_exam = e.id " +
                        "ORDER BY r.mark DESC " +
                        "LIMIT 10", nativeQuery = true)
        List<Result> findTopTen();

        @Query(value = "SELECT * FROM result where username = :id ", nativeQuery = true)
        List<Result> findByHistory(@Param("id") String id);

        @Query(value = "SELECT SUM(mark)\n" +
                        "FROM result\n" +
                        "WHERE username= :id ", nativeQuery = true)
        Object findSum(@Param("id") String id);

        @Query(value = "SELECT AVG (mark)\n" +
                        "FROM result\n" +
                        "WHERE username= :id ", nativeQuery = true)
        Object findAvg(@Param("id") String id);

        @Query(value = "SELECT * FROM result where id_exam= :idExam order by mark desc", nativeQuery = true)
        List<Result> findTopByExam(@Param("idExam") int idExam);
}
