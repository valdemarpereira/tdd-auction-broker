package com.valdemar.tddauctionbroker;

import com.valdemar.tddauctionbroker.auctionsniper.AuctionEventListener;
import com.valdemar.tddauctionbroker.auctionsniper.AuctionMessageTranslator;
import com.valdemar.tddauctionbroker.auctionsniper.AuctionSniper;
import com.valdemar.tddauctionbroker.auctionsniper.SniperListener;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 09-02-2014
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public class Main implements SniperListener {

    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT =
            ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";


    Chat chat;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();

        main.joinAuction(
                connectTo(
                        args[ARG_HOSTNAME],
                        args[ARG_USERNAME],
                        args[ARG_PASSWORD]),
                args[ARG_ITEM_ID]);

    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), null);

        this.chat = chat;
        Auction auction = new XMPPAuction(chat);
        chat.addMessageListener(new AuctionMessageTranslator(connection.getUser(), new AuctionSniper(auction, this)));

        auction.join();
    }

    private static XMPPConnection connectTo(String hostname, String username, String password)
            throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);

        return connection;
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId,
                connection.getServiceName());
    }

    private void startUserInterface() throws Exception {
        System.out.println("Main Screen");
    }

    private void showStatus(String status) {
        System.out.println(status);
    }


    @Override
    public void sniperLost() {
        System.out.println("Lost the auction");
    }

    @Override
    public void sniperBidding(int bidingValue, AuctionEventListener.PriceSource from) {
        System.out.println("Our Snipper just bid " + bidingValue);

    }

    private static class XMPPAuction implements Auction {

        private final Chat chat;

        public XMPPAuction(Chat chat) {
            this.chat = chat;
        }

        @Override
        public void bid(int amount) {
            sendMessage(String.format(BID_COMMAND_FORMAT, amount));
        }

        @Override
        public void join() {
            sendMessage(JOIN_COMMAND_FORMAT);
        }

        private void sendMessage(final String message) {
            try {
                chat.sendMessage(message);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }
}
