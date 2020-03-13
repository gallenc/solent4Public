/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.rest;

/**
 *
 * @author gallenc
 */
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.flower.dto.ReplyMessage;
import org.solent.com504.project.model.flower.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * To make the ReST interface easier to program. All of the replies are contained in ReplyMessage classes but only the fields indicated are populated with each
 * reply. All replies will contain a code and a debug message. Possible replies are: List<String> replyMessage.getStringList() AnimalList
 * replyMessage.getAnimalList() int replyMessage.getCode() replyMessage.getDebugMessage();
 *
 * Note for details on swagger annotations go to https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations
 *
 * @author cgallen
 *
 */
@Component // component annotation allows resource to be picked up
@Path("/flowerService")
public class RestService {

    // SETS UP LOGGING 
    // note that log name will be org.solent.com504.project.impl.rest.RestService
    final static Logger LOG = LogManager.getLogger(RestService.class);

    // This serviceFacade object is injected by Spring
    @Autowired(required = true)
    @Qualifier("serviceFacade")
    ServiceFacade serviceFacade = null;

    /**
     * this is a very simple rest test message which only returns a string
     *
     * http://localhost:8084/flowers-web/rest/flowerService/
     *
     * @return String simple message
     */
    // swagger annotations
    @Operation(summary = "all this does is ask for a text 'hello world' response",
            description = "Returns text hello world",
            responses = {
                @ApiResponse(description = "hello world message",
                        content = @Content(mediaType = "text/plain"))
            })

    @GET
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    @Produces({MediaType.TEXT_PLAIN})
    public String message() {
        LOG.debug("flowerService called");
        return "Hello, rest!";
    }

    /**
     * get heartbeat
     *
     * http://localhost:8084/flowers-web/rest/flowerService/getHeartbeat
     *
     * @return Response OK and heartbeat in debug message
     */
    // Swagger annotations
    @Operation(summary = "This is simply a test method to check there is a heartbeat reply message",
            description = "Returns a list of flower descriptons corresponding to the symbol or synonym",
            responses = {
                @ApiResponse(description = "Heartbeat message in debug message of Reply",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/getHeartbeat")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getHeartbeat() {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/getHeartbeat called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            String heartbeat = serviceFacade.getHeartbeat();
            replyMessage.setDebugMessage(heartbeat);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /getHeartbeat ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /getHeartbea " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * public List<Flower> findBySymbolOrSynonymSymbol(String symbol);
     * http://localhost:8084/flowers-web/rest/flowerService/findBySymbolOrSynonymSymbol?symbol="ABAM5"
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Find a flower by its symbol or SynonymSymbol",
            description = "Returns a list of flower descriptons corresponding to the symbol or synonym",
            responses = {
                @ApiResponse(description = "List of Flowers in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/findBySymbolOrSynonymSymbol")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findBySymbolOrSynonymSymbol(
            @Parameter(description = "the symbol or synonymsymbol to use.", required = true)
            @QueryParam("symbol") String symbol) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findBySymbolOrSynonymSymbol called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findBySymbolOrSynonymSymbol(symbol);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findBySymbolOrSynonymSymbol ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findBySymbolOrSynonymSymbola " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);
     * http://localhost:8084/flowers-web/rest/flowerService/findLikeScientificNamewithAuthor?name="Verbesina pauciflora"
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Find a flower by scientific name or author",
            description = "Returns a list of flower descriptons corresponding to the match with scientific name or author. Note partial matches use 'contains' ",
            responses = {
                @ApiResponse(description = "List of Flowers in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/findLikeScientificNamewithAuthor")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikeScientificNamewithAuthor(
            @Parameter(description = "a partial match for ScientificName with Author", required = true)
            @QueryParam("name") String name) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findLikeScientificNamewithAuthor called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findLikeScientificNamewithAuthor(name);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findLikeScientificNamewithAuthor ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findLikeScientificNamewithAuthor " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);
     * http://localhost:8084/flowers-web/rest/flowerService/findLikeCommonName?name="ABAM5"
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Find a flower by its common name",
            description = "Returns a list of flower descriptons corresponding to the match with its common name. Note partial matches use 'contains' ",
            responses = {
                @ApiResponse(description = "List of Flowers in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/findLikeCommonName")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikeCommonName(
            @Parameter(description = "a partial match forthe common name of the plant", required = true)
            @QueryParam("name") String name) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findLikeCommonName called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findLikeCommonName(name);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findLikeCommonName ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findLikeCommonName " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);
     * http://localhost:8084/flowers-web/rest/flowerService/findLikefamily?name="Asteraceae"
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Find a flower by its family",
            description = "Returns a list of flower descriptons corresponding to the match with family. Note partial matches use 'contains' ",
            responses = {
                @ApiResponse(description = "List of Flowers in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/findLikefamily")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikefamily(
            @Parameter(description = "a partial match for the family name", required = true)
            @QueryParam("name") String name) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findLikefamily called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findLikefamily(name);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findLikefamily ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findLikefamily " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * public List<Flower> findLike(Flower flower); http://localhost:8084/flowers-web/rest/flowerService/findLike
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Find a flower by matching with a flower template",
            description = "Flower template fields are matched. Null or empty fields are ignored."
            + " Note partial matches use 'contains' ",
            responses = {
                @ApiResponse(description = "List of Flowers in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @POST
    @Path("/findLike")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLike(
            @Parameter(description = "a flower object used as a template match", required = true)
            @QueryParam("flower") Flower flower) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findLike called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findLike(flower);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findLike ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findLike" + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);
     * http://localhost:8084/flowers-web/rest/flowerService/findLikeCommonName?name="ABAM5"
     *
     * @return
     */
    // Swagger annotations
    @Operation(summary = "Returns a list of all flower families. Note NONE indicates flowers not matched to a family",
            description = "Returns a list of flower families ",
            responses = {
                @ApiResponse(description = "List of string names in reply message",
                        content = @Content(
                                schema = @Schema(implementation = ReplyMessage.class)
                        ))
                ,
            @ApiResponse(responseCode = "500", description = "Internal server error. See Debug message in response for details")
            })

    @GET
    @Path("/getAllFamilies")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllFamilies() {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/getAllFamilies called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<String> stringList = serviceFacade.getAllFamilies();
            replyMessage.setStringList(stringList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /fgetAllFamilies ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /getAllFamilies " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

}
