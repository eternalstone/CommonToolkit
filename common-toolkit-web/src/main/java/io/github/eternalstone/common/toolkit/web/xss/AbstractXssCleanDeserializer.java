package io.github.eternalstone.common.toolkit.web.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.IOException;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 10:39
 */
public abstract class AbstractXssCleanDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        // json 字段名
        String name = p.getCurrentName();
        // 字符串类型
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            String text = p.getText();
            if (text == null) {
                return null;
            }
            return clean(name, text);
        }
        JsonToken jsonToken = p.getCurrentToken();
        if (jsonToken.isScalarValue()) {
            String text = p.getValueAsString();
            if (text != null) {
                return text;
            }
        }
        throw MismatchedInputException.from(p, String.class, "xss-defence: can't deserialize json name:" + name + " value of type java.lang.String from " + jsonToken);
    }

    /**
     * 清理 xss
     *
     * @param name  json name
     * @param value json value
     * @return String
     */
    public abstract String clean(String name, String value) throws IOException;

}
