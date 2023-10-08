package io.github.eternalstone.common.toolkit.asserts;

import io.github.eternalstone.common.toolkit.enums.GlobalErrorCode;
import io.github.eternalstone.common.toolkit.exception.BizException;
import io.github.eternalstone.common.toolkit.model.ErrorCode;
import io.github.eternalstone.common.toolkit.util.ObjectUtils;

import java.util.Objects;

/**
 * 业务断言
 *
 * @author Justzone on 2023/9/22 11:41
 */
public class BizAssert {

    public static void notNull(Object obj, ErrorCode error) {
        isTrue(null != obj, error);
    }

    public static void isNull(Object obj, ErrorCode error) {
        isTrue(ObjectUtils.isEmpty(obj), error);
    }

    public static void isNull(Object obj, String msg) {
        asserts(true, ObjectUtils.isEmpty(obj), msg);
    }

    public static void isNotNull(Object obj, String msg) {
        asserts(false, ObjectUtils.isEmpty(obj), msg);
    }

    public static void isNotNull(Object obj, ErrorCode error) {
        isTrue(!ObjectUtils.isEmpty(obj), error);
    }

    public static void isTrue(Boolean bool, ErrorCode error) {
        if (!bool) {
            throw new BizException(error);
        }
    }

    public static void isTrue(Boolean bool, String msg) {
        if (!bool) {
            throw new BizException(GlobalErrorCode.SYSTEM_ERROR.getCode(), msg);
        }
    }

    public static void asserts(Object expect, Object actual, String msg) {
        if (!Objects.equals(expect, actual)) {
            throw new BizException(GlobalErrorCode.SYSTEM_ERROR.getCode(), msg);
        }
    }

    public static void asserts(Object expect, Object actual, ErrorCode error) {
        if (!Objects.equals(expect, actual)) {
            throw new BizException(error);
        }
    }

    public static void isFalse(Object expect, Object actual, ErrorCode error) {
        if (Objects.equals(expect, actual)) {
            throw new BizException(error);
        }
    }

}
