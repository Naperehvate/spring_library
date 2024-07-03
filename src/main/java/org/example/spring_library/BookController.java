package org.example.spring_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController                                 //Анотация что это контроллер
@RequestMapping("/api/books")                 //Анотация для маршрутизации
public class BookController {
    @Autowired                                   //Анотация для внедрения зависимостей
    private IBookRepository bookRepository;

    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> GetBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> CreateBook(@RequestBody Book book)
    {
       return ResponseEntity.ok(bookRepository.save(book));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Book> UpdateBook(@PathVariable Long id, @RequestBody Book updatedBook)
    {
       Optional<Book> book = bookRepository.findById(id);
       if (book.isPresent()) {
           book.get().setTitle(updatedBook.getTitle());
           book.get().setAuthor(updatedBook.getAuthor());
           return ResponseEntity.ok(bookRepository.save(book.get()));
       } else {
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> DeleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
