package net.sharksystem.hub.util;

import net.sharksystem.hub.ASAPHubException;

import net.sharksystem.hub.hubside.ConnectorInternal;
import net.sharksystem.streams.StreamPair;
import net.sharksystem.streams.StreamPairImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectorInternalLocalStub implements ConnectorInternal {

    private InputStream inputStream;
    private OutputStream outputStream;

    public ConnectorInternalLocalStub(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void connectionRequest(CharSequence sourcePeerID, CharSequence targetPeerID, int timeout) throws ASAPHubException, IOException {

    }

    @Override
    public void disconnect(CharSequence sourcePeerID, CharSequence targetPeerID) throws ASAPHubException {

    }

    @Override
    public void startDataSession(CharSequence sourcePeerID, CharSequence targetPeerID, StreamPair connection, int timeout) throws ASAPHubException, IOException {

    }

    @Override
    public void notifyConnectionEnded(CharSequence sourcePeerID, CharSequence targetPeerID, StreamPair connection) throws ASAPHubException {

    }


    @Override
    public StreamPair initDataSession(CharSequence sourcePeerID, CharSequence targetPeerID, int timeout) throws ASAPHubException, IOException {
        return StreamPairImpl.getStreamPair(this.inputStream, this.outputStream);
    }
}