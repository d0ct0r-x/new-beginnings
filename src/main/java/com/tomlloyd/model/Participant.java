package com.tomlloyd.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Participant data model
 */
@Value
@Builder(toBuilder = true)
public class Participant
{
    /**
     * Unique reference number (e.g. ABC-123)
     */
    String urn;

    /**
     * The timestamp for when the participant registered
     */
    LocalDateTime createdAt;

    /**
     * The timestamp for when the participant's data was last updated
     */
    LocalDateTime updatedAt;

    /**
     * Title (e.g. Mr, Mrs)
     */
    @NotBlank(message="Title may not be blank")
    String title;

    /**
     * First name
     */
    @NotBlank(message="Forename may not be blank")
    String forename;

    /**
     * Last name
     */
    @NotBlank(message="Surname may not be blank")
    String surname;

    /**
     * Date of birth
     */
    @NotNull(message="Date of birth is required")
    @Past
    LocalDate dateOfBirth;

    /**
     * Home telephone number
     */
    String homePhoneNumber;

    /**
     * Mobile number
     */
    String mobilePhoneNumber;

    /**
     * Mailing address
     */
    @Valid
    Address address;

    /**
     * Contact preference for clinical trial
     */
    @NotNull(message="Contact preference is required")
    ContactPreference contactPreference;
}
