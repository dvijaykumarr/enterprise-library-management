package com.vijay.repository;

import com.vijay.modal.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);


    // book - java programming
    //prog ani kodthe - it will match this and returns the book

    //progies ani kodthe - idhi sequence ledu aa book title lo so it will return false(it will not return book)
    // ✅ Correct - group all search term conditions together
    @Query("SELECT b FROM Book b WHERE " +
            "(:searchTerm IS NULL OR " +
            "lower(b.title) LIKE lower(concat('%', :searchTerm, '%')) OR " +
            "lower(b.author) LIKE lower(concat('%', :searchTerm, '%')) OR " +
            "lower(b.isbn) LIKE lower(concat('%', :searchTerm, '%'))) AND " +
            "(:genreId IS NULL OR b.genre.id = :genreId) AND " +
            "(:availableOnly IS NULL OR :availableOnly = false OR b.availableCopies > 0) AND " +
            "b.active = true"
    )
    Page<Book> searchBookWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("availableOnly") Boolean availableOnly,
            Pageable pageable
    );

    long countByActiveTrue();

    @Query("SELECT count(b) FROM Book b WHERE b.availableCopies>0 AND b.active=true")
    long countAvailableBooks();

}
