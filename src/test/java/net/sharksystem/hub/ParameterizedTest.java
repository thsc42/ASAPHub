package net.sharksystem.hub;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.hub.hubside.ASAPTCPHub;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.beans.ExceptionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ParameterizedTest {

    @Parameterized.Parameters
    public static Collection<Object[]> getFiles() {
        Collection<Object[]> params = new ArrayList<>();
        for (File testPlanSubDirs : new File("testplans").listFiles()) {
            Object[] subDir = new Object[]{testPlanSubDirs};
            params.add(subDir);
        }
        return params;
    }

    private File testPlanDir;
    private final int port = 6910;
    private final String host = "localhost";

    public ParameterizedTest(File testPlanDir) {
        this.testPlanDir = testPlanDir;
    }

    @Test
    public void testY() throws IOException, ASAPException, InterruptedException {
        ASAPTCPHub hub = new ASAPTCPHub(port, true);
        hub.setPortRange(7000, 9000); // optional - required to configure a firewall
        hub.setMaxIdleConnectionInSeconds(60);
        new Thread(hub).start();

        CLITestExceptionListener listener = new CLITestExceptionListener();
        System.out.println("executing testplan: " + testPlanDir.getName());

        File[] testPlans = testPlanDir.listFiles();
        for (int i = 0; i < testPlans.length - 1; i++) {
            FileInputStream fis = new FileInputStream(testPlans[i]);
            Runnable r = () -> {
                try {
                    new HubConnectorCLI(fis, System.out, host, port, listener).startCLI();
                } catch (IOException | ASAPHubException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };

            File lastTestPlanElem = testPlans[testPlans.length - 1];
            FileInputStream fis2 = new FileInputStream(lastTestPlanElem);
            new Thread(r).start();

            new HubConnectorCLI(fis2, System.out, host, port, listener).startCLI();
            Thread.sleep(2000);

            hub.kill();
            Assert.assertNull(listener.getException());


        }
    }

    private class CLITestExceptionListener implements ExceptionListener {
        private Exception exception = null;

        @Override
        public void exceptionThrown(Exception e) {
            exception = e;
        }

        public Exception getException() {
            return exception;
        }
    }
}