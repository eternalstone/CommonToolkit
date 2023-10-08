package io.github.eternalstone.common.toolkit.web.xss;

import org.jsoup.Jsoup;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 10:14
 */
public interface XssCleaner {

    /**
     * 清理 html
     *
     * @param value 属性值
     * @return 清理后的数据
     */
    default String clean(String value) {
        return clean(null, value);
    }

    /**
     * 清理 html
     *
     * @param name  属性名
     * @param value 属性值
     * @return 清理后的数据
     */
    String clean(String name, String value);

    /**
     * 判断输入是否安全
     *
     * @param html html
     * @return 是否安全
     */
    default boolean isValid(String html) {
        return Jsoup.isValid(html, XssUtil.WHITE_LIST);
    }

}
