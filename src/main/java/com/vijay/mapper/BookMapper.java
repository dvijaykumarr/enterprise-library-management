package com.vijay.mapper;

import com.vijay.modal.Book;
import com.vijay.payload.dto.BookDTO;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = BookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublishedDate())
                .language(book.getLanguage())
                .pages(book.getPages())
                .description(book.getDescription())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .price(book.getPrice())
                .coverImageUrl(book.getCoverImageUrl())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();

        // we get❌ NullPointerException if genre is null
        // we can write these within the build() itself
//        But wait — can genre ever be null in your case?
//        Looking at your Book entity:

//        java@JoinColumn(nullable = false)
//        @ManyToOne
//        private Genre genre;
//        You have nullable = false — so genre is always required!
//        So technically you could do:

//        but still this is a good programming practice
        if (book.getGenre() != null) {
            dto.setGenreId(book.getGenre().getId());
            dto.setGenreName(book.getGenre().getName());
            dto.setGenreCode(book.getGenre().getCode());
        }

        return dto;
    }

    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) return null;

        return Book.builder()
                .id(bookDTO.getId())
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .publishedDate(bookDTO.getPublicationDate())
                .language(bookDTO.getLanguage())
                .pages(bookDTO.getPages())
                .description(bookDTO.getDescription())
                .totalCopies(bookDTO.getTotalCopies())
                .availableCopies(bookDTO.getAvailableCopies())
                .price(bookDTO.getPrice())
                .coverImageUrl(bookDTO.getCoverImageUrl())
                .active(bookDTO.getActive() != null ? bookDTO.getActive() : true)
                .build();
        // Note: genre is handled in service layer
        // same reason as GenreMapper - DB lookup should not be in mapper
    }
}