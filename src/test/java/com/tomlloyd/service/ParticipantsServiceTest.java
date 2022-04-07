package com.tomlloyd.service;


import com.tomlloyd.dao.ParticipantsDao;
import com.tomlloyd.model.Participant;
import com.tomlloyd.service.exception.ValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static com.tomlloyd.ParticipantTestUtils.createContactPreference;
import static com.tomlloyd.ParticipantTestUtils.createParticipant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantsServiceTest
{
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    UrnGenerator urnGenerator;

    @Mock
    ParticipantsDao participantsDao;

    @InjectMocks
    ParticipantsService participantsService = new ParticipantsService(participantsDao, urnGenerator, validator);

    @Nested
    class FindParticipantTests
    {
        @Test
        void shouldReturnParticipantWhenFound()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("URN"));
            when(participantsDao.find("URN")).thenReturn(expected);

            // WHEN
            var actual = participantsService.findParticipant("URN");

            // THEN
            assertEquals(expected, actual);
            verify(participantsDao, times(1)).find("URN");
        }
    }

    @Nested
    class FindAllParticipantTests
    {
        @Test
        void shouldReturnParticipantsWhenFound()
        {
            // GIVEN
            var expected = List.of(createParticipant(p -> p.urn("URN")));
            when(participantsDao.findAll()).thenReturn(expected);

            // WHEN
            var actual = participantsService.findAllParticipants();

            // THEN
            assertEquals(expected, actual);
            verify(participantsDao, times(1)).findAll();
        }
    }

    @Nested
    class AddParticipantTests
    {
        @Test
        void shouldThrowErrorWhenMissingRequiredFields()
        {
            // GIVEN
            var participant = createParticipant(p -> p
                    .urn("URN")
                    .forename(null));

            // THEN
            assertThrows(ValidationException.class, () -> participantsService.addParticipant(participant));
        }

        @Test
        void shouldThrowErrorWhenNotContactable()
        {
            // GIVEN
            var participant = createParticipant(p -> p
                    .urn("URN")
                    .contactPreference(createContactPreference(c -> c
                            .sms(false)
                            .mail(false)
                            .phone(false))));

            // THEN
            assertThrows(ValidationException.class, () -> participantsService.addParticipant(participant));
        }

        @Test
        void shouldAddParticipantWhenValid()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("ABC"));
            when(urnGenerator.generate()).thenReturn("ABC");
            when(participantsDao.add(any(Participant.class))).then(answer -> answer.getArgument(0));

            // WHEN
            var actual = participantsService.addParticipant(expected);

            // THEN
            assertEquals(expected.getUrn(), actual.getUrn());
            verify(participantsDao, times(1)).add(actual);
        }
    }

    @Nested
    class UpdateParticipantTests
    {
        @Test
        void shouldThrowErrorWhenMissingRequiredFields()
        {
            // GIVEN
            var participant = createParticipant(p -> p
                    .urn("URN")
                    .forename(null));

            // THEN
            assertThrows(ValidationException.class, () -> participantsService.updateParticipant("URN", participant));
        }

        @Test
        void shouldThrowErrorWhenNotContactable()
        {
            // GIVEN
            var participant = createParticipant(p -> p
                    .urn("URN")
                    .contactPreference(createContactPreference(c -> c
                            .sms(false)
                            .mail(false)
                            .phone(false))));

            // THEN
            assertThrows(ValidationException.class, () -> participantsService.updateParticipant("URN", participant));
        }

        @Test
        void shouldUpdateParticipantWhenValid()
        {
            // GIVEN
            var expected = createParticipant(p -> p.urn("URN"));
            when(participantsDao.update(any(Participant.class))).then(answer -> answer.getArgument(0));

            // WHEN
            var actual = participantsService.updateParticipant("URN", expected);

            // THEN
            assertEquals(expected.getUrn(), actual.getUrn());
            verify(participantsDao, times(1)).update(actual);
        }
    }

    @Nested
    class RemoveParticipantTests
    {
        @Test
        void shouldRemoveParticipantWhenFound()
        {
            // GIVEN
            doNothing().when(participantsDao).remove(anyString());

            // WHEN
            participantsService.removeParticipant("URN");

            // THEN
            verify(participantsDao, times(1)).remove("URN");
        }
    }
}