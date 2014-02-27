package com.valdemar.tddauctionbroker;

import static com.valdemar.tddauctionbroker.FakeAuctionServer.XMPP_HOSTNAME;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 08-02-2014
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = "sniper@asus-laptop/Auction";
    //  private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
//        driver = new AuctionSniperDriver(1000);
//        driver.showsSniperStatus(Main.STATUS_JOINING);
    }

    public void showsSniperHasLostAuction() {
//        driver.showsSniperStatus(Main.STATUS_LOST);
    }

    public void stop() {
//        if (driver != null) {
//            driver.dispose();
//        }
    }

    public void hasShownSniperIsBidding() {


    }
}
