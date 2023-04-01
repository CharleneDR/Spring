package com.wildcodeschool.myproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wildcodeschool.myproject.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @org.springframework.data.jpa.repository.Query(value = "SELECT b FROM Book b WHERE title LIKE %:searchTerm% OR description LIKE %:searchTermBis%")
    List<Book> findByTitleContainingOrContentContaining(@Param("searchTerm") String searchTerm, @Param("searchTermBis") String searchTermBis);
}
