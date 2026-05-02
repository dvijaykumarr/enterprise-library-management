package com.vijay.service;

import com.vijay.exception.BookException;
import com.vijay.payload.dto.BookDTO;
import com.vijay.payload.request.BookSearchRequest;
import com.vijay.payload.response.PageResponse;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO) throws BookException;
    List<BookDTO> createBooksInBulk(List<BookDTO> bookDTOs) throws BookException;
    BookDTO getBookById(Long bookId) throws BookException;
    BookDTO getBookByISBN(String isbn) throws BookException;
    BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException;
    void deleteBook(Long bookId) throws BookException;
    void hardDeleteBook(Long bookId) throws BookException;

    PageResponse<BookDTO> searchBookWithFilters(
            BookSearchRequest searchRequest
    );

    long getTotalActiveBooks();

    long getTotalAvailableBooks();


}
