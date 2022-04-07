package com.tomlloyd.config;

import com.tomlloyd.dao.InMemoryParticipantsDao;
import com.tomlloyd.dao.ParticipantsDao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.LinkedHashMap;

@ApplicationScoped
public class AppConfig
{
    @Produces
    @ApplicationScoped
    public ParticipantsDao participantsDao()
    {
        return new InMemoryParticipantsDao(new LinkedHashMap<>());
    }
}
