package com.htxtdshopping.htxtd.frame.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.convert.Converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author 陈志鹏
 * @date 2018/7/27
 */
public class JsonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JsonConvert() {
    }

    public JsonConvert(Type type) {
        this.type = type;
    }

    public JsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            return null;
        }

        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<T>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<T> clazz) throws Exception {
        if (clazz == null) {
            return null;
        }
        ResponseBody body = response.body();

        if (clazz == String.class) {
            return (T) body.string();
        } else if (clazz == JSONObject.class) {
            return (T) JSON.parseObject(body.string());
        } else if (clazz == JSONArray.class) {
            return (T) JSON.parseArray(body.string());
        } else {
            T t = JSON.parseObject(body.string(), clazz);
            response.close();
            return t;
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) {
            return null;
        }
        ResponseBody body = response.body();
        T t = JSON.parseObject(body.string(), type);
        response.close();
        return t;
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) {
            return null;
        }
        ResponseBody body = response.body();
        // 泛型的实际类型
        Type rawType = type.getRawType();
        // 泛型的参数
        Type typeArgument = type.getActualTypeArguments()[0];
        if (rawType != BaseResponse.class) {
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            T t = JSON.parseObject(body.string(), type);
            response.close();
            return t;
        } else {
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<BaseResponse<Void>>(this)
                SimpleResponse simpleResponse = JSON.parseObject(body.string(), SimpleResponse.class);
                response.close();
                return (T) simpleResponse.toBaseResponse();
            } else {
                // 泛型格式如下： new JsonCallback<BaseResponse<内层JavaBean>>(this)
                BaseResponse baseResponse = JSON.parseObject(body.string(), type);
                response.close();
                int code = baseResponse.getCode();
                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (code == 0) {
                    return (T) baseResponse;
                } else if (code == 104) {
                    throw new IllegalStateException("用户授权信息无效");
                } else if (code == 105) {
                    throw new IllegalStateException("用户收取信息已过期");
                } else {
                    //直接将服务端的错误信息抛出，onError中可以获取
                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + baseResponse.getMsg());
                }
            }
        }
    }
}
