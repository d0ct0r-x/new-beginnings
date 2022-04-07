package com.tomlloyd.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Mailing address details
 */
@Value
@Builder
public class Address
{
    /**
     * Street address lines
     */
    @Singular
    @NotEmpty
    @Size(min=1, max=5)
    List<@NotBlank String> addressLines;

    /**
     * City
     */
    String city;

    /**
     * County
     */
    String county;

    /**
     * Country
     */
    String country;

    /**
     * Postal code
     */
    @NotBlank
    String postcode;
}
