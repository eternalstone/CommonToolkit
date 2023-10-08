package io.github.eternalstone.common.toolkit.exception;

import io.github.eternalstone.common.toolkit.model.ErrorCode;
import lombok.Data;

/**
 * 全局异常
 *
 * @author Justzone on 2023/9/18 15:50
 */
@Data
public class GlobalException extends RuntimeException {

    private Integer code;

    public GlobalException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.code = errorCode.getCode();
    }

}
