package com.real.interview.service;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.entity.Movie;
import com.real.interview.exception.NotFoundException;
import com.real.interview.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

        private static final Logger logger = LoggerFactory.getLogger(MovieServiceTest.class);

        @Mock
        private MovieRepository repository;

        @InjectMocks
        private IMDBService service;

        final private UUID uuid = UUID.randomUUID();
        private Movie movie;
        private MovieRequest validRequest;


        @BeforeEach
        public void setUp() {
            logger.info("Setting up mocks and test data...");
            MockitoAnnotations.openMocks(this);
            validRequest = new MovieRequest("Moana", "2024");
            movie = new Movie(uuid, "Moana", "2024");
        }

        /**
         * Test successful creation of a movie.
         */
        @Test
        public void testCreateMovie() {
            logger.info("Running testCreateMovie...");
            when(repository.save(any(Movie.class))).thenReturn(movie);

            MovieResponse response = service.createMovie(validRequest);

            assertNotNull(response);
            assertEquals("Moana", response.getTitle());
            assertEquals("2024", response.getYear());
        }

        @Test
        public void testCreateTitleByRequestNull() {
            logger.info("Running testCreateMovieByRequestNull...");
            assertThrows(IllegalArgumentException.class,
                    () -> service.createMovie(null));
        }

        /**
         * Test updating a movie.
         */
        @Test
        public void testUpdateMovie() {
            logger.info("Running testUpdateMovie...");
            when(repository.findById(any(UUID.class))).thenReturn(Optional.of(movie));
            when(repository.save(any(Movie.class))).thenReturn(movie);

            MovieResponse response = service.updateMovie(movie.getId(), validRequest);

            assertEquals("Moana", response.getTitle());
            logger.info("Movie updated successfully");
        }

        /**
         * Test deletion of a movie.
         */
        @Test
        public void testDeleteMovie() {
            logger.info("Running testDeleteMovie...");
            UUID id = movie.getId();
            doNothing().when(repository).deleteById(id);

            assertDoesNotThrow(() -> service.deleteMovie(id));
            verify(repository, times(1)).deleteById(id);
            logger.info("Movie deleted successfully");
        }

        @Test
        void testGetMovieByNameNull() {
            assertThrows(IllegalArgumentException.class, () -> service.getByTitle(null));
        }

        @Test
        void testDeleteMovieByIdNull() {
            assertThrows(IllegalArgumentException.class, () -> service.deleteMovie(null));
        }

        @Test
        void testGetByNameFound() {
            when(repository.findByTitle("Moana")).thenReturn(Optional.of(movie));
            MovieResponse response = service.getByTitle("Moana");
            assertEquals("Moana", response.getTitle());
        }

        @Test
        void testGetByNameNotFound() {
            when(repository.findByTitle("Test User")).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> service.getByTitle("Test User"));
        }

        @Test
        void testUpdateMovieSuccess() {
            when(repository.findById(uuid)).thenReturn(Optional.of(movie));
            when(repository.save(any())).thenReturn(movie);
            MovieResponse response = service.updateMovie(uuid, validRequest);
            assertEquals("Moana", response.getTitle());
        }

        @Test
        void testUpdateMovieNotFound() {
            when(repository.findById(uuid)).thenReturn(Optional.empty());
            assertThrows(NotFoundException.class, () -> service.updateMovie(uuid, validRequest));
        }

        @Test
        void testUpdateMovieNull() {
            assertThrows(IllegalArgumentException.class, () -> service.updateMovie(null, validRequest));
        }

        @Test
        void testValidateRequestNullName() {
            MovieRequest badRequest = new MovieRequest(null, "2025");
            assertThrows(IllegalArgumentException.class, () -> service.createMovie(badRequest));
        }
    }
