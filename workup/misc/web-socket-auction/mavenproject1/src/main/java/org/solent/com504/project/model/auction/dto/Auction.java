package org.solent.com504.project.model.auction.dto;

public class Auction {

    private Date startTime;

    private Long id;

    private List<Lot> lots;

    private String description;

    private List<Party> registeredPartys;

    private String auctionuuid;

    private AuctionStatus auctionStatus;
}
