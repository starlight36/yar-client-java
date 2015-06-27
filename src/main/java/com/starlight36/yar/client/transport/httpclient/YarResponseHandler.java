package com.starlight36.yar.client.transport.httpclient;

import com.starlight36.yar.client.YarResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * YarResponseHandler
 */
public class YarResponseHandler implements ResponseHandler<YarResponse> {

    /**
     * 处理HTTP响应
     * @param httpResponse
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public YarResponse handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
        int status = httpResponse.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = httpResponse.getEntity();
            byte[] data = entity != null ? EntityUtils.toByteArray(entity) : null;
            return new YarResponse(data);
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
