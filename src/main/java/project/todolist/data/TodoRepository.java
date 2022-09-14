package project.todolist.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.todolist.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}