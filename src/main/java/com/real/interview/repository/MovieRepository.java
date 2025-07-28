package com.real.interview.repository;

import com.real.interview.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findByTitle(String title);

}

