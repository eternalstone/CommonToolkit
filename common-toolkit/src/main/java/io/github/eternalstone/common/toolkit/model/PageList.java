package io.github.eternalstone.common.toolkit.model;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author Justzone on 2023/9/18 15:33
 */
@ToString
@Data
public class PageList<T, K> implements Serializable {

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

    public PageList() {

    }

    public PageList(int page, int limit, long count, List<T> list) {
        this.page = page;
        this.limit = limit;
        this.count = count;
        this.list = list;
        this.pages = calPages();
    }

    public PageList<K, T> resetData() {
        PageList<K, T> pageList = new PageList();
        pageList.page = this.getPage();
        pageList.limit = this.getLimit();
        pageList.pages = this.getPages();
        pageList.count = this.getCount();
        List<K> convert = this.convert(this.getList());
        pageList.list = convert;
        return pageList;
    }

    public int calPages() {
        if (limit != 0) {
            return (int) ((this.count % this.limit == 0) ? this.count / this.limit : (this.count / this.limit) + 1);
        }
        return this.pages;
    }

    public PageResult<T> getPageResult(){
        return new PageResult(this.page, this.limit, this.count, this.getList());
    }


    public List<K> convert(List<T> data) {
        return null;
    }

}
