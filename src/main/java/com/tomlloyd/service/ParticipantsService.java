package com.tomlloyd.service;

import com.tomlloyd.dao.ParticipantsDao;
import com.tomlloyd.model.Participant;
import com.tomlloyd.service.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
@Log
public class ParticipantsService
{
    private ParticipantsDao participantsDao;
    private UrnGenerator urnGenerator;
    private Validator validator;

    public Participant findParticipant(String urn)
    {
        log.fine(() -> String.format("Finding participant with urn [%s]", urn));
        return participantsDao.find(urn);
    }

    public List<Participant> findAllParticipants()
    {
        log.fine(() -> "Finding all participants");
        return participantsDao.findAll();
    }

    public Participant addParticipant(Participant participant)
    {
        validateParticipant(participant);
        final var urn = urnGenerator.generate();
        final var now = LocalDateTime.now();
        log.fine(() -> String.format("Adding new participant with urn [%s]", urn));

        return participantsDao.add(participant.toBuilder()
                .urn(urn)
                .createdAt(now)
                .updatedAt(now)
                .build());
    }

    public Participant updateParticipant(String urn, Participant participant)
    {
        log.fine(() -> String.format("Updating participant with urn [%s]", urn));
        validateParticipant(participant);
        return participantsDao.update(participant.toBuilder()
                .urn(urn)
                .updatedAt(LocalDateTime.now())
                .build());
    }

    public void removeParticipant(String urn)
    {
        log.fine(() -> String.format("Removing participant with urn [%s]", urn));
        participantsDao.remove(urn);
    }

    private void validateParticipant(Participant participant)
    {
        validateRequiredFields(participant);
        validateContact(participant);
    }

    private void validateRequiredFields(Participant participant)
    {
        final var violations = validator.validate(participant);

        if (! violations.isEmpty())
        {
            final var errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            throw new ValidationException("Missing required fields", errors);
        }
    }

    private void validateContact(Participant participant)
    {
        final var hasPhone = participant.getMobilePhoneNumber() != null || participant.getHomePhoneNumber() != null;
        final var isMailable = participant.getContactPreference().isMail() && participant.getAddress() != null;
        final var isSmsable = participant.getContactPreference().isSms() && participant.getMobilePhoneNumber() != null;
        final var isPhoneable = participant.getContactPreference().isPhone() && hasPhone;

        if (!isMailable && !isSmsable && !isPhoneable)
        {
            throw new ValidationException("Missing required fields", List.of("Participant not contactable for trial"));
        }
    }
}
