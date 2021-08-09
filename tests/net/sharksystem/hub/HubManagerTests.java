package net.sharksystem.hub;

import net.sharksystem.asap.*;
import net.sharksystem.asap.apps.testsupport.ASAPTestPeerFS;
import net.sharksystem.hub.hubside.TCPHub;
import net.sharksystem.hub.peerside.ASAPHubManager;
import net.sharksystem.hub.peerside.ASAPHubManagerImpl;
import net.sharksystem.hub.peerside.HubConnector;
import net.sharksystem.hub.peerside.SharedTCPChannelConnectorPeerSide;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static net.sharksystem.hub.TestConstants.maxTimeInSeconds;

public class HubManagerTests {
    public static final String ALICE_ROOT_FOLDER = "hubManagerTests/Alice";
    public static final String ALICE = "Alice";
    public static final String BOB = "Bob";
    public static final String FORMAT = "asap/hubmanagertests";


    @Test
    public void test1() throws IOException, ASAPException, InterruptedException {
        // launch hub
        int specificPort = 6907;
        CharSequence host = "localhost";
        TCPHub hub = new TCPHub(specificPort);
        hub.setMaxIdleConnectionInSeconds(maxTimeInSeconds);
        new Thread(hub).start();

        Set formats = new HashSet();
        formats.add(FORMAT);

        // create alice peer
        ASAPTestPeerFS alicePeer = new ASAPTestPeerFS(ALICE, formats);

        // connect to hub
        HubConnector aliceHubConnector = SharedTCPChannelConnectorPeerSide.createTCPHubConnector(host, specificPort);
        HubConnectorTester aliceListener = new HubConnectorTester(ALICE);
        aliceHubConnector.addListener(aliceListener);

        // create alice BOB
        ASAPTestPeerFS bobPeer = new ASAPTestPeerFS(ALICE, formats);

        // connect to hub
        HubConnector bobHubConnector = SharedTCPChannelConnectorPeerSide.createTCPHubConnector(host, specificPort);
        HubConnectorTester bobListener = new HubConnectorTester(BOB);
        bobHubConnector.addListener(bobListener);

        // connect to hub
        aliceHubConnector.connectHub(ALICE);
        bobHubConnector.connectHub(BOB);

        // give it soem time
        Thread.sleep(1000);

        // add to hub manager
        ASAPEncounterManagerImpl asapEncounterManager = new ASAPEncounterManagerImpl(alicePeer);
        ASAPHubManagerImpl asapASAPHubManager = new ASAPHubManagerImpl(asapEncounterManager);
        asapASAPHubManager.addHub(aliceHubConnector);

        // start hub manager
        new Thread(asapASAPHubManager).start();

        Thread.sleep(1000);
        //Thread.sleep(Long.MAX_VALUE);
    }
}
