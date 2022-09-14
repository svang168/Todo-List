package project.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.todolist.data.TodoRepository;
import project.todolist.exception.ResourceNotFoundException;
import project.todolist.model.Todo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    // Find all todo list items
    @GetMapping("/todo")
    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    // Find only one todo list item
    @GetMapping("/todo/{id}")
    public Todo getTodoItemById(@PathVariable(value = "id") Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", todoId));
    }

    // Create a new todo list item
    @PostMapping("/todo")
    public Todo createTodoListItem(@Valid @RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // Update a todo item on the list
    @PutMapping("/todo/{id}")
    public Todo updateTodoListItem(@PathVariable(value = "id") Long todoId,
                                   @Valid @RequestBody Todo todoItem) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", todoId));

        todo.setTitle(todoItem.getTitle());
        todo.setDescription(todoItem.getDescription());
        todo.setIsCompleted(todoItem.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return updatedTodo;
    }

    // Delete a todo item
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable(value = "id") Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", todoId));

        todoRepository.delete(todo);

        return ResponseEntity.ok().build();
    }

}
