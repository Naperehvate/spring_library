package org.example.spring_library;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long>
{
    List<Book> searchBookByTitleContainsIgnoreCase(String searchQuery);
}
