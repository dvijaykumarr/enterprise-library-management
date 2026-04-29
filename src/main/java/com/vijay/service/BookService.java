package com.vijay.service;

import com.vijay.payload.dto.BookDTO;
import com.vijay.payload.request.BookSearchRequest;
import com.vijay.payload.response.PageResponse;

import java.util.List;

public interface BookService {

    BookDTO createBook(BookDTO bookDTO);
    List<BookDTO> createBooksInBulk(List<BookDTO> bookDTOs);
    BookDTO getBookById(Long bookId);
    BookDTO getBookByISBN(String isbn);
    BookDTO updateBook(Long bookId, BookDTO bookDTO);
    void deleteBook(Long bookId);
    void hardDeleteBook(Long bookId);

    PageResponse<BookDTO> searchBookWithFilters(
            BookSearchRequest searchRequest
    );

    long getTotalActiveBooks();

    long getTotalAvailableBooks();


}
