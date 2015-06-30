package com.starlight36.yar.client;

import java.io.IOException;

/**
 * com.starlight36.yar.client.Main
 */
public class Main {
    public static void main(String[] args) throws IOException {
        YarClient client = new YarClient("http://localhost/yar.php");
        DummyTimeDto time = client.call("doSomething", DummyTimeDto.class, "Hello");
        System.out.println(time.getId());
        System.out.println(time.getTime());
        client.close();
    }
}
