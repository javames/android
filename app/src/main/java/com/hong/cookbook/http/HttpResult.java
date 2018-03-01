package com.hong.cookbook.http;

/**
 * Created by Administrator on 2018/2/11.
 */

public class HttpResult<Object>{

    private String retCode;
    private String msg;
    private  Object result;


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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
