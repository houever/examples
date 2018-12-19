package com.july.guava.model;

import com.sun.corba.se.spi.orb.ParserImplBase;

public class Result {

    private Integer code;
    private String message;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success(){
        return new Result(500,"操作成功");
    }

}
