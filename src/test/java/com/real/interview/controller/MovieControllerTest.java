package com.real.interview.controller;

import com.real.interview.dto.MovieRequest;
import com.real.interview.dto.MovieResponse;
import com.real.interview.service.IMDBService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class MovieControllerTest {

    @Mock
    private IMDBService movieService;

    @InjectMocks
    private IMDBController controller;

    private MovieRequest request;
    private MovieResponse response;
    private UUID id;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        id = UUID.randomUUID();
        request = new MovieRequest("Moana", "2025");
        response = new MovieResponse(id, "Moana", "2025");
    }



    @Test
    void testGetMovieByNameOnly() {
        when(movieService.getByTitle("Moana")).thenReturn(response);
        ResponseEntity<MovieResponse> result = controller.getMovie("Moana");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetMovieWithNoParams() {
        ResponseEntity<MovieResponse> result = controller.getMovie(null);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testUpdateMovie() {
        when(movieService.updateMovie(id, request)).thenReturn(response);
        ResponseEntity<MovieResponse> result = controller.updateMovie(id, request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Moana", result.getBody().getTitle());
    }

    @Test
    void testDeleteMovie() {
        doNothing().when(movieService).deleteMovie(id);
        ResponseEntity<Void> result = controller.deleteMovie(id);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void testHandleIllegalArgumentException() {
        ResponseEntity<String> result = controller.handleIllegalArgumentException(new IllegalArgumentException("Invalid input"));
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid input", result.getBody());
    }
}