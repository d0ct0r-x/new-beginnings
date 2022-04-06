package com.tomlloyd.dao;

import com.tomlloyd.model.Participant;

public interface ParticipantsDao
{
    Participant find(String urn);

    Participant add(Participant participant);

    Participant update(Participant participant);

    void remove(String urn);
}
