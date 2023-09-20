package com.example.blog_springboot.modules.user.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.PagingResponse;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.user.constant.UserConstants;
import com.example.blog_springboot.modules.user.dto.ChangeInformationDTO;
import com.example.blog_springboot.modules.user.dto.ChangePasswordDTO;
import com.example.blog_springboot.modules.user.exception.ChangePasswordException;
import com.example.blog_springboot.modules.user.exception.PasswordIncorrectException;
import com.example.blog_springboot.modules.user.exception.PasswordNotMatchException;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import com.example.blog_springboot.modules.user.viewmodel.UserVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SuccessResponse<Boolean> changePassword(ChangePasswordDTO dto, User userPrincipal) {
        if(!passwordEncoder.matches(dto.getOldPassword(),userPrincipal.getPassword())){
            throw new PasswordIncorrectException(UserConstants.PASSWORD_INCORRECT);
        }
        if(!dto.getNewPassword().equals(dto.getConfirmPassword())){
            throw new PasswordNotMatchException(UserConstants.PASSWORD_NOT_MATCH);
        }

        userPrincipal.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        var save = userRepository.save(userPrincipal);
        if(save == null){
            throw new ChangePasswordException(UserConstants.CHANGE_PASSWORD_FAILED);
        }

        return new SuccessResponse<>(UserConstants.CHANGE_PASSWORD_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> changeAvatar(String avatar, User userPrincipal) {
        userPrincipal.setAvatar(avatar);
        var save = userRepository.save(userPrincipal);
        if(save == null){
            throw new ChangePasswordException(UserConstants.CHANGE_AVATAR_FAILED);
        }

        return new SuccessResponse<>(UserConstants.CHANGE_AVATAR_SUCCESS,true);
    }

    @Override
    public SuccessResponse<UserVm> changeInformation(ChangeInformationDTO dto, User userPrincipal) {
        userPrincipal.setFirstName(dto.getFirstName());
        userPrincipal.setFirstName(dto.getLastName());

        // Nếu còn nhiều thng tin muốn sửa thì add ở trên;

        var save = userRepository.save(userPrincipal);

        if(save == null){
            throw new ChangePasswordException(UserConstants.CHANGE_INFORMATION_FAILED);
        }

        var userVm = getUserVm(save);

        return new SuccessResponse<>(UserConstants.CHANGE_INFORMATION_SUCCESS,userVm);

    }

    @Override
    public SuccessResponse<PagingResponse<List<UserVm>>> getListFollowing(String sortBy,int pageIndex,int userId){
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        Page<User> pagingResult = userRepository.getAllUserFollowing(userId,paging);

        List<UserVm> listUserVm = pagingResult.getContent().stream().map(this::getUserVm).toList();

        var pagingResponse = new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listUserVm);

        return new SuccessResponse<>("Thành công",pagingResponse);
    }

    @Override
    public SuccessResponse<PagingResponse<List<UserVm>>> getListFollowers(String sortBy,int pageIndex,int userId){
        Pageable paging = PageRequest.of(pageIndex, Constants.PAGE_SIZE, Sort.by(sortBy));

        Page<User> pagingResult = userRepository.getAllUserFollower(userId,paging);

        List<UserVm> listUserVm = pagingResult.getContent().stream().map(this::getUserVm).toList();

        var pagingResponse = new PagingResponse<>(pagingResult.getTotalPages(),(int)pagingResult.getTotalElements(),listUserVm);

        return new SuccessResponse<>("Thành công",pagingResponse);
    }

    private UserVm getUserVm(User user){
        UserVm userVm = new UserVm();
        userVm.setAvatar(user.getAvatar());
        userVm.setFirstName(user.getFirstName());
        userVm.setLastName(user.getLastName());
        userVm.setEmail(user.getEmail());
        userVm.setNotLocked(user.isAccountNonLocked());
        userVm.setRole(user.getRole().toString());
        userVm.setUserName(user.getUsername());
        userVm.setId(user.getId());

        return userVm;
    }
}
