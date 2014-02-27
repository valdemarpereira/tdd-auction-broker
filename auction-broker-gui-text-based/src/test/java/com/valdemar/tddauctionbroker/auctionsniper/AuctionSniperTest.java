package com.valdemar.tddauctionbroker.auctionsniper;

import com.valdemar.tddauctionbroker.Auction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuctionSniperTest {

    @Mock
    private SniperListener listener;
    @Mock
    private Auction auction;
    private AuctionSniper auctionSniper;

    @Before
    public void setup() {
        auctionSniper = new AuctionSniper(auction, listener);
    }

    @Test
    public void reportsLostWhenAuctionCloses() throws Exception {
        auctionSniper.auctionClosed();
        verify(listener).sniperLost();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() throws Exception {
        final int price = 1001;
        final int increment = 25;

        auctionSniper.currentPrice(price, increment, AuctionEventListener.PriceSource.FromSniper);
        verify(auction).bid(price + increment);
    }
}
