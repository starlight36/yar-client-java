package com.starlight36.yar.client;

import com.starlight36.yar.client.transport.Transport;
import com.starlight36.yar.client.transport.TransportFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

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

    public <T> void call(YarRequest request[], YarCallback<T> cb, Class<T> cla) throws Exception {

        final CountDownLatch latch = new CountDownLatch(request.length);

        transport.asyncExecute(request, cb, latch, cla);
        latch.await();
    }


    public void close() throws IOException {
        if(transport != null) {
            transport.close();
        }
    }
}
