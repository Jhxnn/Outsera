package com.Outsera.controllers;

import com.Outsera.dtos.MovieDto;
import com.Outsera.models.Movie;
import com.Outsera.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable(name = "id")UUID id,
                                             @RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.updateMovie(id,movieDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable(name = "id")UUID id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
