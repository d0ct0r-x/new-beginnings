package com.tomlloyd;

import com.tomlloyd.dao.exception.ConstraintViolationException;
import com.tomlloyd.dao.exception.ResourceNotFoundException;
import com.tomlloyd.model.Participant;
import com.tomlloyd.service.ParticipantsService;
import com.tomlloyd.service.exception.ValidationException;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collections;

import static com.tomlloyd.ParticipantTestUtils.createParticipant;
import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(ParticipantsResource.class)
class ParticipantsResourceTest
{
    @InjectMock
    ParticipantsService participantsService;

    @Nested
    class FindParticipantTests
    {
        @Test
        void shouldReturn200WhenValid() {

            final var expected = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.findParticipant("URN")).thenReturn(expected);

            given()
                .when()
                    .get("/participants/URN")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body("urn", is(expected.getUrn()));
        }

        @Test
        void shouldReturn404WhenNotFound() {

            Mockito.when(participantsService.findParticipant("URN")).thenThrow(ResourceNotFoundException.class);

            given()
                .when()
                    .get("/participants/URN")
                .then()
                    .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @Nested
    class FindAllParticipantsTests
    {
        @Test
        void shouldReturn200WhenValid() {

            Mockito.when(participantsService.findAllParticipants()).thenReturn(emptyList());

            given()
                .when()
                    .get("/participants")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode());
        }
    }

    @Nested
    class AddParticipantTests
    {
        @Test
        void shouldReturn200WhenValid() {

            final var expected = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.addParticipant(expected)).thenReturn(expected);

            given()
                .when()
                    .body(expected)
                    .contentType(MediaType.APPLICATION_JSON)
                    .post("/participants")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body("urn", is(expected.getUrn()));
        }

        @Test
        void shouldReturn400WhenInvalid() {

            final var participant = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.addParticipant(participant)).thenThrow(ValidationException.class);

            given()
                .when()
                    .body(participant)
                    .contentType(MediaType.APPLICATION_JSON)
                    .post("/participants")
                .then()
                    .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        void shouldReturn403WhenDuplicateExists() {

            final var participant = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.addParticipant(participant)).thenThrow(ConstraintViolationException.class);

            given()
                .when()
                    .body(participant)
                    .contentType(MediaType.APPLICATION_JSON)
                    .post("/participants")
                .then()
                    .statusCode(Response.Status.FORBIDDEN.getStatusCode());
        }
    }

    @Nested
    class UpdateParticipantTests
    {
        @Test
        void shouldReturn200WhenValid() {

            final var expected = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.updateParticipant("URN", expected)).thenReturn(expected);

            given()
                .when()
                    .body(expected)
                    .contentType(MediaType.APPLICATION_JSON)
                    .put("/participants/URN")
                .then()
                    .statusCode(Response.Status.OK.getStatusCode())
                    .body("urn", is(expected.getUrn()));
        }

        @Test
        void shouldReturn400WhenInvalid() {

            final var participant = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.updateParticipant("URN", participant)).thenThrow(ValidationException.class);

            given()
                .when()
                    .body(participant)
                    .contentType(MediaType.APPLICATION_JSON)
                    .put("/participants/URN")
                .then()
                    .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
        }

        @Test
        void shouldReturn404WhenNotFound() {

            final var participant = createParticipant(p -> p.urn("URN"));
            Mockito.when(participantsService.updateParticipant("URN", participant)).thenThrow(ResourceNotFoundException.class);

            given()
                .when()
                    .body(participant)
                    .contentType(MediaType.APPLICATION_JSON)
                    .put("/participants/URN")
                .then()
                    .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        }
    }

    @Nested
    class RemoveParticipantTests
    {
        @Test
        void shouldReturn204WhenValid() {

            Mockito.doNothing().when(participantsService).removeParticipant("URN");

            given()
                .when()
                    .delete("/participants/URN")
                .then()
                    .statusCode(Response.Status.NO_CONTENT.getStatusCode());
        }

        @Test
        void shouldReturn404WhenNotFound() {

            Mockito.doThrow(ResourceNotFoundException.class).when(participantsService).removeParticipant("URN");

            given()
                .when()
                    .delete("/participants/URN")
                .then()
                    .statusCode(Response.Status.NOT_FOUND.getStatusCode());
        }
    }
}