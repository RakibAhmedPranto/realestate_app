package com.rea.app.user.service;

import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.common.model.Response;
import com.rea.app.config.AppConstants;
import com.rea.app.role.entity.Role;
import com.rea.app.role.repository.RoleRepository;
import com.rea.app.user.dtos.AdminRequestDto;
import com.rea.app.user.dtos.UserRequestDto;
import com.rea.app.user.dtos.UserResponseDto;
import com.rea.app.user.entity.User;
import com.rea.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static com.rea.app.common.ResponseObject.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        Response<UserResponseDto> response = new Response<>();

        Role role = this.roleRepository.findById(AppConstants.ROLE_USER).get();

        try {
            User user = new User();
            user.setName(userRequestDto.getName());
            user.setUsername(userRequestDto.getPhone());
            user.setPhone(userRequestDto.getPhone());
            user.setPassword(this.passwordEncoder.encode(this.generateOTP()));
            user.setOtpGeneratedTime(this.getPresentTime());
            user.getRoles().add(role);
            User savedUser = this.userRepository.save(user);
            UserResponseDto userResponseDto = this.modelMapper.map(savedUser,UserResponseDto.class);
            return dataSavedOrUpdateSuccess(response,userResponseDto,201);
        }catch (RuntimeException ex){
            return dataNotSavedOrUpdate(response);
        }
    }

    @Override
    public Response<UserResponseDto> createAdmin(AdminRequestDto adminRequestDto) {
        Response<UserResponseDto> response = new Response<>();

        Role role = this.roleRepository.findById(AppConstants.ROLE_ADMIN).get();

        try {
            User user = new User();
            user.setName(adminRequestDto.getName());
            user.setUsername(adminRequestDto.getEmail());
            user.setEmail(adminRequestDto.getEmail());
            user.setPassword(this.passwordEncoder.encode(this.generateOTP()));
            user.setOtpGeneratedTime(this.getPresentTime());
            user.getRoles().add(role);
            User savedUser = this.userRepository.save(user);
            UserResponseDto userResponseDto = this.modelMapper.map(savedUser,UserResponseDto.class);
            return dataSavedOrUpdateSuccess(response,userResponseDto,201);
        }catch (RuntimeException ex){
            return dataNotSavedOrUpdate(response);
        }
    }

    @Override
    public void updateRefreshToken(String username, String refreshToken) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","username",username));

        user.setRefreshToken(refreshToken);
        this.userRepository.save(user);
    }

    @Override
    public String getNewOTP(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","username",username));

        String OTP = this.generateOTP();

        user.setPassword(this.passwordEncoder.encode(OTP));
        //Save in second
        user.setOtpGeneratedTime(this.getPresentTime());

        this.userRepository.save(user);

        return OTP;
    }

    @Override
    public Boolean checkPasswordAndOTPExpiration(String username, String password) {
        Long currentTimeInSecond = this.getPresentTime();

        User user = this.userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","username",username));

        Long otpGeneratedTime = user.getOtpGeneratedTime();

        long timeDiff = currentTimeInSecond - otpGeneratedTime;

        if(this.passwordEncoder.matches(password, user.getPassword()) && timeDiff <= AppConstants.OTP_EXPIRATION_THRESHOLD){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean checkUserAndRefreshToken(String username, String refreshtoken) {
        User user = this.userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","username",username));

        String dbRefreshToken = user.getRefreshToken();
        if(dbRefreshToken.equals(refreshtoken)){
            return true;
        }else {
            System.out.println("Token Miss matched");
            return false;
        }
    }

    private String generateOTP(){
        int tempOTP = ThreadLocalRandom.current().nextInt(100000, 1000000);
        String OTP = String.valueOf(tempOTP);
        return OTP;
    }

    private Long getPresentTime(){
        Date date = new Date();
        return date.getTime() / 1000;
    }
}