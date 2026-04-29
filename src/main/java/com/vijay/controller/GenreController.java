package com.vijay.controller;


import com.vijay.modal.Genre;
import com.vijay.payload.dto.GenreDTO;
import com.vijay.payload.response.ApiResponse;
import com.vijay.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody @Valid GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.createGenre(genreDTO);
        return ResponseEntity.ok(createdGenre);
    }

    @GetMapping()
    public ResponseEntity<?> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<?> getGenreById(@RequestParam("genreId") Long genreId) throws Exception {
        GenreDTO genres = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genres);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<?> updateGenre(@RequestParam("genreId") Long genreId,
                                         @RequestBody GenreDTO genre) throws Exception {
        GenreDTO genres = genreService.updateGenre(genreId,genre);
        return ResponseEntity.ok(genres);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<?> deleteGenre(@RequestParam("genreId") Long genreId) throws Exception {
        genreService.deleteGenre(genreId);
        ApiResponse apiResponse = new ApiResponse("genre deleted - soft delete", true);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{genreId}/hardDelete")
    public ResponseEntity<?> hardDeleteGenre(@RequestParam("genreId") Long genreId) throws Exception {
        genreService.hardDeleteGenre(genreId);
        ApiResponse apiResponse = new ApiResponse("genre deleted - Hard delete", true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/top-level")
    public ResponseEntity<?> getTopLevelGenres() {
        List<GenreDTO> genres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTotalActiveGenre() {
        Long genres = genreService.getTotalActiveGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}/book-count")
    public ResponseEntity<?> getBookCountByGenre(@PathVariable Long id) {

        Long count = genreService.getBookCountByGenre(id);
        return ResponseEntity.ok(count);
    }

}
