package com.starlight36.yar.client.packager;

import java.io.IOException;

/**
 * Created by liusixian on 15/6/23.
 */
public interface Packager {

    <E> byte[] encode(E value) throws IOException;

    <E> E decode(byte[] data, Class<E> messageType) throws IOException;

}
