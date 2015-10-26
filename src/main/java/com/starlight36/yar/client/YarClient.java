package com.starlight36.yar.client;

import com.starlight36.yar.client.transport.Transport;
import com.starlight36.yar.client.transport.TransportFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * YarClient
 */
public class YarClient implements Closeable {

    private Properties  configs;
    private Transport   transport;

    public YarClient(String endpointUri) throws IOException {
        this(endpointUri, null);
    }

    public YarClient(String endpointUri, Properties configurations) throws IOException {
        configs = new Properties(configurations);
        transport = new TransportFactory().create(URI.create(endpointUri).getScheme());
        transport.configure(configurations);
        transport.init(endpointUri);
    }

    public <E> E call(YarRequest request, Class<E> responseClass) throws IOException {

        YarResponse response = transport.execute(request);
        return response.fetchContent(responseClass);
    }

    public <E> E call(String method, Class<E> responseClass, Object... parameterObject) throws IOException {
        YarRequest request = new YarRequest(method);
        if (parameterObject != null && parameterObject.length > 0) {
            request.setParameters(parameterObject);
        }
        YarResponse response = transport.execute(request);
        return response.fetchContent(responseClass);
    }

    public void close() throws IOException {
        if(transport != null) {
            transport.close();
        }
    }
}
