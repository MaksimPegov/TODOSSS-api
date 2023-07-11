package com.maksimpegov.todos.todo;

import com.maksimpegov.todos.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // update/delete methods is transaction methods(in DB), so we need to add @Transactional annotation
    @Transactional
    @Modifying
    @Query("DELETE FROM Todo as t WHERE t.userId = :userId")
    void deleteTodosByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("SELECT t FROM Todo as t WHERE t.userId = :userId ORDER BY t.id")
    List<Todo> getAllByUserId(@Param("userId") Long userId);
}
