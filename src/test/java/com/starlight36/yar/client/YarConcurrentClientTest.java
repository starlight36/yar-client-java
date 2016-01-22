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

    private YarConcurrentClient client;
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

        client = new YarConcurrentClient("http://127.0.0.1:8095/yar.php");
    }

    @Test
    public void testWithYarRequest() throws Exception {
        for (int j = 1; j < 100; j*=10) {

            final long starTime = System.currentTimeMillis();
            YarRequest req[] = new YarRequest[j];
            for (int i = 0; i < req.length; i++) {
                req[i] = new YarRequest("returnSimpleValue", "json");
            }


            YarCallback cb = new YarCallback<Integer>() {
                @Override
                public void completed(Integer var1) {
                    Assert.assertEquals(new Integer(1024), var1);
                    //System.out.println("complete" + (System.currentTimeMillis() - starTime));
                }

                @Override
                public void failed(Exception var1) {

                }

                @Override
                public void cancelled() {

                }
            };

            client.call(req, cb, Integer.class);
        }

        //Assert.assertEquals(Integer.valueOf(1024), returnValue);
    }


    @After
    public void tearDown() throws Exception {

    }
}