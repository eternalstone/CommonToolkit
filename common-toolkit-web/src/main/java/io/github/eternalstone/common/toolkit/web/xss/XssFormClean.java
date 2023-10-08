package io.github.eternalstone.common.toolkit.web.xss;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 9:26
 */
@ControllerAdvice
@RequiredArgsConstructor
public class XssFormClean {

    private final XssProperties xssProperties;
    private final XssCleaner xssCleaner;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 处理前端传来的表单字符串
        binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssCleaner, xssProperties));
    }

    @Slf4j
    @RequiredArgsConstructor
    public static class StringPropertiesEditor extends PropertyEditorSupport {
        private final XssCleaner xssCleaner;
        private final XssProperties xssProperties;

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            if (text == null) {
                setValue(null);
            } else if (XssHolder.isEnabled()) {
                String value = xssCleaner.clean(XssUtil.trim(text, xssProperties.isTrim()));
                setValue(value);
                log.debug("Request parameter value:{} cleaned up by xss-defence, current value is:{}.", text, value);
            } else {
                setValue(XssUtil.trim(text, xssProperties.isTrim()));
            }
        }
    }

}
