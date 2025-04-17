package com.Outsera.services;

import com.Outsera.dtos.MovieDto;
import com.Outsera.models.Movie;
import com.Outsera.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }
    public Movie findById(UUID id){
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Nao foi possivel achar"));
    }
    public Movie createMovie(MovieDto movieDto){
        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDto,movie);
        return movieRepository.save(movie);
    }
    public Movie updateMovie(UUID id, MovieDto movieDto){
        Movie movie = findById(id);
        if(movieDto.year() != null){
            movie.setMovieYear(movieDto.year());
        }
        if(movieDto.title() != null){
            movie.setTitle(movieDto.title());
        }
        if(movieDto.studios() != null){
            movie.setStudios(movieDto.studios());
        }
        if(movieDto.producers() != null){
            movie.setProducers(movieDto.producers());
        }
        if(movieDto.winner() != null){
            movie.setWinner(movieDto.winner());
        }
        return movieRepository.save(movie);
    }

    public void deleteMovie(UUID id){
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }
}
