package com.example.blog_springboot.modules.authenticate.service;

import com.example.blog_springboot.commons.Constants;
import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.authenticate.constant.AuthConstants;
import com.example.blog_springboot.modules.authenticate.dto.LoginDTO;
import com.example.blog_springboot.modules.authenticate.dto.RegisterDTO;
import com.example.blog_springboot.modules.authenticate.dto.ResendMailDTO;
import com.example.blog_springboot.modules.authenticate.dto.VerifyDTO;
import com.example.blog_springboot.modules.authenticate.exception.*;
import com.example.blog_springboot.modules.authenticate.viewmodel.AuthenVm;
import com.example.blog_springboot.modules.jwt.service.JwtService;
import com.example.blog_springboot.modules.mail.service.MailService;
import com.example.blog_springboot.modules.user.constant.UserConstants;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
import com.example.blog_springboot.utils.Utilities;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService{

    final AuthenticationManager authenticationManager;
    final private UserRepository userRepository;
    final private JwtService jwtService;

    final private PasswordEncoder passwordEncoder;

    final private MailService mailService;



    public AuthServiceImpl(AuthenticationManager authenticationManager,JwtService jwtService,UserRepository userRepository,PasswordEncoder passwordEncoder,MailService mailService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public SuccessResponse<AuthenVm> login(LoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
           dto.getUserName(),
           dto.getPassword()
        ));

        var user = userRepository.findByUserName(dto.getUserName()).orElse(null);
        if(user == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        AuthenVm authenVm = new AuthenVm();
        authenVm.setAccessToken(accessToken);
        authenVm.setRefeshToken(refreshToken);

        return new SuccessResponse<>(AuthConstants.LOGIN_SUCCESS,authenVm);
    }

    @Override
    public SuccessResponse<Boolean> register(RegisterDTO dto){
        var isFoundUserByUserName = userRepository.findByUserName(dto.getUserName()).orElse(null);
        if(isFoundUserByUserName != null){
            throw new UserNameUsedException(AuthConstants.USERNAME_USED);
        }

        var isFoundUserByEmail = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if(isFoundUserByEmail != null){
            throw new EmailUsedException(AuthConstants.EMAIL_USED);
        }

        User newUser = new User();
        newUser.setFirstName(dto.getFirstName());
        newUser.setLastName(dto.getLastName());
        newUser.setUserName(dto.getUserName());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        newUser.setVerify(false);
        newUser.setNotLocked(true);
        newUser.setRole(Role.USER);
        newUser.setCode(passwordEncoder.encode(Utilities.generateCode()));

        var saveUser = userRepository.save(newUser);
        if(saveUser == null){
            throw new RegisterException(AuthConstants.REGISTER_FAILED);
        }

        mailService.sendMail(saveUser.getEmail(), Constants.SUBJECT_EMAIL_VERIFY,Constants.PUBLIC_HOST + "?email=" + saveUser.getEmail() + "&code=" + saveUser.getCode());

        return new SuccessResponse<>(AuthConstants.REGISTER_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> verifyAccount(VerifyDTO dto) {
        var foundUser = userRepository.findByEmailAndCode(dto.getEmail(), dto.getCode()).orElse(null);
        if(foundUser == null){
            throw new VerifyEmailException(AuthConstants.VERIFY_FAILED);
        }
        if(foundUser.isVerify()){
            return new SuccessResponse<>(AuthConstants.ACCOUNT_VERIFIED,true);
        }
        foundUser.setVerify(true);
        foundUser.setCode(null);

        var saveUser =userRepository.save(foundUser);
        if(saveUser == null){
            throw new VerifyEmailException(AuthConstants.VERIFY_FAILED);
        }
        return new SuccessResponse<>(AuthConstants.VERIFY_SUCCESS,true);

    }

    @Override
    public SuccessResponse<Boolean> resendEmail(ResendMailDTO dto) {
        var foundUser = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if(foundUser == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        if(foundUser.isVerify()){
            return new SuccessResponse<>(AuthConstants.ACCOUNT_VERIFIED,true);
        }

        foundUser.setCode(passwordEncoder.encode(Utilities.generateCode()));

        var setCodeUser = userRepository.save(foundUser);

        if(setCodeUser == null){
            throw new SetCodeUserException(AuthConstants.SET_CODE_FAILED);
        }

        mailService.sendMail(setCodeUser.getEmail(), Constants.SUBJECT_EMAIL_VERIFY,Constants.PUBLIC_HOST + "?email=" + setCodeUser.getEmail() + "&code=" + setCodeUser.getCode());

        return new SuccessResponse<>(AuthConstants.RESEND_EMAIL,true);

    }

    @Override
    public SuccessResponse<String> refreshToken(String refreshToken) {
        String userName = jwtService.extractUsername(refreshToken);
        if(userName != null){
            var userFound = userRepository.findByUserName(userName).orElse(null);
            if(userFound == null){
                throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
            }

            var accessToken = jwtService.generateAccessToken(userFound);
            return new SuccessResponse<>("Thành công",accessToken);
        }

        throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
    }

    @Override
    public SuccessResponse<Boolean> lockAccount(int id) {
        var foundUser = userRepository.findById(id).orElse(null);
        if(foundUser == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        foundUser.setNotLocked(false);

        var save = userRepository.save(foundUser);
        if(save == null){
            throw new AccountLockedException(AuthConstants.LOCKED_ACCOUNT_FAILED);
        }
        return new SuccessResponse<>(AuthConstants.LOCKED_ACCOUNT_SUCCESS,true);
    }

    @Override
    public SuccessResponse<Boolean> unLockAccount(int id){
        var foundUser = userRepository.findById(id).orElse(null);
        if(foundUser == null){
            throw new UserNotFoundException(AuthConstants.USER_NOT_FOUND);
        }

        foundUser.setNotLocked(true);

        var save = userRepository.save(foundUser);
        if(save == null){
            throw new AccountLockedException(AuthConstants.UN_LOCKED_ACCOUNT_FAILED);
        }
        return new SuccessResponse<>(AuthConstants.UN_LOCKED_ACCOUNT_SUCCESS,true);
    }
}
