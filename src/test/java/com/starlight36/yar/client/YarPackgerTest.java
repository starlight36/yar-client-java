package com.starlight36.yar.client;

/**
 * Created by jason on 1/22/16.
 */

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YarPackgerTest {

    private YarClient client;
    private Thread phpweb;

    @Before
    public void setUp() throws Exception {
        final StringBuffer output = new StringBuffer();

        Runnable r = new Runnable() {
            public void run() {
                try {
                    Process p;
                    p = Runtime.getRuntime().exec("/usr/bin/php -ddate.timezone=PRC -dextension=yar.so -S 127.0.0.1:8095 -t src/test/resources");
                    p.waitFor();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                    String line = "";
                    while ((line = reader.readLine())!= null) {
                        output.append(line + "\n");
                    }
                } catch(InterruptedException e) {
                    //TODO may terminted by others
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        phpweb = new Thread(r);
        phpweb.start();

        client = new YarClient("http://127.0.0.1:8095/yar.php");
    }

    private YarRequest buildReq() {
        YarRequest request = new YarRequest("echoRequest", "json");
        Object[] params = new Object[3];
        params[0] = "1234";
        List<String> list = new ArrayList<String>();
        list.add("ok");
        params[1] = list;
        request.setParameters(params);

        return request;
    }

    @Test
    public void testEchoRequestWithJson() throws IOException {

        YarRequest request = buildReq();
        Map<String, String> returnMap = client.call(request, Map.class);

        Assert.assertNotNull(returnMap);
        Assert.assertEquals("1234", returnMap.get("param1"));
        List<String> expected = new ArrayList<String>();
        expected.add("ok");
        Assert.assertEquals(expected, returnMap.get("param2"));
    }

    @Test
    public void testEchoRequestWithMsgpack() throws IOException {

        YarRequest request = buildReq();
        request.setPackagerName("msgpack");
        Map<String, String> returnMap = client.call(request, Map.class);

        Assert.assertNotNull(returnMap);
        Assert.assertEquals("1234", returnMap.get("param1"));
        List<String> expected = new ArrayList<String>();
        expected.add("ok");
        Assert.assertEquals(expected, returnMap.get("param2"));
    }


    @After
    public void tearDown() throws Exception {
        client.close();
        phpweb.interrupt();
    }
}