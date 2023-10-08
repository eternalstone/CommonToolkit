package io.github.eternalstone.common.toolkit.exception;

import io.github.eternalstone.common.toolkit.model.ErrorCode;
import io.github.eternalstone.common.toolkit.model.WebApiRes;
import lombok.Data;

/**
 * 业务异常
 *
 * @author Justzone on 2023/9/18 15:50
 */
@Data
public class BizException extends RuntimeException {

    private Integer code;

    private String message;

    private ErrorCode errorCode;

    private Object errorData;

    public BizException() {
        super();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Deprecated
    public BizException(String message) {
        super(message);
    }

    @Deprecated
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    @Deprecated
    public BizException(Throwable cause) {
        super(cause);
    }

    @Deprecated
    protected BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizException(ErrorCode errorCode) {
        super(String.format("code: %s, description: %s", errorCode.getCode(), errorCode.getDescription()));
        this.errorCode = errorCode;
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public BizException(ErrorCode errorCode, Object errorData) {
        super(String.format("code: %s, description: %s ,errorData: %s", errorCode.getCode(), errorCode.getDescription(), errorData.toString()));
        this.errorCode = errorCode;
        this.errorData = errorData;
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public BizException(Integer code, String message) {
        super(String.format("code: %s, errorMsg: %s", code, message));
        this.message = message;
        this.code = code;
    }

    public BizException(Integer code, String message, Object errorData) {
        super(String.format("code: %s, description: %s ,errorData: %s", code, message, errorData.toString()));
        this.code = code;
        this.message = message;
        this.errorData = errorData;
    }

    public BizException(String errorMsg, Integer code, String message) {
        super(errorMsg);
        this.message = message;
        this.code = code;
    }

    public BizException(WebApiRes webApiRes) {
        this.code = webApiRes.getCode();
        this.message = webApiRes.getMessage();
        this.errorData = webApiRes.getErrorData();
    }
}
