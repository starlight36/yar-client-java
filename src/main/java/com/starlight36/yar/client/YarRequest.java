package com.starlight36.yar.client;

import com.starlight36.yar.client.packager.Packager;
import com.starlight36.yar.client.packager.PackagerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * YarRequest
 */
public class YarRequest {

    private String  packagerName;
    private String  methodName;
    private List    parameters;

    private PackagerFactory packagerFactory = new PackagerFactory();

    public String getPackagerName() {
        return packagerName;
    }

    public void setPackagerName(String packagerName) {
        this.packagerName = packagerName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        List<Object> list = new ArrayList<Object>();
        for (Object param : parameters) {
            list.add(param);
        }
        this.parameters = list;
    }

    public YarRequest(String method, String packager) {
        this.methodName = method;
        this.packagerName = packager;
    }

    /**
     * 请求转换为字节
     * @return
     */
    public byte[] toByteArray() throws IOException {
        Map<String, Object> requestMap = new HashMap<String, Object>();
        requestMap.put("i", "");
        requestMap.put("m", getMethodName());
        requestMap.put("p", getParameters());
        Packager packager = packagerFactory.createPackager(packagerName);

        byte[] bodyBytes;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(Arrays.copyOf(packagerName.toUpperCase().getBytes(), 8));
            out.write(packager.encode(requestMap));
            bodyBytes = out.toByteArray();
        } finally {
            out.close();
        }

        YarProtocol protocol = new YarProtocol();
        protocol.setBodyLenght(bodyBytes.length);
        ByteArrayOutputStream totalByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            totalByteArrayOutputStream.write(protocol.toByteArray());
            totalByteArrayOutputStream.write(bodyBytes);
            return totalByteArrayOutputStream.toByteArray();
        } finally {
            totalByteArrayOutputStream.close();
        }
    }
}
