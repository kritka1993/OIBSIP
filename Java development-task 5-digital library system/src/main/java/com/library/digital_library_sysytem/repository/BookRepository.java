package com.library.digital_library_sysytem.repository;

import com.library.digital_library_sysytem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(String category);
    List<Book> findByTitleContainingOrAuthorContaining(String title, String author);
}
