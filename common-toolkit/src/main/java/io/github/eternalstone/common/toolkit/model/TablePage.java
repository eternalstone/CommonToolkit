package io.github.eternalstone.common.toolkit.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 流式分页对象
 *
 * @author Justzone on 2023/9/18 15:43
 */
@ToString
@Data
public class TablePage<T> implements Serializable {

    private String cursor;

    private List<T> list;

    public TablePage() {

    }

    public TablePage(String cursor, List<T> list) {
        this.cursor = cursor;
        this.list = list;
    }

}
