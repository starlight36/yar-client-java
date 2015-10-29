package com.starlight36.yar.client;

/**
 * Created by sunan on 10/29/15.
 */
public interface YarCallback<T> {
    void completed(T var1);

    void failed(Exception var1);

    void cancelled();
}
