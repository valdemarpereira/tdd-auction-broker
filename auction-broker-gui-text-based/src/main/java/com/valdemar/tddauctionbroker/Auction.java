package com.valdemar.tddauctionbroker;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 11-02-2014
 * Time: 22:33
 * To change this template use File | Settings | File Templates.
 */
public interface Auction {
    public void join();

    public void bid(int priceToBid);
}
