package com.vijay.service.impl;

import com.vijay.exception.GenreException;
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
    public GenreDTO getGenreById(Long genreId) throws Exception {
        Genre genre = genreRepository.findById(genreId).orElseThrow(
                ()->new GenreException("genre not found")
        );
        return GenreMapper.toDto(genre);
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreException("Genre not found with id: " + genreId));

        // just set fields directly here - no need for mapper method
        existingGenre.setCode(genreDTO.getCode());
        existingGenre.setName(genreDTO.getName());
        existingGenre.setDescription(genreDTO.getDescription());
        existingGenre.setDisplayOrder(genreDTO.getDisplayOrder() != null ? genreDTO.getDisplayOrder() : 0);
        existingGenre.setActive(genreDTO.getActive() != null ? genreDTO.getActive() : existingGenre.getActive());

        // parent genre handled here in service
        if (genreDTO.getParentGenreId() != null) {
            Genre parentGenre = genreRepository.findById(genreDTO.getParentGenreId())
                    .orElseThrow(() -> new GenreException("Parent genre not found"));
            existingGenre.setParentGenre(parentGenre);
        } else {
            existingGenre.setParentGenre(null);
        }

        return GenreMapper.toDto(genreRepository.save(existingGenre));

    }

    @Override
    public void deleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                () -> new GenreException("Genre not found with id: " + genreId)
        );
        existingGenre.setActive(false);
        genreRepository.save(existingGenre);

    }

    @Override
    public void hardDeleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(
                () -> new GenreException("Genre not found with id: " + genreId)
        );
        genreRepository.delete(existingGenre);

    }

    @Override
    public List<GenreDTO> getAllActiveGenresWithSubGenres() {

        return genreRepository.findByActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDTO> getTopLevelGenres() {
        return genreRepository.findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(GenreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalActiveGenres() {
        return genreRepository.countByActiveTrue();
    }

    @Override
    public long getBookCountByGenre(Long genreId) {
        return 0;
    }
}
