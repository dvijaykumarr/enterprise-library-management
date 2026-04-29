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

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Long genreId) throws Exception { // CHANGED: @PathVariable("genreId") → @PathVariable
        GenreDTO genre = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Long genreId, // CHANGED: @PathVariable("genreId") → @PathVariable
                                                @RequestBody @Valid GenreDTO genreDTO) throws Exception {
        GenreDTO updatedGenre = genreService.updateGenre(genreId, genreDTO);
        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<ApiResponse> deleteGenre(@PathVariable Long genreId) throws Exception { // CHANGED: @PathVariable("genreId") → @PathVariable
        genreService.deleteGenre(genreId);
        return ResponseEntity.ok(new ApiResponse("Genre soft deleted successfully", true));
    }

    @DeleteMapping("/{genreId}/hard-delete")
    public ResponseEntity<ApiResponse> hardDeleteGenre(@PathVariable Long genreId) throws Exception { // CHANGED: @PathVariable("genreId") → @PathVariable
        genreService.hardDeleteGenre(genreId);
        return ResponseEntity.ok(new ApiResponse("Genre permanently deleted", true));
    }

    @GetMapping("/top-level")
    public ResponseEntity<List<GenreDTO>> getTopLevelGenres() {
        List<GenreDTO> genres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/active")
    public ResponseEntity<List<GenreDTO>> getAllActiveGenresWithSubGenres() {
        List<GenreDTO> genres = genreService.getAllActiveGenresWithSubGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalActiveGenres() {
        long count = genreService.getTotalActiveGenres();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/book-count")
    public ResponseEntity<Long> getBookCountByGenre(@PathVariable Long id) {
        long count = genreService.getBookCountByGenre(id);
        return ResponseEntity.ok(count);
    }
}
