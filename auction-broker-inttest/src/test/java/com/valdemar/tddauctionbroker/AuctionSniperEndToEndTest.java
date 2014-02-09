package com.valdemar.tddauctionbroker;

import org.junit.After;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AuctionSniperEndToEndTest {

private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
private final ApplicationRunner application = new ApplicationRunner();

        @Test
        public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
            auction.startSellingItem();                 // Step 1
            application.startBiddingIn(auction);        // Step 2
            auction.assertThatHasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID); // Step 3
            auction.announceClosed();                   // Step 4
            application.showsSniperHasLostAuction();    // Step 5
        }

    @Test
    public void sniperMakesHigherBidButLoses() throws Exception {

        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.assertThatHasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding();

        auction.assertThatHasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

        // Additional cleanup
        @After
        public void stopAuction() {
            auction.stop();
        }
        @After public void stopApplication() {
            application.stop();
        }
}
