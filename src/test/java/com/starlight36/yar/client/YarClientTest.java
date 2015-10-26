package com.starlight36.yar.client;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * YarClientTest
 */
public class YarClientTest {

    private YarClient client;

    @Before
    public void setUp() throws Exception {
        client = new YarClient("http://127.0.0.1:8095/yar.php");
    }

    @Test
    public void testReturnSimpleValue() throws IOException {
        Integer returnValue = client.call("returnSimpleValue", Integer.class);
        Assert.assertEquals(Integer.valueOf(1024), returnValue);
    }
    
    @Test
    public void testParams() throws IOException {
        Integer returnValue = client.call("testParams", Integer.class, 100);
        Assert.assertEquals(Integer.valueOf(100), returnValue);
    }

    @Test
    public void testReturnArray() throws IOException {
        List<Integer> returnValue = client.call("returnArray", List.class);
        Assert.assertArrayEquals(new Integer[]{1, 2, 3}, returnValue.toArray());
    }

    @Test
    public void testReturnMixed() throws IOException {
        DummyTimeDto timeDto = client.call("returnMixed", DummyTimeDto.class);
        Assert.assertNotNull(timeDto);
        Assert.assertEquals(Integer.valueOf((int) (new Date().getTime() / 1000)), timeDto.getTime());
        Assert.assertNotNull(timeDto.getId());
    }

    @Test
    public void testReturnComplexObject() throws IOException {
        DummyResultDto resultDto = client.call("returnComplexObject", DummyResultDto.class);
        Assert.assertNotNull(resultDto);
        List<DummyUserDto> list = resultDto.getList();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        DummyUserDto userDto = list.get(0);
        Assert.assertEquals(1, userDto.getId());
        Assert.assertEquals("user1", userDto.getName());
    }

    @Test
    public void testEchoRequest() throws IOException {
        Map<String, String> returnMap = client.call("echoRequest", Map.class, "foo", "bar");
        Assert.assertNotNull(returnMap);
        Assert.assertEquals("foo", returnMap.get("param1"));
        Assert.assertEquals("bar", returnMap.get("param2"));
    }

    @Test
    public void testThrowException() throws IOException {
        try {
            client.call("throwException", Object.class);
            Assert.fail();
        } catch (YarException ex) {
            Assert.assertEquals("Hello world!", ex.getMessage());
        }
    }

    @Test
    public void testResponseTimeout() {
        try {
            client.call("responseTimeout", Object.class);
            Assert.fail();
        } catch (IOException ex) {
            Assert.assertTrue(ex.getMessage().contains("Read timed out"));
        }
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }
}