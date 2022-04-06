package com.tomlloyd;

import com.tomlloyd.model.Participant;
import com.tomlloyd.service.ParticipantsService;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/participants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@AllArgsConstructor
public class ParticipantsResource
{
    private ParticipantsService participantsService;

    @GET
    @Path("/{urn}")
    public Participant findParticipant(@PathParam("urn") String urn)
    {
        return participantsService.findParticipant(urn);
    }

    @POST
    public Participant addParticipant(Participant participant)
    {
        return participantsService.addParticipant(participant);
    }

    @PUT
    @Path("/{urn}")
    public Participant updateParticipant(@PathParam("urn") String urn, Participant participant)
    {
        return participantsService.updateParticipant(urn, participant);
    }

    @DELETE
    @Path("/{urn}")
    public void removeParticipant(@PathParam("urn") String urn)
    {
        participantsService.removeParticipant(urn);
    }
}
