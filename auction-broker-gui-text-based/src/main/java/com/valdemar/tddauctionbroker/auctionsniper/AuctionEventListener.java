package com.valdemar.tddauctionbroker.auctionsniper;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 11-02-2014
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public interface AuctionEventListener {

    enum PriceSource {
        FromSniper, FromOtherBidder;
    }

    ;

    public void auctionClosed();

    public void currentPrice(int currentPrice, int increment, PriceSource from);
}
