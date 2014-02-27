package com.valdemar.tddauctionbroker.auctionsniper;

import com.valdemar.tddauctionbroker.Auction;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 11-02-2014
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class AuctionSniper implements AuctionEventListener {

    private SniperListener listener;
    private Auction auction;

    public AuctionSniper(Auction auction, SniperListener listener) {
        this.listener = listener;
        this.auction = auction;
    }

    @Override
    public void auctionClosed() {
        listener.sniperLost();
    }

    @Override
    public void currentPrice(int currentPrice, int increment, PriceSource from) {
        auction.bid(currentPrice + increment);
        listener.sniperBidding(currentPrice + increment, from);
    }
}
