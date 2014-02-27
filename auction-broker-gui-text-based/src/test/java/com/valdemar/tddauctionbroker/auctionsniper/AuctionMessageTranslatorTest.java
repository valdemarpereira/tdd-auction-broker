package com.valdemar.tddauctionbroker.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created with IntelliJ IDEA.
 * User: valdemar
 * Date: 11-02-2014
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuctionMessageTranslatorTest {

    public static final String SNIPER_ID = "sniper";
    public static final Chat UNUSED_CHAT = null;
    @Mock
    private AuctionEventListener listener;
    private AuctionMessageTranslator translator;

    @Before
    public void setup() {
        translator = new AuctionMessageTranslator(SNIPER_ID, listener);
    }

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() {

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: CLOSE;");

        translator.processMessage(UNUSED_CHAT, message);

        verify(listener).auctionClosed();
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");

        translator.processMessage(UNUSED_CHAT, message);

        verify(listener, times(1)).currentPrice(192, 7, AuctionEventListener.PriceSource.FromOtherBidder);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {

        Message message = new Message();
        message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_ID + ";");

        translator.processMessage(UNUSED_CHAT, message);

        verify(listener, times(1)).currentPrice(234, 5, AuctionEventListener.PriceSource.FromSniper);
    }

}
