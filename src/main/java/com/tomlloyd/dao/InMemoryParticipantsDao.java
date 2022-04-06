package com.tomlloyd.dao;

import com.tomlloyd.dao.exception.ConstraintViolationException;
import com.tomlloyd.dao.exception.ResourceNotFoundException;
import com.tomlloyd.model.Participant;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;

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
    public Participant add(Participant participant)
    {
        final var urn = participant.getUrn();

        if (participantsMap.containsKey(urn))
        {
            log.severe(() -> String.format("Duplicate participant found with urn [%s]", urn));
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
            log.severe(() -> String.format("No participant found with urn [%s]", urn));
            throw new ResourceNotFoundException("No participant found");
        }
    }
}
