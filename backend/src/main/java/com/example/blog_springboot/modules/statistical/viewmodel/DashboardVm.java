package com.example.blog_springboot.modules.statistical.viewmodel;

public class DashboardVm {
    private int totalSeries;
    private int totalTag;
    private int totalUser;
    private int totalAdmin;
    private int totalPostNoPublished;
    private int totalPostPublished;


    public int getTotalTag() {
        return totalTag;
    }

    public void setTotalTag(int totalTag) {
        this.totalTag = totalTag;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getTotalAdmin() {
        return totalAdmin;
    }

    public void setTotalAdmin(int totalAdmin) {
        this.totalAdmin = totalAdmin;
    }

    public int getTotalPostNoPublished() {
        return totalPostNoPublished;
    }

    public void setTotalPostNoPublished(int totalPostNoPublished) {
        this.totalPostNoPublished = totalPostNoPublished;
    }

    public int getTotalPostPublished() {
        return totalPostPublished;
    }

    public void setTotalPostPublished(int totalPostPublished) {
        this.totalPostPublished = totalPostPublished;
    }

    public int getTotalSeries() {
        return totalSeries;
    }

    public void setTotalSeries(int totalSeries) {
        this.totalSeries = totalSeries;
    }
}
