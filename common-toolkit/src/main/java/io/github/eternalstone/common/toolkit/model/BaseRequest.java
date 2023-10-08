package io.github.eternalstone.common.toolkit.model;

import lombok.Data;

import java.io.Serializable;

/**
 * to do something
 *
 * @author Justzone on 2023/9/18 15:46
 */
@Data
public class BaseRequest implements Serializable {

    /**
     * 当前用户ID
     */
    private Long userId;

    /**
     * 平台ID
     */
    private Integer platformId;

    /**
     * 设备类性
     */
    private Integer device;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * ip
     */
    private String ip;

    /**
     * 语言
     */
    private String language;

    /**
     * app
     */
    private String appVersion;

    /**
     * 渠道
     */
    private String appChannel;


}
