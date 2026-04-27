package com.vijay.service.impl;

import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;
import com.vijay.repository.GenreRepository;
import com.vijay.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;


    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {

//        return genreRepository.save(genreDTO);



        return null;
    }

}
