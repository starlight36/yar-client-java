package com.starlight36.yar.client.transport;

import com.starlight36.yar.client.YarCallback;
import com.starlight36.yar.client.YarRequest;
import com.starlight36.yar.client.YarResponse;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * Transport interface
 */
public interface Transport<T> extends Closeable {

    void configure(Properties properties);

    void init(String uri) throws IOException;

    YarResponse execute(YarRequest request) throws IOException;

    void asyncExecute(YarRequest[] request, final YarCallback<T> callback, final CountDownLatch latch, Class<T> cla) throws Exception;

}
