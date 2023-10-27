package com.example.blog_springboot.modules.notification.viewmodel;

import java.util.List;

public class RpNotificationVm {
    private int unSeenNotification;
    private List<NotificationVm> notifications;

    public int getUnSeenNotification() {
        return unSeenNotification;
    }

    public void setUnSeenNotification(int unSeenNotification) {
        this.unSeenNotification = unSeenNotification;
    }

    public List<NotificationVm> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationVm> notifications) {
        this.notifications = notifications;
    }
}
