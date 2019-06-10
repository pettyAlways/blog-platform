package org.yingzuidou.cms.cmsweb.core.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页实体类
 *
 * @author yingzuidou
 * @date 2018/9/16     
 */
public class PageInfo {

    /**
     *  当前页数
     */
    private int page;

    /**
     * 页的大小(默认显示10条)
     */
    private int size = 10;

    /**
     * 总数
     */
    private long counts;

    public Pageable toPageable() {
       // 注意这里不要写成create_time 不然会被拆分两个属性
       Sort sort = new Sort(Sort.Direction.DESC,"createTime");
       return PageRequest.of(page - 1, size, sort);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getCounts() {
        return counts;
    }

    public void setCounts(long counts) {
        this.counts = counts;
    }
}
