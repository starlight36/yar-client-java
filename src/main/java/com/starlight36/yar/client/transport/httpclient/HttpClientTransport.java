package com.starlight36.yar.client.transport.httpclient;

import com.starlight36.yar.client.YarRequest;
import com.starlight36.yar.client.YarResponse;
import com.starlight36.yar.client.transport.Transport;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Properties;

/**
 * HttpClientTransport
 */
public class HttpClientTransport implements Transport {

    private CloseableHttpClient httpClient;
    private String endpoint;
    private Properties configuration;

    public void configure(Properties properties) {
        configuration = properties;
    }

    public void open(String uri) throws IOException {
        httpClient = HttpClients.createDefault();
        endpoint = uri;
    }

    public YarResponse execute(YarRequest request) throws IOException {
        // Config http client.
        RequestConfig requestConfig =
            RequestConfig.custom()
            .setSocketTimeout(Integer.valueOf(configuration.getProperty("yar.timeout", "5000")))
            .setConnectTimeout(Integer.valueOf(configuration.getProperty("yar.connectTimeout", "1000")))
            .build();
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new ByteArrayEntity(request.toByteArray(), ContentType.APPLICATION_FORM_URLENCODED));
        return httpClient.execute(httpPost, new YarResponseHandler());
    }

    public void close() throws IOException {
        if(httpClient != null) {
            httpClient.close();
        }
    }
}
