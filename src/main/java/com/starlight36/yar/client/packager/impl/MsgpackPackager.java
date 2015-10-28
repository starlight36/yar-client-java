package com.starlight36.yar.client.packager.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlight36.yar.client.packager.Packager;
import org.msgpack.MessagePack;
import org.msgpack.type.ArrayValue;
import org.msgpack.type.MapValue;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MsgpackPackager
 */
public class MsgpackPackager implements Packager {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <E> byte[] encode(E value) throws IOException {
        MessagePack msgpack = new MessagePack();
        return msgpack.write(value);
    }

    public <E> Map decode(byte[] data, Class<E> messageType) throws IOException {
        MessagePack msgpack = new MessagePack();
        String jsonData = msgpack.read(data).toString();
        Map resultMap = objectMapper.readValue(jsonData, Map.class);
        if (resultMap.containsKey("r")) {
            resultMap.put("r", objectMapper.convertValue(resultMap.get("r"), messageType));
        }
        return resultMap;
    }
}
