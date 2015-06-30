package com.starlight36.yar.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starlight36.yar.client.packager.Packager;
import com.starlight36.yar.client.packager.PackagerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * YarResponse
 */
public class YarResponse {

    private static final Logger logger = Logger.getLogger(YarResponse.class.getName());

    private ByteArrayInputStream responseDataInputStream;

    public YarResponse(byte[] responseData) {
        responseDataInputStream = new ByteArrayInputStream(responseData);
    }

    public <T> T fetchContent(Class<T> contentClass) throws IOException {
        // Read protocol header.
        byte[] headerBytes = new byte[82];
        responseDataInputStream.read(headerBytes);
        YarProtocol protocol = new YarProtocol();
        protocol.parse(headerBytes);

        // Read 8 bytes as packager name.
        byte[] packagerNameBytes = new byte[8];
        responseDataInputStream.read(packagerNameBytes);
        // Trade '\0' as the terminal char.
        int length;
        for(length = 0; length < packagerNameBytes.length && packagerNameBytes[length] != 0; length++);
        String packagerName = new String(packagerNameBytes, 0, length);

        // remain bytes is response content.
        byte[] bodyBytes = new byte[(int)(protocol.getBodyLenght() - 8)];
        responseDataInputStream.read(bodyBytes);

        // Unpack all results
        Packager packager = new PackagerFactory().createPackager(packagerName);
        Map entry = packager.decode(bodyBytes, contentClass);

        // Server output will be write to logger
        String output = entry.get("o").toString();
        if(output != null && !output.isEmpty()) {
            logger.log(Level.INFO, output);
        }

        // if it has some exception...
        if(entry.containsKey("e")) {
            Map exceptionMap = (Map) entry.get("e");
            if (exceptionMap != null) {
                String message = exceptionMap.get("message").toString();
                String file =  exceptionMap.get("file").toString();
                Long code = Long.valueOf(exceptionMap.get("code").toString());
                Integer line = Integer.valueOf(exceptionMap.get("line").toString());
                throw new YarException(message, code, file, line);
            }
        }

        return contentClass.cast(entry.get("r"));
    }
}
