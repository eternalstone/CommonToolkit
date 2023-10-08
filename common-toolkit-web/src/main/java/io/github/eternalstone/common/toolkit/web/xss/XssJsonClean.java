package io.github.eternalstone.common.toolkit.web.xss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 9:26
 */
@Slf4j
@RequiredArgsConstructor
public class XssJsonClean extends AbstractXssCleanDeserializer {

    private final XssProperties xssProperties;
    private final XssCleaner xssCleaner;

    @Override
    public String clean(String name, String text) throws IOException {
        if (text == null) {
            return null;
        }
        // 判断是否忽略
        if (XssHolder.isIgnore(name)) {
            return XssUtil.trim(text, xssProperties.isTrim());
        }
        String value = xssCleaner.clean(name, XssUtil.trim(text, xssProperties.isTrim()));
        log.debug("Json property name:{} value:{} cleaned up by xss-defence, current value is:{}.", name, text, value);
        return value;
    }

}
