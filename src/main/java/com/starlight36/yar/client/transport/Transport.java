package com.starlight36.yar.client.transport;

import com.starlight36.yar.client.YarRequest;
import com.starlight36.yar.client.YarResponse;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

/**
 * Transport interface
 */
public interface Transport extends Closeable {

    void configure(Properties properties);

    void init(String uri) throws IOException;

    YarResponse execute(YarRequest request) throws IOException;

}
