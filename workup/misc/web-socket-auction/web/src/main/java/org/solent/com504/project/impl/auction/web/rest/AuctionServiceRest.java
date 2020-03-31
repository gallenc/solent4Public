/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.impl.auction.web.rest;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solent.com504.project.impl.auction.web.WebObjectFactory;
import org.solent.com504.project.model.auction.dto.Auction;
import org.solent.com504.project.model.auction.dto.Lot;
import org.solent.com504.project.model.dto.ReplyMessage;
import org.solent.com504.project.model.auction.service.AuctionService;
import org.solent.com504.project.model.flower.dto.Flower;
import org.solent.com504.project.model.party.dto.Party;

/**
 *
 * @author cgallen
 */
@Path("/auction")
public class AuctionServiceRest {

    private static final Logger LOG = LoggerFactory.getLogger(AuctionServiceRest.class);

    // http://localhost:8084/auction-events/rest/auction/auctionlist
    @GET
    @Path("/auctionlist")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAuctionList() {

        try {
            LOG.debug("getAuctionList() called");

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            List<Auction> auctionList = auctionService.getAuctionList();
            
            replyMessage.setAuctionList(auctionList);

            // LOG.debug("/retrievematching bookTemplate: " + bookTemplate 
            //          + " found books");
            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error GET calling /auctionlist ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /auctionlist " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @GET
    @Path("/auctiondetails")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAuctionDetails(@QueryParam("auctionuuid") String auctionuuid) {
        try {
            LOG.debug("getAuctionDetails(auctionuuid = " + auctionuuid
                    + ") called");
            if (auctionuuid == null) {
                throw new IllegalArgumentException("auctionuuid request parameter must be set");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            Auction auction = auctionService.getAuctionDetails(auctionuuid);
            if (auction != null) {
                List<Auction> auctionlist = Arrays.asList(auction);
                replyMessage.setAuctionList(auctionlist);
                replyMessage.setCode(Response.Status.OK.getStatusCode());

                return Response.status(Response.Status.OK).entity(replyMessage).build();
            } else {
                replyMessage.setCode(Response.Status.NOT_FOUND.getStatusCode());
                replyMessage.setDebugMessage("auction not found with auctionuuid=" + auctionuuid);
                return Response.status(Response.Status.NOT_FOUND).entity(replyMessage).build();
            }
        } catch (Exception ex) {
            LOG.error("error GET calling //auctionlist ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /auctionlist " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @GET
    @Path("/lotdetails")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getLotDetails(@QueryParam("lotuuid") String lotuuid) {
        try {
            LOG.debug("getLotDetails(lotuuid = " + lotuuid
                    + ") called");
            if (lotuuid == null) {
                throw new IllegalArgumentException("lotuuid request parameter must be set");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            Lot lot = auctionService.getLotDetails(lotuuid);
            if (lot != null) {
                List<Lot> lotlist = Arrays.asList(lot);
                replyMessage.setLotList(lotlist);
                replyMessage.setCode(Response.Status.OK.getStatusCode());

                return Response.status(Response.Status.OK).entity(replyMessage).build();
            } else {
                replyMessage.setCode(Response.Status.NOT_FOUND.getStatusCode());
                replyMessage.setDebugMessage("lot not found with lotuuid=" + lotuuid);
                return Response.status(Response.Status.NOT_FOUND).entity(replyMessage).build();
            }
        } catch (Exception ex) {
            LOG.error("error GET calling //lotlist ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /auctionlist " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @GET
    @Path("/auctionlots")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAuctionLots(@QueryParam("auctionuuid") String auctionuuid) {
        try {
            LOG.debug("getAuctionDetails(auctionuuid = " + auctionuuid
                    + ") called");
            if (auctionuuid == null) {
                throw new IllegalArgumentException("auctionuuid request parameter must be set");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            List<Lot> lots = auctionService.getAuctionLots(auctionuuid);

            replyMessage.setLotList(lots);
            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error GET calling //auctionlist ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /auctionlist " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @POST
    @Path("/addlottoauction")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addLotToAuction(@QueryParam("auctionuuid") String auctionuuid,
            @QueryParam("selleruuid") String selleruuid,
            @QueryParam("flowertype") Flower flowertype,
            @QueryParam("reserveprice") Double reserveprice,
            @QueryParam("quantity") Long quantity
    ) {
        try {
            LOG.debug("addlottoauction called auctionuuid=" + auctionuuid
                    + " selleruuid=" + selleruuid
                    + " flowertype=" + flowertype
                    + " reserveprice=" + reserveprice
                    + " quantity=" + quantity);
            if (auctionuuid == null) {
                throw new IllegalArgumentException("auctionuuid request parameter must be set");
            }
            if (selleruuid == null) {
                throw new IllegalArgumentException("selleruuid request parameter must be set");
            }
            if (flowertype == null) {
                throw new IllegalArgumentException("flowertype request parameter must be set");
            }
            if (reserveprice == null) {
                throw new IllegalArgumentException("reserveprice request parameter must be set");
            }
            if (quantity == null) {
                throw new IllegalArgumentException("quantity request parameter must be set");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            Lot lot = auctionService.addLotToAuction(auctionuuid, selleruuid, flowertype, reserveprice, quantity);

            List<Lot> lotlist = Arrays.asList(lot);
            replyMessage.setLotList(lotlist);
            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error GET calling //addlottoauction ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /addlottoauction " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @POST
    @Path("/registerforauction")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.MULTIPART_FORM_DATA})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response registerForAuction(@QueryParam("auctionuuid") String auctionuuid,
            @QueryParam("partyuuid") String partyUuid
    ) {
        try {
            LOG.debug("registerforauction called auctionuuid=" + auctionuuid
                    + " partyuuid=" + partyUuid);
            if (auctionuuid == null) {
                throw new IllegalArgumentException("auctionuuid request parameter must be set");
            }
            if (partyUuid == null) {
                throw new IllegalArgumentException("partyuuid request parameter must be set");
            }

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            String authKey = auctionService.registerForAuction(auctionuuid, partyUuid);

            replyMessage.setAuthKey(authKey);
            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error calling /registerforauction ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling /registerforauction " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

    @GET
    @Path("/partys")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getPartys() {
        try {
            LOG.debug("getPartys() called");

            ReplyMessage replyMessage = new ReplyMessage();

            AuctionService auctionService = WebObjectFactory.getAuctionService();
            List<Party> partyList = auctionService.getPartys();

            replyMessage.setPartyList(partyList);
            replyMessage.setCode(Response.Status.OK.getStatusCode());

            return Response.status(Response.Status.OK).entity(replyMessage).build();

        } catch (Exception ex) {
            LOG.error("error GET calling //auctionlist ", ex);
            ReplyMessage replyMessage = new ReplyMessage();
            replyMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
            replyMessage.setDebugMessage("error calling GET /auctionlist " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(replyMessage).build();
        }
    }

}
