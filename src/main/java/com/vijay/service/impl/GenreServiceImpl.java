package com.vijay.service.impl;

import com.vijay.mapper.GenreMapper;
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
        Genre genre = GenreMapper.toEntity(genreDTO);

        // Set parentGenre if parentGenreId is provided
        if (genreDTO.getParentGenreId() != null) {
            Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId())
                    .orElseThrow(() -> new RuntimeException("Parent genre not found with id: " + genreDTO.getParentGenreId()));
            genre.setParentGenre(parentGenre);
        }

        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.toDto(savedGenre);
    }
}
