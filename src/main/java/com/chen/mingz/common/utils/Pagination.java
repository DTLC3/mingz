package com.chen.mingz.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by chenmingzhi on 18/2/27.
 */

@ApiModel(value = "Pagination", description = "分页查询对象")
public class Pagination {

    @ApiModelProperty(name = "start", dataType = "Integer", example = "0", value = "起始行数", hidden = true)
    private Integer start;

    @ApiModelProperty(name = "length", dataType = "Integer", example = "10", value = "每一页长度(兼容老接口已弃用)", hidden = true)
    private Integer length;

    @ApiModelProperty(name = "last", dataType = "Integer", example = "10", value = "终止行数", hidden = true)
    private Integer last;

    @ApiModelProperty(name = "rows", dataType = "Integer", example = "1", value = "每一页长度")
    private Integer rows;

    @ApiModelProperty(name = "page", dataType = "Integer", example = "1", value = "第几页")
    private Integer page;

    public Pagination() {
        this.start = 0;
        this.length = 10;
        this.page = 1;
        this.rows = 10;
        this.last = 10;
    }

    public Pagination(Integer page, Integer rows) {
        rows = (rows == null || rows < 0) ? 10 : rows;
        page = (page == null || page < 0) ? 1 : page;
        start = (page - 1) * rows;
        length = rows;
        last = start + length;
    }

    public void setStartLength() {
        rows = (rows == null || rows < 0) ? 10 : rows;
        page = (page == null || page < 0) ? 1 : page;
        start = (page - 1) * rows;
        length = rows;
        last = start + length;
    }


    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagination)) return false;

        Pagination that = (Pagination) o;

        if (!start.equals(that.start)) return false;
        if (!length.equals(that.length)) return false;
        return last.equals(that.last);
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + length.hashCode();
        result = 31 * result + last.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "start=" + start +
                ", length=" + length +
                ", last=" + last +
                '}';
    }
}
