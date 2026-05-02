package com.vijay.controller;


import com.vijay.exception.BookException;

import com.vijay.payload.dto.BookDTO;
import com.vijay.payload.request.BookSearchRequest;
import com.vijay.payload.response.ApiResponse;
import com.vijay.payload.response.BookStatsResponse;
import com.vijay.payload.response.PageResponse;
import com.vijay.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {

        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBook);

    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookDTO>> createBooksInBulk(@Valid @RequestBody List<BookDTO> bookDTOS) throws BookException {

        List<BookDTO> createdBook = bookService.createBooksInBulk(bookDTOS);
        return ResponseEntity.ok(createdBook);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws BookException {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByISBN(@PathVariable String isbn) throws BookException {
        BookDTO book = bookService.getBookByISBN(isbn);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) throws BookException {

        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) throws BookException {

        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book soft deleted successfully", true));
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable Long id) throws BookException {

        bookService.hardDeleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book Hard deleted successfully", true));
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<BookDTO>> advancedSearch(@RequestBody BookSearchRequest searchRequest){

        PageResponse<BookDTO> books = bookService.searchBookWithFilters(searchRequest);
        return ResponseEntity.ok(books);

    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats(){
        long totalActive = bookService.getTotalActiveBooks();
        long totalAvailable = bookService.getTotalAvailableBooks();

        BookStatsResponse stats = new BookStatsResponse(totalActive, totalAvailable);
        return ResponseEntity.ok(stats);
    }



}
