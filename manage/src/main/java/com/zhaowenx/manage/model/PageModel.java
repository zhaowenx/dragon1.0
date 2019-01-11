package com.zhaowenx.manage.model;

import java.util.List;

/**
 * Created by zhaowenx on 2018/8/23.
 */
public class PageModel<T> {
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 每页显示条数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 总记录数
     */
    private int totalRecord;
    /**
     * 分页数据
     */
    private List<T> dataList;

    public PageModel(int currentPage,int pageSize,int totalRecord,List<T> dataList){
        setCurrentPage(currentPage);
        this.pageSize=pageSize;
        this.totalRecord = totalRecord;
        this.setDataList(dataList);
    }

    /**
     * 设置当前请求页
     *
     * @param page
     */
    private void setCurrentPage(String page) {
        try {
            currentPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            // 这里异常不做处理，当前页默认为1
            currentPage = 1;
        }
        // 如果当前页小于第一页时，当前页指定到首页
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }

    }

    private void setTotalPage() {
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
        if (totalPage == 0) {
            totalPage = 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * 获得当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 获得总页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 获得开始行数
     */
    public int getStartRow() {
        return (currentPage - 1) * pageSize;
    }

    /**
     * 获得结束行
     */
    public int getEndRow() {
        return currentPage * pageSize;
    }

    /**
     * 获得翻页数据
     */
    public List<T> getDataList() {
        return dataList;
    }

    /**
     * 设置翻页数据
     * @param dataList
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    /**
     * 首页
     *
     * @return
     */
    public int getFirst() {
        return 1;
    }

    /**
     * 上一页
     *
     * @return
     */
    public int getPrevious() {
        if (currentPage == 1) {
            return currentPage;
        }
        return currentPage - 1;
    }

    /**
     * 下一页
     *
     * @return
     */
    public int getNext() {
        if (currentPage == totalPage) {
            return currentPage;
        }
        return currentPage + 1;
    }

    /**
     * 尾页
     *
     * @return
     */
    public int getLast() {
        return totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalRecord=" + totalRecord +
                ", dataList=" + dataList +
                '}';
    }
}
