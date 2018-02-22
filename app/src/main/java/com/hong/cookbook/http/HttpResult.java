package com.hong.cookbook.http;

/**
 * Created by Administrator on 2018/2/11.
 */

public class HttpResult<T>{

    private String retCode;
    private String msg;
    private  T result;


    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
