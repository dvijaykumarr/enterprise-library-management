package com.vijay.controller;


import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;
import com.vijay.service.GenreService;
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
    public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre){
//        Genre createdGenre = genreService.createGenre(genre);
//
//        return ResponseEntity.ok(createdGenre);
        return null;
    }
}
