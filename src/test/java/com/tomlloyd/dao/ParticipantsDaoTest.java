package com.tomlloyd.dao;

import com.tomlloyd.dao.exception.ConstraintViolationException;
import com.tomlloyd.dao.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.tomlloyd.ParticipantTestUtils.createParticipant;
import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParticipantsDaoTest
{
    ParticipantsDao participantsDao;

    @Nested
    class FindTests
    {
        @Test
        void shouldThrowExceptionWhenNotFound()
        {
            // GIVEN
            participantsDao = new InMemoryParticipantsDao(emptyMap());

            // THEN
            assertThrows(ResourceNotFoundException.class, () -> participantsDao.find("URN"));
        }

        @Test
        void shouldReturnParticipantWhenFound()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("URN"));
            participantsDao = new InMemoryParticipantsDao(Map.of("URN", expected));

            // WHEN
            var actual = participantsDao.find("URN");

            // THEN
            assertEquals(expected, actual);
        }
    }

    @Nested
    class FindAllTests
    {
        @Test
        void shouldReturnEmptyListWhenNoParticipants()
        {
            // GIVEN
            participantsDao = new InMemoryParticipantsDao(emptyMap());

            // WHEN
            var participants = participantsDao.findAll();

            // THEN
            assertThat(participants, empty());
        }

        @Test
        void shouldReturnParticipantsWhenFound()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("URN"));
            participantsDao = new InMemoryParticipantsDao(Map.of("URN", expected));

            // WHEN
            var actual = participantsDao.findAll();

            // THEN
            assertThat(actual, contains(expected));
        }
    }

    @Nested
    class AddTests
    {
        @Test
        void shouldThrowExceptionWhenParticipantExists()
        {
            // GIVEN
            var existing = createParticipant(p -> p.urn("URN"));
            participantsDao = new InMemoryParticipantsDao(Map.of("URN", existing));

            // THEN
            assertThrows(ConstraintViolationException.class, () -> participantsDao.add(existing));
        }

        @Test
        void shouldReturnParticipantWhenAdded()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("URN"));
            participantsDao = new InMemoryParticipantsDao(new HashMap<>());

            // WHEN
            var actual = participantsDao.add(expected);

            // THEN
            assertEquals(expected, actual);
        }
    }

    @Nested
    class UpdateTests
    {
        @Test
        void shouldThrowExceptionWhenParticipantNotExists()
        {
            // GIVEN
            var newParticipant = createParticipant(p -> p.urn("URN"));
            participantsDao = new InMemoryParticipantsDao(emptyMap());

            // THEN
            assertThrows(ResourceNotFoundException.class, () -> participantsDao.update(newParticipant));
        }

        @Test
        void shouldReturnParticipantWhenUpdated()
        {
            // GIVEN
            var existing = createParticipant(p -> p.urn("URN").forename("Joe"));
            var expected = createParticipant(p -> p.urn("URN").forename("Joseph"));
            participantsDao = new InMemoryParticipantsDao(new HashMap<>(Map.of("URN", existing)));

            // WHEN
            var actual = participantsDao.update(expected);

            // THEN
            assertEquals(expected, actual);
        }
    }

    @Nested
    class RemoveTests
    {
        @Test
        void shouldThrowExceptionWhenParticipantNotExists()
        {
            // GIVEN
            participantsDao = new InMemoryParticipantsDao(emptyMap());

            // THEN
            assertThrows(ResourceNotFoundException.class, () -> participantsDao.remove("URN"));
        }

        @Test
        void shouldRemoveParticipantWhenParticipantExists()
        {
            // GIVEN
            var participant = createParticipant(p -> p.urn("URN"));
            var map = new HashMap<>(Map.of("URN", participant));
            participantsDao = new InMemoryParticipantsDao(map);

            // WHEN
            participantsDao.remove("URN");

            // THEN
            assertThat(map, anEmptyMap());
        }
    }
}