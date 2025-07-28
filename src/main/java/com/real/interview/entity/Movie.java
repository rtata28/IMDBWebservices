package com.real.interview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    @jakarta.persistence.Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(name = "release_year")
    private String year; // Can be made into Integer/Long
}