package com.vijay.mapper;

import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;

import java.util.stream.Collectors;

public class GenreMapper {

    public static GenreDTO toDto(Genre genre) {
        if (genre == null) return null;

        GenreDTO dto = GenreDTO.builder()
                .id(genre.getId())
                .code(genre.getCode())
                .name(genre.getName())
                .description(genre.getDescription())
                .displayOrder(genre.getDisplayOrder())
                .active(genre.getActive())
                .createdAt(genre.getCreatedAt())
                .updatedAt(genre.getUpdatedAt())
                .build();

        if (genre.getParentGenre() != null) {
            dto.setParentGenreId(genre.getParentGenre().getId());
            dto.setParentGenreName(genre.getParentGenre().getName());
        }

        if (genre.getSubGenres() != null && !genre.getSubGenres().isEmpty()) {
            dto.setSubGenre(genre.getSubGenres().stream()
                    .filter(sub -> Boolean.TRUE.equals(sub.getActive()))
                    .map(GenreMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Genre toEntity(GenreDTO dto) {
        if (dto == null) return null;

        return Genre.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .displayOrder(dto.getDisplayOrder())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
        // Note: parentGenre is handled in the service layer
    }
}