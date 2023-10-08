package io.github.eternalstone.common.toolkit.enums;

import io.github.eternalstone.common.toolkit.model.ErrorCode;
import lombok.Getter;

/**
 * 全局异常枚举
 *
 * @author Justzone on 2023/9/22 11:38
 */
@Getter
public enum GlobalErrorCode implements ErrorCode {

    /**
     * 特殊业务级状态码
     */
    SUCCESS(200, "success"),
    NOT_LOGIN(401, "Not Login"),
    NOT_PERMISSION(403, "Permission denied"),

    /**
     * 常用业务级错误
     */
    SYSTEM_ERROR(100001, "系统异常"),
    ERROR_PARAM(100002, "参数错误"),
    OPERATE_FAIL(100003, "操作失败"),
    FEIGN_ERROR(100004, "调用失败"),
    AUTH_ERROR(100005, "数据权限错误"),
    SYSTEM_BUSY(100006, "系统繁忙"),
    SIGN_FAIL(100007, "加签失败"),
    OPEATE_REPEAT(100008, "重复操作"),
    REQUEST_MANY(100009, "请求频繁，请稍后再试"),
    FORMAT_ERROR(100011, "格式错误"),
    XSS_VALID_FAIL(100012, "Xss校验异常"),

    ;

    private int code;

    private String description;

    GlobalErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
