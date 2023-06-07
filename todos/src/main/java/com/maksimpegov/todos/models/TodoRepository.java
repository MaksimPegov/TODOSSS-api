package com.maksimpegov.todos.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Modifying
    @Query("DELETE FROM Todo as t WHERE t.userId = :userId")
    void deleteByUserId(@Param("userId") String userId);

    @Modifying
    @Query("SELECT t FROM Todo as t WHERE t.userId = :userId ORDER BY t.id")
    List<Todo> getAllByUserId(@Param("userId") String userId);
}
