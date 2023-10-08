package io.github.eternalstone.common.toolkit.web.xss;

import io.github.eternalstone.common.toolkit.enums.GlobalErrorCode;
import io.github.eternalstone.common.toolkit.exception.BizException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.nio.charset.StandardCharsets;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 10:25
 */
public class DefaultXssCleaner implements XssCleaner {

    private XssProperties xssProperties;

    public DefaultXssCleaner(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public String clean(String name, String html) {
        // 1. 为空直接返回
        if (!StringUtils.hasText(html)) {
            return html;
        }
        XssMode mode = xssProperties.getMode();
        if (XssMode.ESCAPE == mode) {
            // html 转义
            return HtmlUtils.htmlEscape(html, StandardCharsets.UTF_8.name());
        } else if (XssMode.VALIDATE == mode) {
            // 校验
            if (Jsoup.isValid(html, XssUtil.WHITE_LIST)) {
                return html;
            }
            throw new BizException(GlobalErrorCode.XSS_VALID_FAIL, html);
        } else {
            // 4. 清理后的 html
            String escapedHtml = Jsoup.clean(html, "", XssUtil.WHITE_LIST, getOutputSettings(xssProperties));
            if (xssProperties.isEnableEscape()) {
                return escapedHtml;
            }
            // 5. 反转义
            return Entities.unescape(escapedHtml);
        }
    }

    private static Document.OutputSettings getOutputSettings(XssProperties xssProperties) {
        return new Document.OutputSettings()
                // 2. 转义，没找到关闭的方法，目前这个规则最少
                .escapeMode(Entities.EscapeMode.xhtml)
                // 3. 保留换行
                .prettyPrint(xssProperties.isPrettyPrint());
    }
}
