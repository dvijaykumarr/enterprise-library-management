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

    @Query("SELECT b FROM Book b WHERE " +
            "(:searchTerm IS NULL OR lower(b.title) LIKE lower(concat('%', :searchTerm, '%'))) AND " +
            "(:genreId IS NULL OR b.genre.id = :genreId) AND " +
            "(:availableOnly = false OR b.availableCopies > 0) AND " +
            "b.active = true"
    )
    Page<Book> searchBookWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("genreId") Long genreId,
            @Param("availableOnly") boolean availableOnly,
            Pageable pageable
    );

}
