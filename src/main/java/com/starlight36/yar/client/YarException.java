package com.starlight36.yar.client;

import java.io.Serializable;

/**
 * YarException
 */
public class YarException extends RuntimeException implements Serializable {

    private Long code;

    private String file;

    private Integer line;

    public YarException(String message, Long code, String file, Integer line) {
        super(message);
        this.code = code;
        this.file = file;
        this.line = line;
    }

    public Long getCode() {
        return code;
    }

    public String getFile() {
        return file;
    }

    public Integer getLine() {
        return line;
    }
}
