package com.htxtdshopping.htxtd.frame.network;

import java.io.Serializable;

/**
 * @author 陈志鹏
 * @date 2018/7/27
 */
public class SimpleResponse implements Serializable {
    private static final long serialVersionUID = -6236349304567980273L;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseResponse toBaseResponse() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMsg(msg);
        return baseResponse;
    }
}
