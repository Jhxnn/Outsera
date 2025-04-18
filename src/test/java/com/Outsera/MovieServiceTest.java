package com.Outsera;

import com.Outsera.dtos.IntervalItemDto;
import com.Outsera.dtos.IntervalResponseDto;
import com.Outsera.models.Movie;
import com.Outsera.repositories.MovieRepository;
import com.Outsera.services.MovieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    void setup() {
        movieRepository.deleteAll();

        movieRepository.save(new Movie(2000, "Filme A", "Estudio", "Produtor 1", "yes"));
        movieRepository.save(new Movie(2002, "Filme B", "Estudio", "Produtor 1", "yes"));
        movieRepository.save(new Movie(2010, "Filme C", "Estudio", "Produtor 2", "yes"));
        movieRepository.save(new Movie(2020, "Filme D", "Estudio", "Produtor 2", "yes"));
        movieRepository.save(new Movie(2022, "Filme E", "Estudio", "Produtor 2", "yes"));
        movieRepository.save(new Movie(2021, "Filme F", "Estudio", "Produtor 3", "no"));
    }

    @Test
    void testFindIntervals() {
        IntervalResponseDto response = movieService.findIntervals();

        List<IntervalItemDto> min = response.min();
        List<IntervalItemDto> max = response.max();

        assertNotNull(min);
        assertNotNull(max);

        // Produtor 2: 2010 -> 2020 = 10, 2020 -> 2022 = 2 => menor intervalo: 2
        boolean hasMin = min.stream()
                .anyMatch(i -> i.producer().equals("Produtor 2") && i.interval() == 2 && i.previousWin() == 2020 && i.followingWin() == 2022);
        assertTrue(hasMin);

        // Produtor 2: maior intervalo = 10 (2010 -> 2020)
        boolean hasMax = max.stream()
                .anyMatch(i -> i.producer().equals("Produtor 2") && i.interval() == 10 && i.previousWin() == 2010 && i.followingWin() == 2020);
        assertTrue(hasMax);
    }
}
