package com.starlight36.yar.client.transport.httpclient;

import com.starlight36.yar.client.YarRequest;
import com.starlight36.yar.client.YarResponse;
import com.starlight36.yar.client.transport.Transport;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * HttpClientTransport
 */
public class HttpClientTransport implements Transport {

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

    public void asyncExecute(YarRequest request) throws InterruptedException {
        asyncClient.start();
        final HttpGet[] requests = new HttpGet[] {
                new HttpGet("http://mapi.weibo.com/"),
                new HttpGet("http://www.google.com/")
        };
        final CountDownLatch latch = new CountDownLatch(requests.length);
        FutureCallback callback = new FutureCallback<HttpResponse>() {

            @Override
            public void completed(final HttpResponse response) {
                latch.countDown();
                System.out.println("->" + response.getStatusLine());
            }

            @Override
            public void failed(final Exception ex) {
                latch.countDown();
                System.out.println("->" + ex);
            }

            @Override
            public void cancelled() {
                latch.countDown();
                System.out.println(" cancelled");
            }

        };

        for (final HttpGet req: requests) {
            asyncClient.execute(req, callback);
        }
        latch.await();
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
