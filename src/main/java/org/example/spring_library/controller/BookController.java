package org.example.spring_library.controller;

import jakarta.validation.Valid;
import org.example.spring_library.models.Book;
import org.example.spring_library.repositories.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class BookController {

    public BookController(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-book";
        }
        bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute("book") Book updatedBook, BindingResult result, Model model) {
        if (result.hasErrors()) {
            updatedBook.setId(id);
            model.addAttribute("book", updatedBook);

            // Логирование ошибок
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));

            return "edit-book";
        }

        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        bookRepository.save(book);

        System.out.println("Book updated: " + book); // Отладочное сообщение
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
        bookRepository.delete(book);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("searchQuery") String searchQuery,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Model model) {
        Page<Book> books = bookRepository.searchBookByTitleContainsIgnoreCase(searchQuery, PageRequest.of(page, size));
        model.addAttribute("books", books.getContent());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("currentPage", page);
        return "books";
    }
}
