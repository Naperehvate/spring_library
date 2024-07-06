package org.example.spring_library;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private IBookRepository bookRepository;

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "create-book";
    }

    @PostMapping
    public String createBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-book";
        }
        System.out.println(book);
        bookRepository.save(book);
        return "redirect:/api/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @Valid Book updatedBook, BindingResult result, Model model) {
        if (result.hasErrors()) {
            updatedBook.setId(id);
            model.addAttribute("book", updatedBook);
            return "edit-book";
        }
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        bookRepository.save(book);
        return "redirect:/api/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        bookRepository.delete(book);
        return "redirect:/api/books";
    }
}
