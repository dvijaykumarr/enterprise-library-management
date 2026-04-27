package com.vijay.controller;


import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;
import com.vijay.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody @Valid GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.createGenre(genreDTO);
        return ResponseEntity.ok(createdGenre);
    }
}
