package com.Outsera;
import com.Outsera.dtos.IntervalResponseDto;
import com.Outsera.services.MovieService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieServiceTest {

    @Autowired
    MovieService movieService;

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setup() {
    }

    @Test
    void testFindIntervalsMatchesExpectedJsonFile() throws IOException {
        IntervalResponseDto response = movieService.findIntervals();

        File expectedFile = ResourceUtils.getFile("classpath:expected_intervals.json");
        String expectedJson = new String(java.nio.file.Files.readAllBytes(expectedFile.toPath()));

        String actualJson = mapper.writeValueAsString(response);

        JsonNode expectedNode = mapper.readTree(expectedJson);
        JsonNode actualNode = mapper.readTree(actualJson);
        assertEquals(expectedNode, actualNode, "A resposta da API não está igual ao JSON esperado");
    }
}
