package com.vijay.service.impl;

import com.vijay.exception.BookException;
import com.vijay.mapper.BookMapper;
import com.vijay.modal.Book;
import com.vijay.modal.Genre;
import com.vijay.payload.dto.BookDTO;
import com.vijay.payload.request.BookSearchRequest;
import com.vijay.payload.response.PageResponse;
import com.vijay.repository.BookRepository;
import com.vijay.repository.GenreRepository;
import com.vijay.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;
    private final GenreRepository genreRepository; // ← needed for genre lookup

    @Override
    public BookDTO createBook(BookDTO bookDTO) throws BookException {

        // provided isbn from user should not be duplicate
        if(bookRepository.existsByIsbn(bookDTO.getIsbn())){
            throw new BookException("book with ISBN "+ bookDTO.getIsbn() + " already exists");
        }

        Book book = BookMapper.toEntity(bookDTO);

        // what if user provides
        // total - 10
        // available - 11
        // so we should restrict user not to do this

        book.isAvailableCopiesValid();

        Book savedBook = bookRepository.save(book);

        return BookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookDTO> createBooksInBulk(List<BookDTO> bookDTOs) throws BookException {

        List<BookDTO> createdBooks = new ArrayList<>();
        for(BookDTO bookDTO : bookDTOs){
            BookDTO book = createBook(bookDTO);
            createdBooks.add(book);
        }
        return createdBooks;
    }

    @Override
    public BookDTO getBookById(Long bookId) throws BookException {


        Book book = bookRepository.findById(bookId).orElseThrow(
                ()->new BookException("Book not found")
        );

        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO getBookByISBN(String isbn) throws BookException {

        Book book = bookRepository.findByIsbn(isbn).orElseThrow(
                ()->new BookException("Book not found")
        );

        return BookMapper.toDTO(book);
    }

    @Override
    public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {

        // check wether book exists or not
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );

        // update simple fields directly - same approach as Genre
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setPublishedDate(bookDTO.getPublicationDate());
        existingBook.setLanguage(bookDTO.getLanguage());
        existingBook.setPages(bookDTO.getPages());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setTotalCopies(bookDTO.getTotalCopies());
        existingBook.setAvailableCopies(bookDTO.getAvailableCopies());
        existingBook.setPrice(bookDTO.getPrice());
        existingBook.setCoverImageUrl(bookDTO.getCoverImageUrl());
        existingBook.setActive(bookDTO.getActive() != null ? bookDTO.getActive() : existingBook.getActive());

        // genre lookup handled in service - same reason as createBook
        if (bookDTO.getGenreId() != null) {
            Genre genre = genreRepository.findById(bookDTO.getGenreId())
                    .orElseThrow(() -> new BookException("Genre not found with id: " + bookDTO.getGenreId()));
            existingBook.setGenre(genre);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return BookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) throws BookException {

        // check wether book exists or not
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );

//        soft delete - inactive ga set chestunnam
        existingBook.setActive(false);
        bookRepository.save(existingBook);

    }

    @Override
    public void hardDeleteBook(Long bookId) throws BookException {

        // check wether book exists or not
        Book existingBook = bookRepository.findById(bookId).orElseThrow(
                () -> new BookException("Book not found with id: " + bookId)
        );

        //hard delete
        bookRepository.delete(existingBook);

    }

    @Override
    public PageResponse<BookDTO> searchBookWithFilters(BookSearchRequest searchRequest) {

        Pageable pageable = createPageable(searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection());
        Page<Book> bookPage = bookRepository.searchBookWithFilters(
                searchRequest.getSearchTerm(),
                searchRequest.getGenreId(),
                searchRequest.getAvailableOnly(),
                pageable

        );
        return convertToPageResponse(bookPage);
    }

    @Override
    public long getTotalActiveBooks() {
        return bookRepository.countByActiveTrue();
    }

    @Override
    public long getTotalAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

//    helper method
    private Pageable createPageable(int page, int size, String sortBy, String sortDirection){
        size = Math.min(size, 10);
        size = Math.max(size, 1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(page,size,sort);

    }

//    helper method
    private PageResponse<BookDTO> convertToPageResponse(Page<Book> books){
        List<BookDTO> bookDTOS = books.getContent()
                .stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(bookDTOS,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isLast(),
                books.isFirst(),
                books.isEmpty());
    }


}
