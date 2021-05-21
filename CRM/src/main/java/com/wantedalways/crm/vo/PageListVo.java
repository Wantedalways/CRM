package com.wantedalways.crm.vo;

import java.util.List;

// 用于分页查询的vo
public class PageListVo<T> {

    private Integer total;
    private List<T> dataList;

    public PageListVo() {
    }

    public PageListVo(Integer total, List<T> dataList) {
        this.total = total;
        this.dataList = dataList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
