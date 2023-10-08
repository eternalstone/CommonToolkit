package io.github.eternalstone.common.toolkit.web.cors;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * to do something
 *
 * @author Justzone on 2023/10/8 16:57
 */
@Data
public class CorsOriginProperties implements Serializable {

    private List<String> origins;

    private String allowedHeaders;

    private String allowedMethods;

    private long maxAge;

    private boolean credentials;

}
