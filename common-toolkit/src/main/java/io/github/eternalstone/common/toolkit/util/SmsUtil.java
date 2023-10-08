package io.github.eternalstone.common.toolkit.util;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.eternalstone.common.toolkit.enums.GlobalErrorCode;
import io.github.eternalstone.common.toolkit.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * to do something
 *
 * @author Justzone on 2021/1/20 11:25
 */
@Slf4j
public class SmsUtil {

    /**
     * 组装短信内容
     */
    public static String makeupContent(String orginContent, String paramsJson) {
        try {
            JsonNode jsonNode = JsonUtil.getObjectMapper().readTree(paramsJson);
            Pattern pattern = Pattern.compile(RegexUtil.REGEX_BIG_RRACE);
            Matcher matcher = pattern.matcher(orginContent);
            while (matcher.find()) {
                String key = matcher.group();
                if (jsonNode == null || jsonNode.get(key) == null) {
                    throw new BizException(GlobalErrorCode.FORMAT_ERROR);
                }
                Object obj = jsonNode.get(key);
                orginContent = orginContent.replaceAll("\\{" + key + "\\}", obj.toString());
            }
            return orginContent;
        } catch (Exception e) {
            log.error("短信组装未知错误", e);
            throw new BizException(GlobalErrorCode.FORMAT_ERROR);
        }
    }

}
