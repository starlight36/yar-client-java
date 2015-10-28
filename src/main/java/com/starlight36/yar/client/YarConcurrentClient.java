package com.starlight36.yar.client;

import com.starlight36.yar.client.transport.Transport;
import com.starlight36.yar.client.transport.TransportFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;

/**
 * Created by sunan on 10/28/15.
 */
public class YarConcurrentClient {
    private Properties configs;
    private Transport transport;

    public YarConcurrentClient(String endpointUri) throws IOException {
        this(endpointUri, null);
    }

    public YarConcurrentClient(String endpointUri, Properties _config) throws IOException {
        configs = new Properties(_config);
        transport = new TransportFactory().create(URI.create(endpointUri).getScheme());
        transport.configure(configs);
        transport.init(endpointUri);
    }

    public <E> E call(YarRequest request, Class<E> responseClass) throws IOException {

        YarResponse response = transport.execute(request);
        return response.fetchContent(responseClass);
    }

    public <E> E call(String method, Class<E> responseClass, Object... parameterObject) throws IOException {
        YarRequest request = new YarRequest(method, configs.getProperty("yar.packager", "json"));
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
