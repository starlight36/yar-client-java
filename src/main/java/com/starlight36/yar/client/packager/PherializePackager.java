package com.starlight36.yar.client.packager;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ailis.pherialize.Mixed;
import de.ailis.pherialize.Pherialize;

import java.io.IOException;

/**
 * PherializePackager
 */
public class PherializePackager implements Packager {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <E> byte[] encode(E value) throws IOException {
        return Pherialize.serialize(value).getBytes();
    }

    public <E> E decode(byte[] data, Class<E> messageType) throws IOException {
        Mixed mixed = Pherialize.unserialize(new String(data, 0, data.length, "ISO-8859-1"));
        return objectMapper.convertValue(mixed.getValue(), messageType);
    }
}
