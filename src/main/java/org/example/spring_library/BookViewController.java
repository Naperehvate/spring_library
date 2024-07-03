package org.example.spring_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/view/books")
public class BookViewController
{
    @Autowired
    private IBookRepository bookRepository;

    @GetMapping
    public String getAllBooks(Model model)
    {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model)
    {
        model.addAttribute("book", new Book());
        return "create-book";
    }

    @GetMapping("/edit/{id}")
    public String ShowEditForm(@PathVariable Long id, Model model)
    {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "edit-book";
    }

}
