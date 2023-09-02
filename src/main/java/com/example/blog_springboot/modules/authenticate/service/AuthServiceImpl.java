package com.example.blog_springboot.modules.authenticate.service;

import com.example.blog_springboot.commons.SuccessResponse;
import com.example.blog_springboot.exceptions.NotFoundException;
import com.example.blog_springboot.modules.authenticate.dto.LoginDTO;
import com.example.blog_springboot.modules.authenticate.dto.RegisterDTO;
import com.example.blog_springboot.modules.authenticate.exception.EmailUsedException;
import com.example.blog_springboot.modules.authenticate.exception.RegisterException;
import com.example.blog_springboot.modules.authenticate.exception.UserNameUsedException;
import com.example.blog_springboot.modules.authenticate.viewmodel.AuthenVm;
import com.example.blog_springboot.modules.jwt.service.JwtService;
import com.example.blog_springboot.modules.user.enums.Role;
import com.example.blog_springboot.modules.user.model.User;
import com.example.blog_springboot.modules.user.repository.UserRepository;
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


    public AuthServiceImpl(AuthenticationManager authenticationManager,JwtService jwtService,UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SuccessResponse<AuthenVm> login(LoginDTO dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
           dto.getUserName(),
           dto.getPassword()
        ));

        var user = userRepository.findByUserName(dto.getUserName()).orElse(null);
        if(user == null){
            throw new NotFoundException("Người dùng này không tồn tại");
        }

        var jwt = jwtService.generateToken(user);
        AuthenVm authenVm = new AuthenVm();
        authenVm.setAccessToken(jwt);
        authenVm.setRefeshToken(jwt);

        return new SuccessResponse<>("Thành công",authenVm);
    }

    @Override
    public SuccessResponse<Boolean> register(RegisterDTO dto){
        var isFoundUserByUserName = userRepository.findByUserName(dto.getUserName()).orElse(null);
        if(isFoundUserByUserName != null){
            throw new UserNameUsedException("Tài khoản này đã có người sử dụng");
        }

        var isFoundUserByEmail = userRepository.findByEmail(dto.getEmail()).orElse(null);
        if(isFoundUserByEmail != null){
            throw new EmailUsedException("Email này đã có người sử dụng");
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

        var saveUser = userRepository.save(newUser);
        if(saveUser == null){
            throw new RegisterException("Đăng ký thất bại");
        }

        return new SuccessResponse<>("Thành công",true);
    }
}
