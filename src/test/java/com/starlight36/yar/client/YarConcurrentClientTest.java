package com.starlight36.yar.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * YarClientTest
 */
public class YarConcurrentClientTest {

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

    @Test
    public void testWithYarRequest() throws IOException {

        Integer returnValue = client.call(new YarRequest("returnSimpleValue", "json"), Integer.class);
        Assert.assertEquals(Integer.valueOf(1024), returnValue);
    }


    @After
    public void tearDown() throws Exception {
        client.close();
        phpweb.interrupt();
    }
}