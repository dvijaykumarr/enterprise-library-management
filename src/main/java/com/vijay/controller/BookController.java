package com.vijay.controller;


import com.vijay.modal.Book;
import com.vijay.payload.dto.BookDTO;
import com.vijay.repository.BookRepository;
import com.vijay.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;


}
