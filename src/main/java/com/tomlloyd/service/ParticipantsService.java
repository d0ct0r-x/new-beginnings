package com.tomlloyd.service;

import com.tomlloyd.dao.ParticipantsDao;
import com.tomlloyd.model.Participant;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

@ApplicationScoped
@AllArgsConstructor
public class ParticipantsService
{
    private ParticipantsDao participantsDao;
    private UrnGenerator urnGenerator;

    public Participant findParticipant(String urn)
    {
        return participantsDao.find(urn);
    }

    public Participant addParticipant(Participant participant)
    {
        final var now = LocalDateTime.now();

        return participantsDao.add(participant.toBuilder()
                .urn(urnGenerator.generate())
                .createdAt(now)
                .updatedAt(now)
                .build());
    }

    public Participant updateParticipant(String urn, Participant participant)
    {
        return participantsDao.update(participant.toBuilder()
                .urn(urn)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    public void removeParticipant(String urn)
    {
        participantsDao.remove(urn);
    }
}
