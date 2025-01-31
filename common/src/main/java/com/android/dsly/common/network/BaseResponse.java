package com.android.dsly.common.network;

import java.io.Serializable;

/**
 * 根据接口数据自定义BaseResponse
 * @author dsly
 * @date 2018/7/27
 */
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 8812426608926628614L;

    private int code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
