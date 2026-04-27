package com.vijay.mapper;

import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;

import java.util.stream.Collectors;

public class GenreMapper {

    public static GenreDTO toDto(Genre savedGenre){
        if(savedGenre == null){
            return null;
        }

        GenreDTO dto = GenreDTO.builder()
                .id(savedGenre.getId())
                .code(savedGenre.getCode())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .active(savedGenre.getActive())
                .createdAt(savedGenre.getCreatedAt())
                .updatedAt(savedGenre.getUpdatedAt())
                .build();
        if(savedGenre.getParentGenre() != null){
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }

        dto.setSubGenre(savedGenre.getSubGenres().stream()
                .filter(subGenre -> subGenre.getActive())
                .map(subGenre -> toDto(subGenre)).collect(Collectors.toList()));


        return dto;

    }
}
