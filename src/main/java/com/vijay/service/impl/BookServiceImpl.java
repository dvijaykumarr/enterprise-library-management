package com.vijay.service.impl;

import com.vijay.payload.dto.BookDTO;
import com.vijay.payload.request.BookSearchRequest;
import com.vijay.payload.response.PageResponse;
import com.vijay.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public List<BookDTO> createBooksInBulk(List<BookDTO> bookDTOs) {
        return List.of();
    }

    @Override
    public BookDTO getBookById(Long bookId) {
        return null;
    }

    @Override
    public BookDTO getBookByISBN(String isbn) {
        return null;
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBook(Long bookId) {

    }

    @Override
    public void hardDeleteBook(Long bookId) {

    }

    @Override
    public PageResponse<BookDTO> searchBookWithFilters(BookSearchRequest searchRequest) {
        return null;
    }

    @Override
    public long getTotalActiveBooks() {
        return 0;
    }

    @Override
    public long getTotalAvailableBooks() {
        return 0;
    }
}
