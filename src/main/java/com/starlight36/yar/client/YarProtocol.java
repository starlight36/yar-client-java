package com.starlight36.yar.client;

import java.io.*;

/**
 * YarHeader
 */
public class YarProtocol {

    private long requestId = 0;

    private int version = 0;

    private final long magicNumber = 0x80DFEC60;

    private long reserved = 0;

    private String provider = null;

    private String token = null;

    private long bodyLenght = 0;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getBodyLenght() {
        return bodyLenght;
    }

    public void setBodyLenght(long bodyLenght) {
        this.bodyLenght = bodyLenght;
    }

    public void parse(byte[] header) throws IOException {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(header));
        try {
            setRequestId(in.readInt() & 0xffffffffL);
            setVersion(in.readUnsignedShort());
            if(in.readInt() != 0x80DFEC60) {
                throw new IOException("Invalid Yar header.");
            }
            reserved = in.readInt() & 0xffffffffL;
            in.skip(64);
            setBodyLenght(in.readInt() & 0xffffffffL);
        } finally {
            in.close();
        }
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);
        try {
            out.writeInt((int)getRequestId());
            out.writeShort((short)getVersion());
            out.writeInt((int)magicNumber);
            out.writeInt((int)reserved);
            out.write(new byte[64]);
            out.writeInt((int)getBodyLenght());
            return byteStream.toByteArray();
        } finally {
            byteStream.close();
            out.close();
        }
    }
}
