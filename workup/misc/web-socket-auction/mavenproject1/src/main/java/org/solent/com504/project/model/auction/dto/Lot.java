package org.solent.com504.project.model.auction.dto;

public class Lot {

    private Long id;

    private List<Bid> bids;

    private Bid winningBid;

    private Flower flowerType;

    private Long quantity;

    private Double reservePrice;

    private Double soldPrice;

    private Party seller;

    private Party buyer;

    private String lotuuid;

    private Boolean active;
}
