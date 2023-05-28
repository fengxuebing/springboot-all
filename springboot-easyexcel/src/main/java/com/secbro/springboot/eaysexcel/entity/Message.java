package com.secbro.springboot.eaysexcel.entity;


import lombok.Data;

@Data
public class Message {

    public static final String OK ="ok";
    public static String ERROR="error";
    private String type;
    private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
