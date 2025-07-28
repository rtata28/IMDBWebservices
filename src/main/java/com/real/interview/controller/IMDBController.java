package com.real.interview.controller;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.service.IMDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class IMDBController {

    private static final Logger logger = LoggerFactory.getLogger(IMDBController.class);

    private final IMDBService imdbService;

    public IMDBController(IMDBService imdbService) {
        this.imdbService = imdbService;
    }

    // Creating a Movie
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest request) {
        logger.info("Creating new Movie with name: {}", request.getTitle());

        MovieResponse createdMovie = imdbService.createMovie(request);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    //  Reading/Get the Movie
    @GetMapping
    public ResponseEntity<MovieResponse> getMovie(
            @RequestParam(required = false) String title) {

        logger.info("Fetching movie by query params: title={}", title);

        if (title != null) {
            return ResponseEntity.ok(imdbService.getByTitle(title));
        } else {
            logger.warn("No query parameters provided for movie fetch");
            return ResponseEntity.badRequest().body(null); // Or throw a custom exception
        }
    }

    // Updating a Movie
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable UUID id,
                                                           @RequestBody MovieRequest request) {
        logger.info("Updating movie with ID: {}", id);
        MovieResponse updatedCustomer = imdbService.updateMovie(id, request);
        logger.info("Customer movie successfully: {}", updatedCustomer);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Deleting a Movie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        logger.info("Deleting movie with ID: {}", id);
        imdbService.deleteMovie(id);
        logger.info("Movie deleted successfully");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
