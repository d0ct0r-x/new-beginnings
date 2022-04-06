package com.tomlloyd.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Address
{
    @Singular
    List<String> addressLines;
    String city;
    String county;
    String country;
    String postcode;
}
