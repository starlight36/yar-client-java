package com.starlight36.yar.client.packager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * JsonPackager
 */
public class JsonPackager implements Packager {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <E> byte[] encode(E value) throws IOException {
        return objectMapper.writeValueAsBytes(value);
    }

    public <E> Map decode(byte[] data, Class<E> messageType) throws IOException {
        Map resultMap = objectMapper.readValue(data, Map.class);
        if (resultMap.containsKey("r")) {
            resultMap.put("r", objectMapper.convertValue(resultMap.get("r"), messageType));
        }
        return resultMap;
    }
}
