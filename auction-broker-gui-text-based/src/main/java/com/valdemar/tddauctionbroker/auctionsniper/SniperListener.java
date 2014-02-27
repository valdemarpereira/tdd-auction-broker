package com.valdemar.tddauctionbroker.auctionsniper;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 11-02-2014
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public interface SniperListener {
    void sniperLost();

    void sniperBidding(int bidingValue, AuctionEventListener.PriceSource from);
}
