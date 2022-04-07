package com.tomlloyd.dao;

import com.tomlloyd.dao.exception.ConstraintViolationException;
import com.tomlloyd.dao.exception.ResourceNotFoundException;
import com.tomlloyd.model.Participant;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * In memory implementation of the participants data store.
 * The interface allows to swap for an RDS implementation for example.
 * Checks for existence of participants and guards against duplicates.
 */
@AllArgsConstructor
@Log
public class InMemoryParticipantsDao implements ParticipantsDao
{
    private Map<String, Participant> participantsMap;

    @Override
    public Participant find(String urn)
    {
        throwIfNotExists(urn);
        return participantsMap.get(urn);
    }

    @Override
    public List<Participant> findAll()
    {
        return new ArrayList<>(participantsMap.values());
    }

    @Override
    public Participant add(Participant participant)
    {
        final var urn = participant.getUrn();

        if (participantsMap.containsKey(urn))
        {
            log.warning(() -> String.format("Duplicate participant found with urn [%s]", urn));
            throw new ConstraintViolationException("Duplicate participant found");
        }

        participantsMap.put(urn, participant);
        return participant;
    }

    @Override
    public Participant update(Participant participant)
    {
        final var urn = participant.getUrn();
        throwIfNotExists(urn);

        participantsMap.put(urn, participant);
        return participant;
    }

    @Override
    public void remove(String urn)
    {
        throwIfNotExists(urn);
        participantsMap.remove(urn);
    }

    private void throwIfNotExists(String urn)
    {
        if (! participantsMap.containsKey(urn))
        {
            log.warning(() -> String.format("No participant found with urn [%s]", urn));
            throw new ResourceNotFoundException("No participant found");
        }
    }
}
