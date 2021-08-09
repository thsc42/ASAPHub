package net.sharksystem.hub;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.hub.peerside.HubConnector;
import net.sharksystem.hub.hubside.TCPHub;
import net.sharksystem.hub.peerside.SharedTCPChannelConnectorPeerSide;
import org.junit.Test;

import java.io.IOException;

import static net.sharksystem.hub.TestConstants.ALICE_ID;

public class HubTests {

    @Test
    public void usage() throws IOException, InterruptedException, ASAPException {
        int specificPort = 6907;
        CharSequence host = "localhost";
        TCPHub hub = new TCPHub(specificPort);
        hub.setPortRange(7000, 9000); // optional - required to configure a firewall
        hub.setMaxIdleConnectionInSeconds(TestConstants.maxTimeInSeconds);
        new Thread(hub).start();

        HubConnector aliceHubConnector = SharedTCPChannelConnectorPeerSide.createTCPHubConnector(host, specificPort);
        HubConnectorTester aliceListener = new HubConnectorTester(ALICE_ID);
        aliceHubConnector.addListener(aliceListener);

        aliceHubConnector.connectHub(ALICE_ID);
    }

    @Test
    public void remoteAccess() throws IOException, InterruptedException, ASAPException {
        int specificPort = TCPHub.DEFAULT_PORT;
        CharSequence host = "asaphub.f4.htw-berlin.de";

        HubConnector aliceHubConnector = SharedTCPChannelConnectorPeerSide.createTCPHubConnector(host, specificPort);
        HubConnectorTester aliceListener = new HubConnectorTester(ALICE_ID);
        aliceHubConnector.addListener(aliceListener);

        aliceHubConnector.connectHub(ALICE_ID);

        Thread.sleep(Long.MAX_VALUE);
    }
}
