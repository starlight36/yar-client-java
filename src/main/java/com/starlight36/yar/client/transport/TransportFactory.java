package com.starlight36.yar.client.transport;

import com.starlight36.yar.client.transport.httpclient.HttpClientTransport;

/**
 * TransportFactory
 */
public class TransportFactory {

    public Transport create(String protocolName) {
        if(protocolName == null) {
            throw new IllegalArgumentException("Protocol name should not be null.");
        }
        if(protocolName.equalsIgnoreCase("http") || protocolName.equalsIgnoreCase("https")) {
            return new HttpClientTransport();
        } else {
            throw new IllegalArgumentException("Unsupported protocol " + protocolName + ".");
        }
    }

}
