package io.github.eternalstone.common.toolkit.model;

import io.github.eternalstone.common.toolkit.enums.GlobalErrorCode;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 包装统一返回对象
 *
 * @author Justzone on 2023/9/18 15:25
 */
@ToString
@Data
public class WebApiRes<T> implements Serializable {

    /**
     * 请求处理是否成功
     */
    private boolean success;

    /**
     * 业务错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误对象
     */
    private Object errorData;

    /**
     * 业务正常数据对象
     */
    private T data;

    public WebApiRes() {

    }

    public static <T> WebApiRes<T> success(T data) {
        WebApiRes<T> webApiRes = new WebApiRes();
        webApiRes.setCode(GlobalErrorCode.SUCCESS.getCode());
        webApiRes.setSuccess(true);
        webApiRes.setData(data);
        return webApiRes;
    }

    public static <T> WebApiRes<T> success() {
        WebApiRes<T> webApiRes = new WebApiRes();
        webApiRes.setCode(GlobalErrorCode.SUCCESS.getCode());
        webApiRes.setSuccess(true);
        webApiRes.setData(null);
        return webApiRes;
    }

    public static <T> WebApiRes<T> failure(ErrorCode errorCode) {
        return failure(errorCode.getCode(), errorCode.getDescription());
    }

    public static <T> WebApiRes<T> failure(ErrorCode errorCode, Object errorData) {
        return failure(errorCode.getCode(), errorCode.getDescription(), errorData);
    }

    public static <T> WebApiRes<T> failure(int code, String message) {
        WebApiRes<T> webApiRes = new WebApiRes();
        webApiRes.setSuccess(false);
        webApiRes.setCode(code);
        webApiRes.setMessage(message);
        return webApiRes;
    }

    public static <T> WebApiRes<T> failure(int code, String message, Object errorData) {
        WebApiRes<T> webApiRes = new WebApiRes();
        webApiRes.setSuccess(false);
        webApiRes.setErrorData(errorData);
        webApiRes.setCode(code);
        webApiRes.setMessage(message);
        return webApiRes;
    }
}
