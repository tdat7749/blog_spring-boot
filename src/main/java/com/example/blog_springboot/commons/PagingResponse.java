package com.example.blog_springboot.commons;

public class PagingResponse<T> {
    private int totalPage;
    private int totalRecord;
    private T data;

    public PagingResponse(int totalPage, int totalRecord, T data) {
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
