package com.starlight36.yar.client.packager.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlight36.yar.client.packager.Packager;
import de.ailis.pherialize.Mixed;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * PherializePackager
 */
public class PherializePackager implements Packager {

    private ObjectMapper objectMapper = new ObjectMapper();

    public <E> byte[] encode(E value) throws IOException {
        return Pherialize.serialize(value).getBytes();
    }

    public <E> Map decode(byte[] data, Class<E> messageType) throws IOException {
        Mixed mixed = Pherialize.unserialize(new String(data, 0, data.length, "ISO-8859-1"));
        throw new UnsupportedOperationException("Not implemented.");
    }
}
