package com.tomlloyd.model;

import lombok.Builder;
import lombok.Value;

/**
 * Contact preference for the trial
 */
@Value
@Builder
public class ContactPreference
{
    /**
     * If true, the participant allows communication by SMS to their mobile number
     */
    boolean sms;

    /**
     * If true, the participant allows communication by mail to their address
     */
    boolean mail;

    /**
     * If true, the participant allows communication by phone to their home/mobile number
     */
    boolean phone;
}
