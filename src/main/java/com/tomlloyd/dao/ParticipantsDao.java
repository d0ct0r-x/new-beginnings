package com.tomlloyd.dao;

import com.tomlloyd.model.Participant;

import java.util.List;

/**
 * Data access object operating on the participants data store
 */
public interface ParticipantsDao
{
    Participant find(String urn);

    List<Participant> findAll();

    Participant add(Participant participant);

    Participant update(Participant participant);

    void remove(String urn);
}
