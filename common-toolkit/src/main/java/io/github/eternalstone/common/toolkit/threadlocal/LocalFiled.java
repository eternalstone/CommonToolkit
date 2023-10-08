package io.github.eternalstone.common.toolkit.threadlocal;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * to do something
 *
 * @author Justzone on 2023/10/7 11:09
 */
@ToString
@Data
public class LocalFiled implements Serializable {

    private Map<String, String> properties;

}
