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
import java.util.List;

/**
 * Resource to manage participant registration
 */
@Path("/participants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@AllArgsConstructor
public class ParticipantsResource
{
    private ParticipantsService participantsService;

    /**
     * Find a participant by urn
     * @param urn  the unique reference number
     * @return  the participant details
     */
    @GET
    @Path("/{urn}")
    public Participant findParticipant(@PathParam("urn") String urn)
    {
        return participantsService.findParticipant(urn);
    }

    /**
     * Find all registered participants
     * @return  the list of particpants
     */
    @GET
    public List<Participant> findAllParticipants()
    {
        return participantsService.findAllParticipants();
    }

    /**
     * Add a new participant to the trial
     * @param participant  the participant details
     * @return  the participant details with assigned urn
     */
    @POST
    public Participant addParticipant(Participant participant)
    {
        return participantsService.addParticipant(participant);
    }

    /**
     * Update a participant's details
     * @param urn  the unique reference number
     * @param participant  the participant details
     * @return  the updated participant details
     */
    @PUT
    @Path("/{urn}")
    public Participant updateParticipant(@PathParam("urn") String urn, Participant participant)
    {
        return participantsService.updateParticipant(urn, participant);
    }

    /**
     * Removes a participant from the trial
     * @param urn  the unique reference number
     */
    @DELETE
    @Path("/{urn}")
    public void removeParticipant(@PathParam("urn") String urn)
    {
        participantsService.removeParticipant(urn);
    }
}
