package com.vijay.service;


import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;

public interface GenreService {

    GenreDTO createGenre(GenreDTO genre);
}
