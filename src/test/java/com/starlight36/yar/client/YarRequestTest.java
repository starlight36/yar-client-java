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
public class YarRequestTest {

    private YarRequest request = new YarRequest("test", "json");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPackager() {
        request.setPackagerName("msgpack");
        Assert.assertEquals("msgpack", request.getPackagerName());
    }

    @Test
    public void testMethod() {
        request.setMethodName("msg");
        Assert.assertEquals("msg", request.getMethodName());
    }

    @After
    public void tearDown() throws Exception {
    }
}