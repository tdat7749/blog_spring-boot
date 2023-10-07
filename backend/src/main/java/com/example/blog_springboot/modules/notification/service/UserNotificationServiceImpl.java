package com.example.blog_springboot.modules.notification.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.modules.notification.constant.NotificationConstants;
import com.example.blog_springboot.modules.notification.exception.CreateUserNotificationException;
import com.example.blog_springboot.modules.notification.exception.NotificationNotFoundException;
import com.example.blog_springboot.modules.notification.exception.ReadNotificationException;
import com.example.blog_springboot.modules.notification.model.Notification;
import com.example.blog_springboot.modules.notification.model.UserNotification;
import com.example.blog_springboot.modules.notification.repository.UserNotificationRepository;
import com.example.blog_springboot.modules.notification.viewmodel.NotificationVm;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.utils.Utilities;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserNotificationServiceImpl implements UserNotificationService{

    private final UserNotificationRepository userNotificationRepository;
    public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository){
        this.userNotificationRepository = userNotificationRepository;
    }
    @Override
    @Transactional
    public boolean createUserNotification(List<User> listUser, Notification notification) {
        List<UserNotification> listUserNotification = new ArrayList<>();
        for(User user : listUser){
            UserNotification userNotification = new UserNotification();
            userNotification.setUser(user);
            userNotification.setNotification(notification);
            userNotification.setRead(false);
            listUserNotification.add(userNotification);
        }

        var save = userNotificationRepository.saveAll(listUserNotification);
        if(save == null){
            throw new CreateUserNotificationException(NotificationConstants.CREATE_NOTIFICATION_FAILED);
        }
        return true;
    }

    @Override
    public SuccessResponse<List<NotificationVm>> getTop10NotificationCurrentUser(User user) {
        var listNotification = userNotificationRepository.getTop10NotificationCurrentUser(user, PageRequest.of(0,10));

        List<NotificationVm> listNoti = listNotification.stream().map(Utilities::getNotificationVm).toList();

        return new SuccessResponse<>("Thành công",listNoti);
    }

    @Override
    public SuccessResponse<PagingResponse<List<NotificationVm>>> getNotificationCurrentUser(int pageIndex,User user) {
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE);

        var pagingResult = userNotificationRepository.getNotificationCurrentUser(user,paging);

        List<NotificationVm> listNoti = pagingResult.stream().map(Utilities::getNotificationVm).toList();



        return new SuccessResponse<>("Thành công",new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listNoti));
    }

    @Override
    public SuccessResponse<Boolean> readNotification(int id, User user) {
        var isFoundNotification = userNotificationRepository.findByUserAndId(user,id).orElse(null);
        if(isFoundNotification == null){
            throw new NotificationNotFoundException(NotificationConstants.NOTIFICATION_NOT_FOUND);
        }

        isFoundNotification.setRead(true);

        var save = userNotificationRepository.save(isFoundNotification);
        if(save == null){
            throw new ReadNotificationException(NotificationConstants.CANNOT_SET_READ_NOTIFICATION);
        }

        return new SuccessResponse<>(NotificationConstants.READ_NOTIFICATION_SUCCESS,true);
    }

    @Override
    @Transactional
    public SuccessResponse<Boolean> readAllNotification(User user) {
        userNotificationRepository.readAllNotification(user);

        return new SuccessResponse<>(NotificationConstants.READ_NOTIFICATION_SUCCESS,true);
    }


}
