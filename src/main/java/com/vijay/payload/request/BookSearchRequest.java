package com.vijay.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {

    private String searchTerm;
    private Long genreId;
    private Boolean availableOnly;
    private Integer page=0;
    private Integer size = 20; // show only 20 content per page
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";




}
