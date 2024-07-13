package org.example.spring_library.repositories;

import org.example.spring_library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Page<Book> searchBookByTitleContainsIgnoreCase(String searchQuery, Pageable pageable);
}