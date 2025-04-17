package com.Outsera.config;

import com.Outsera.models.Movie;
import com.Outsera.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class CsvLoad implements CommandLineRunner {

    @Autowired
    MovieRepository movieRepository;


    @Override
    public void run(String... args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/movies.csv")))) {

            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Movie movie = new Movie();
                String[] partes = line.split(";");
                movie.setYear(Integer.parseInt(partes[0]));
                movie.setTitle(partes[1]);
                movie.setStudios(partes[2]);
                movie.setProducers(partes[3]);
                if (partes.length >= 5) {
                    movie.setWinner(partes[4]);
                } else {
                    movie.setWinner("no");
                }
                movieRepository.save(movie);
            }

            System.out.println("csv importado");
        }
    }
}
