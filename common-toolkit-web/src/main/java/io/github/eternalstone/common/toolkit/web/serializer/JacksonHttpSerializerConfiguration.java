package io.github.eternalstone.common.toolkit.web.serializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 16:08
 */
@Configuration
@RequiredArgsConstructor
public class JacksonHttpSerializerConfiguration {

    private final ObjectMapper jacksonObjectMapper;

    @PostConstruct
    public void init() {
        jacksonObjectMapper
                // 反序列化设置 关闭反序列化时Jackson发现无法找到对应的对象字段，便会抛出UnrecognizedPropertyException: Unrecognized field xxx异常
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 序列化设置 关闭日志输出为时间戳的设置
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 返回所有字段
                .setSerializationInclusion(JsonInclude.Include.ALWAYS);
        jacksonObjectMapper.setSerializerFactory(jacksonObjectMapper.getSerializerFactory().withSerializerModifier(new JacksonHttpSerializer()));
    }


}
