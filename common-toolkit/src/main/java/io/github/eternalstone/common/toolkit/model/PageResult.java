package io.github.eternalstone.common.toolkit.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * to do something
 *
 * @author Justzone on 2023/9/22 10:59
 */
@ToString
@Data
public class PageResult<T> implements Serializable {

    /**
     * 页码
     */
    private int page;

    /**
     * 每页显示数
     */
    private int limit;

    /**
     * 总记录数
     */
    private long count;

    /**
     * 记录数据列表
     */
    private List<T> list;

    /**
     * 总页数
     */
    private int pages;


    public PageResult() {

    }

    public PageResult(int page, int limit, long count, List<T> list) {
        this.page = page;
        this.limit = limit;
        this.count = count;
        this.list = list;
        this.pages = calPages();
    }

    public PageResult resetData() {
        PageResult result = new PageResult();
        result.page = this.getPage();
        result.limit = this.getLimit();
        result.pages = this.getPages();
        result.count = this.getCount();
        return result;
    }

    public int calPages() {
        if (limit != 0) {
            return (int) ((this.count % this.limit == 0) ? this.count / this.limit : (this.count / this.limit) + 1);
        }
        return this.pages;
    }
}
