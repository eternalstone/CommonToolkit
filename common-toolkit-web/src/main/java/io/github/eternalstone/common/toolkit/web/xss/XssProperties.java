package io.github.eternalstone.common.toolkit.web.xss;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 17:18
 */
@Data
public class XssProperties {

    /**
     * 全局：开关
     */
    private boolean enabled = true;

    /**
     * 全局：对文件进行首尾 trim
     */
    private boolean trim = true;
    /**
     * 模式：clear 清理（默认），escape 转义
     */
    private XssMode mode = XssMode.CLEAR;
    /**
     * [clear 专用] prettyPrint，默认关闭： 保留换行
     */
    private boolean prettyPrint = false;
    /**
     * [clear 专用] 使用转义，默认关闭
     */
    private boolean enableEscape = false;
    /**
     * 拦截的路由，默认为空
     */
    private List<String> pathPatterns = new ArrayList<>();
    /**
     * 放行的路由，默认为空
     */
    private List<String> pathExcludePatterns = new ArrayList<>();

}
