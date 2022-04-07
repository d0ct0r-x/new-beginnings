package com.tomlloyd;

import com.tomlloyd.model.Address;
import com.tomlloyd.model.ContactPreference;
import com.tomlloyd.model.Participant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ParticipantTestUtils
{
    public static Participant createParticipant(Consumer<Participant.ParticipantBuilder> participantBuilderConsumer)
    {
        final var now = LocalDateTime.now();

        final var builder = Participant.builder()
                .urn("URN")
                .createdAt(now.minusDays(1))
                .updatedAt(now)
                .title("Mr")
                .forename("Joe")
                .surname("Bloggs")
                .dateOfBirth(LocalDate.parse("1960-01-01"))
                .homePhoneNumber("01234 567890")
                .mobilePhoneNumber("07123456789")
                .address(buildAddress())
                .contactPreference(createContactPreference(c -> {}));

        participantBuilderConsumer.accept(builder);

        return builder.build();
    }

    public static ContactPreference createContactPreference(Consumer<ContactPreference.ContactPreferenceBuilder> contactPreferenceBuilderConsumer)
    {
        final var builder = ContactPreference.builder()
                .sms(true)
                .mail(true)
                .phone(true);

        contactPreferenceBuilderConsumer.accept(builder);

        return builder.build();
    }

    private static Address buildAddress()
    {
        return Address.builder()
                .addressLine("123 Test Road")
                .city("Plymouth")
                .county("Devon")
                .country("UK")
                .postcode("AA99 9AA")
                .build();
    }
}
