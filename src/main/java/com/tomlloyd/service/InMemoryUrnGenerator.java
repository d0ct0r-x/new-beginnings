package com.tomlloyd.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * In memory implementation of the urn generator to simulate the remote service.
 * Consonants are used to remove similar letter/number input mistakes (e.g. 1/L, 0/O) and prevent bad word occurrence.
 * This is limited for the purpose of the test, the real service could pick from a large enough set of numbers
 * such that input errors would not likely match another URN.
 * Formatting should also be considered e.g. using a separator between 3-4 characters for readability.
 */
@ApplicationScoped
public class InMemoryUrnGenerator implements UrnGenerator
{
    private static final String CONSONANTS = "QWRTYPSDFGHJKLZXCVBNM";
    private static final int TOTAL_LETTERS = 3;
    private static final int TOTAL_NUMBERS = 3;
    private static final Random RANDOM = new Random();
    private static int counter = 0;

    /**
     * Generate URN of format "RTY-123"
     * @return  unique reference number
     */
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
