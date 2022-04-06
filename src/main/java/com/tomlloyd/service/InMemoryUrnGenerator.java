package com.tomlloyd.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemoryUrnGenerator implements UrnGenerator
{
    private static final String CONSONANTS = "QWRTYPSDFGHJKLZXCVBNM";
    private static final int TOTAL_LETTERS = 3;
    private static final int TOTAL_NUMBERS = 3;
    private static final Random RANDOM = new Random();
    private static int counter = 0;

    @Override
    public String generate()
    {
        return generateLetters() + "-" + generateNumbers();
    }

    private String generateLetters()
    {
        return RANDOM.ints(TOTAL_LETTERS, 0, CONSONANTS.length())
                .mapToObj(CONSONANTS::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private static String generateNumbers()
    {
        return String.format("%0" + TOTAL_NUMBERS + "d", ++counter);
    }
}
