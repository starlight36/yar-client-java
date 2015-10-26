package com.starlight36.yar.client.packager;

import com.starlight36.yar.client.packager.impl.JsonPackager;
import com.starlight36.yar.client.packager.impl.MsgpackPackager;
import com.starlight36.yar.client.packager.impl.PherializePackager;

/**
 * PackagerFactory
 */
public class PackagerFactory {

    /**
     * Create a new packager by name
     * @param packagerName
     * @return
     */
    public Packager createPackager(String packagerName) {
        if(packagerName == null) {
            throw new IllegalArgumentException("Packager name should not be null.");
        }
        if(packagerName.equalsIgnoreCase("PHP")) {
            return new PherializePackager();
        } else if(packagerName.equalsIgnoreCase("JSON")) {
            return new JsonPackager();
        } else if(packagerName.equalsIgnoreCase("MSGPACK")) {
            return new MsgpackPackager();
        } else {
            throw new IllegalArgumentException("Unknown packager type " + packagerName + ".");
        }
    }
}
