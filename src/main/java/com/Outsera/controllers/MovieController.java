package com.Outsera.controllers;

import com.Outsera.dtos.MovieDto;
import com.Outsera.models.Movie;
import com.Outsera.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "Lista todos os filmes")
    @GetMapping
    public ResponseEntity<List<Movie>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }

    @Operation(description = "Busca filme por id")
    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable(name = "id")UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(id));
    }

    @Operation(description = "Cria um filme")
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.createMovie(movieDto));
    }
    @Operation(description = "Atualiza um filme")
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable(name = "id")UUID id,
                                             @RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.updateMovie(id,movieDto));
    }
    @Operation(description = "Deleta um filme")
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable(name = "id")UUID id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
