package org.example.spring_library.repositories;

import org.example.spring_library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long>
{
    List<Book> searchBookByTitleContainsIgnoreCase(String searchQuery);
}
