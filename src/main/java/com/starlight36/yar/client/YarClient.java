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

    private Properties configurationProperties;
    private Transport transport;

    public YarClient(String endpointUri) throws IOException {
        this(endpointUri, null);
    }

    public YarClient(String endpointUri, Properties configurationProperties) throws IOException {
        this.configurationProperties = new Properties(configurationProperties);
        transport = new TransportFactory().create(URI.create(endpointUri).getScheme());
        transport.configure(this.configurationProperties);
        transport.open(endpointUri);
    }

    public <E> E call(String method, Class<E> responseClass, Object... parameterObject) throws IOException {
        YarRequest yarRequest = new YarRequest();
        yarRequest.setPackagerName(configurationProperties.getProperty("yar.packager", "json"));
        yarRequest.setMethodName(method);
        if (parameterObject != null && parameterObject.length > 0) {
            yarRequest.setParameters(parameterObject);
        }
        YarResponse yarResponse = transport.execute(yarRequest);
        return yarResponse.fetchContent(responseClass);
    }

    public void close() throws IOException {
        if(transport != null) {
            transport.close();
        }
    }
}
