package com.starlight36.yar.client;

import java.util.ArrayList;
import java.util.List;

/**
 * DummyResultDto
 */
public class DummyResultDto {

    private List<DummyUserDto> list = new ArrayList<DummyUserDto>();

    public List<DummyUserDto> getList() {
        return list;
    }

    public void setList(List<DummyUserDto> list) {
        this.list = list;
    }
}
