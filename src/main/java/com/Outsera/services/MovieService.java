package com.Outsera.services;

import com.Outsera.dtos.IntervalItemDto;
import com.Outsera.dtos.IntervalResponseDto;
import com.Outsera.dtos.MovieDto;
import com.Outsera.models.Movie;
import com.Outsera.repositories.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

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
    public IntervalResponseDto findIntervals() {
        List<Movie> movies = findAll().stream()
                .filter(m -> "yes".equalsIgnoreCase(m.getWinner()))
                .collect(Collectors.toList());

        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : movies) {
            String[] producers = movie.getProducers().split(",| and "); // trata múltiplos produtores
            for (String producer : producers) {
                producer = producer.trim();
                producerWins
                        .computeIfAbsent(producer, k -> new ArrayList<>())
                        .add(movie.getMovieYear());
            }
        }

        List<IntervalItemDto> intervalItems = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            if (years.size() < 2) continue;

            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);
                intervalItems.add(new IntervalItemDto(producer, interval, years.get(i - 1), years.get(i)));
            }
        }

        if (intervalItems.isEmpty()) {
            return new IntervalResponseDto(Collections.emptyList(), Collections.emptyList());
        }

        int minInterval = intervalItems.stream().mapToInt(IntervalItemDto::interval).min().orElse(0);
        int maxInterval = intervalItems.stream().mapToInt(IntervalItemDto::interval).max().orElse(0);

        List<IntervalItemDto> minList = intervalItems.stream()
                .filter(i -> i.interval() == minInterval)
                .collect(Collectors.toList());

        List<IntervalItemDto> maxList = intervalItems.stream()
                .filter(i -> i.interval() == maxInterval)
                .collect(Collectors.toList());

        return new IntervalResponseDto(minList, maxList);
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
