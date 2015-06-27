package com.starlight36.yar.client.packager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JsonPackager
 */
public class JsonPackager implements Packager {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <E> byte[] encode(E value) throws IOException {
        return objectMapper.writeValueAsBytes(value);
    }

    public <E> E decode(byte[] data, Class<E> messageType) throws IOException {
        return objectMapper.readValue(data, messageType);
    }
}
