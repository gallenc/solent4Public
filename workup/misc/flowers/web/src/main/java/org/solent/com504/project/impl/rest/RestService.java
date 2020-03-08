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
 * replyMessage.getAnimalList() int replyMessage.getCode() replyMessage.getDebugMessage(); * @author cgallen
 */
@Component // component allows resource to be picked up
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
     * http://localhost:8084/projectweb/rest/flowerService/
     *
     * @return String simple message
     */
    @GET
    public String message() {
        LOG.debug("flowerService called");
        return "Hello, rest!";
    }

    /**
     * get heartbeat
     *
     * http://localhost:8084/projectweb/rest/flowerService/getHeartbeat
     *
     * @return Response OK and heartbeat in debug message
     */
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
     * public List<Flower> findBySymboOrSynonymSymbol(String symbol);
     * http://localhost:8084/projectweb/rest/flowerService/findBySymboOrSynonymSymbol?symbol="ABAM5"
     *
     * @return
     */
    @GET
    @Path("/findBySymboOrSynonymSymbol")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findBySymboOrSynonymSymbol(@QueryParam("symbol") String symbol) {
        try {

            ReplyMessage replyMessage = new ReplyMessage();
            LOG.debug("/findBySymboOrSynonymSymbol called");

            if (serviceFacade == null) {
                throw new RuntimeException("serviceFacade==null and has not been initialised");
            }

            List<Flower> flowerList = serviceFacade.findBySymboOrSynonymSymbol(symbol);
            replyMessage.setFlowerList(flowerList);

            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /findBySymboOrSynonymSymbol ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /findBySymboOrSynonymSymbola " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    /**
     * List<Flower> findLikeScientificNamewithAuthor(String scientificNamewithAuthor);
     * http://localhost:8084/projectweb/rest/flowerService/findLikeScientificNamewithAuthor?name="Verbesina pauciflora"
     *
     * @return
     */
    @GET
    @Path("/findLikeScientificNamewithAuthor")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikeScientificNamewithAuthor(@QueryParam("name") String name) {
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
     * http://localhost:8084/projectweb/rest/flowerService/findLikeCommonName?name="ABAM5"
     *
     * @return
     */
    @GET
    @Path("/findLikeCommonName")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikeCommonName(@QueryParam("name") String name) {
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
     * http://localhost:8084/projectweb/rest/flowerService/findLikefamily?name="Asteraceae"
     *
     * @return
     */
    @GET
    @Path("/findLikefamily")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikefamily(@QueryParam("name") String name) {
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
     * public List<Flower> findLike(Flower flower); http://localhost:8084/projectweb/rest/flowerService/findLike
     *
     * @return
     */
    @POST
    @Path("/findLike")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findLikefamily(@QueryParam("flower") Flower flower) {
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
     * http://localhost:8084/projectweb/rest/flowerService/findLikeCommonName?name="ABAM5"
     *
     * @return
     */
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
