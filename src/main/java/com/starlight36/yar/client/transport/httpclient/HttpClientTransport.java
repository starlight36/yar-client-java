package com.starlight36.yar.client.transport.httpclient;

import com.starlight36.yar.client.YarCallback;
import com.starlight36.yar.client.YarRequest;
import com.starlight36.yar.client.YarResponse;
import com.starlight36.yar.client.transport.Transport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * HttpClientTransport
 */
public class HttpClientTransport<T> implements Transport<T> {

    private CloseableHttpClient httpClient;
    private CloseableHttpAsyncClient asyncClient;
    private String endpoint;
    private Properties configuration;

    public void configure(Properties properties) {
        configuration = properties;
    }

    public void init(String uri) throws IOException {
        endpoint = uri;



        RequestConfig requestConfig = RequestConfig
                .custom()
                .setSocketTimeout(Integer.valueOf(configuration.getProperty("yar.timeout", "5000")))
                .setConnectTimeout(Integer.valueOf(configuration.getProperty("yar.connectTimeout", "1000")))
                .build();

        httpClient = HttpClients.
                custom().
                setDefaultRequestConfig(requestConfig).
                build();

        //asyn
        RequestConfig asynRequestConfig = RequestConfig.custom()
                .setSocketTimeout(Integer.valueOf(configuration.getProperty("yar.timeout", "5000")))
                .setConnectTimeout(Integer.valueOf(configuration.getProperty("yar.connectTimeout", "1000")))
                .build();
        asyncClient = HttpAsyncClients
                .custom()
                .setDefaultRequestConfig(asynRequestConfig)
                .build();

    }

    public void asyncExecute(YarRequest[] request, final YarCallback<T> callback, final CountDownLatch latch, final Class<T> cla) throws Exception {
        asyncClient.start();

        final HttpPost[] requests = new HttpPost[request.length];

        for (int i = 0; i < request.length; i++) {
            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.setEntity(new ByteArrayEntity(request[i].toByteArray(), ContentType.APPLICATION_FORM_URLENCODED));

            requests[i] = httpPost;
        }

        FutureCallback cb = new FutureCallback<HttpResponse>() {

            @Override
            public void completed(HttpResponse response) {
                try {

                    HttpEntity entity = response.getEntity();


                    byte[] bytes = EntityUtils.toByteArray(entity);
                    YarResponse yarResponse = new YarResponse(bytes);
                    callback.completed(yarResponse.fetchContent(cla));
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                    latch.countDown();
                }
            }

            @Override
            public void failed(final Exception ex) {
                callback.failed(ex);
                latch.countDown();
            }

            @Override
            public void cancelled() {
                callback.cancelled();
                latch.countDown();
            }

        };


        for (final HttpPost req: requests) {
            asyncClient.execute(req, cb);
        }

    }

    public YarResponse execute(YarRequest request) throws IOException {
        // Config http client.

        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setEntity(new ByteArrayEntity(request.toByteArray(), ContentType.APPLICATION_FORM_URLENCODED));
        return httpClient.execute(httpPost, new YarResponseHandler());
    }

    public void close() throws IOException {
        if(httpClient != null) {
            httpClient.close();
        }
    }
}
