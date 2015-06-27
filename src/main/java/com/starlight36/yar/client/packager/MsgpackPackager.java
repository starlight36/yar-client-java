package com.starlight36.yar.client.packager;

import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.io.IOException;

/**
 * MsgpackPackager
 */
public class MsgpackPackager implements Packager {

    public <E> byte[] encode(E value) throws IOException {
        MessagePack msgpack = new MessagePack();
        return msgpack.write(value);
    }

    public <E> E decode(byte[] data, Class<E> messageType) throws IOException {
        MessagePack msgpack = new MessagePack();
        Value dynamic = msgpack.read(data);
        Converter converter = new Converter(dynamic);
        return converter.read(messageType);
    }

}
