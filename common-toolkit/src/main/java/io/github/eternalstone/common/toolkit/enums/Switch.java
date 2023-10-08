package io.github.eternalstone.common.toolkit.enums;

import lombok.Getter;

/**
 * 开关枚举(一般性套用，启用禁用， 未删已删)
 *
 * @author Justzone on 2023/9/22 11:32
 */
@Getter
public enum Switch {

    ENABLE(0, "开启"),

    DISABLE(1, "禁用");

    /**
     * 状态值
     */
    private Integer value;
    /**
     * 状态名
     */
    private String name;

    Switch(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static boolean isOpen(Integer code) {
        if (code == null) {
            return false;
        }
        return ENABLE.value.equals(code);
    }

}
