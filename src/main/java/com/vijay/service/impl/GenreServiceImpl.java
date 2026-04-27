package com.vijay.service.impl;

import com.vijay.mapper.GenreMapper;
import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;
import com.vijay.repository.GenreRepository;
import com.vijay.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = GenreMapper.toEntity(genreDTO);

        //parent genre is handled here
        // Set parentGenre if parentGenreId is provided
        if (genreDTO.getParentGenreId() != null) {
            Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId())
                    .orElseThrow(() -> new RuntimeException("Parent genre not found with id: " + genreDTO.getParentGenreId()));
            genre.setParentGenre(parentGenre);
        }

        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.toDto(savedGenre);
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GenreDTO getGenreById(Long genreId) {
        return null;
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genre) {
        return null;
    }

    @Override
    public void deleteGenre(Long genreId) {

    }

    @Override
    public void hardDeleteGenre(Long genreId) {

    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGenres() {
        return List.of();
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        return List.of();
    }

    @Override
    public long getTotalActiveGenres() {
        return 0;
    }

    @Override
    public long getBookCountByGenre(Long genreId) {
        return 0;
    }
}
