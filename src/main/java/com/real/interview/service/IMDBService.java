package com.real.interview.service;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.entity.Movie;
import com.real.interview.exception.NotFoundException;
import com.real.interview.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IMDBService {
    private static final Logger logger = LoggerFactory.getLogger(IMDBService.class);

    @Autowired
    private MovieRepository repository;

    public IMDBService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieResponse createMovie(MovieRequest request) {
        validateRequest(request);

        Movie movie = new Movie(
                null,
                request.getTitle(),
                request.getYear()
        );

        Movie saved = repository.save(movie);
        logger.info("Saved movie with ID: {}", saved.getId());

        return mapToResponse(saved);
    }

//    public MovieResponse getMovieById(UUID id) {
//        if (id == null) {
//            throw new IllegalArgumentException("Movie ID must not be null");
//        }
//        logger.debug("Fetching movie by ID: {}", id);
//        return repository.findById(id)
//                .map(this::mapToResponse)
//                .orElseThrow(() -> new NotFoundException("Movie not found"));
//    }

    public MovieResponse getByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be blank");
        }
        logger.debug("Fetching movie by title: {}", title);
        return repository.findByTitle(title)
                .map(this::mapToResponse)
                .orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    public MovieResponse updateMovie(UUID id, MovieRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Movie ID must not be null");
        }
        logger.debug("Updating movie ID: {}", id);
        validateRequest(request);

        Movie movie = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movie not found"));

        movie.setTitle(request.getTitle());
        movie.setYear(request.getYear());
        MovieResponse updatedMovie = mapToResponse(repository.save(movie));
        logger.info("Movie updated with ID: {}", updatedMovie.getId());

        return updatedMovie;
    }

    public void deleteMovie(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Movie ID must not be null");
        }
        logger.debug("Deleting movie ID: {}", id);
        repository.deleteById(id);
    }

    private MovieResponse mapToResponse(Movie c) {
        return new MovieResponse(
                c.getId(), c.getTitle(), c.getYear()
        );
    }

    private void validateRequest(MovieRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        if (request.getYear() == null || request.getYear().isBlank()) {
            throw new IllegalArgumentException("Year must not be null or blank");
        }
    }
}
