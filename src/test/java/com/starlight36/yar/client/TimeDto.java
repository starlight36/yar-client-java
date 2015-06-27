package com.starlight36.yar.client;

import java.io.Serializable;

/**
 * com.starlight36.yar.client.TimeDto
 */
public class TimeDto implements Serializable {

    private Integer time;

    private String id;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
