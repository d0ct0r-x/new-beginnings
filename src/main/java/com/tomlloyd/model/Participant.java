package com.tomlloyd.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Participant
{
    String urn;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String title;
    String forename;
    String surname;
    LocalDate dateOfBirth;
    String homePhoneNumber;
    String mobilePhoneNumber;
    Address address;
}
