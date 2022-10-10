package com.rea.app.user.service;

import com.rea.app.common.model.Response;
import com.rea.app.user.dtos.AdminRequestDto;
import com.rea.app.user.dtos.UserRequestDto;
import com.rea.app.user.dtos.UserResponseDto;

public interface UserService {
    Response<UserResponseDto> createUser(UserRequestDto userRequestDto);
    Response<UserResponseDto> createAdmin(AdminRequestDto adminRequestDto);

    void updateRefreshToken(String username, String refreshToken);

    String getNewOTP(String username);

    Boolean checkPasswordAndOTPExpiration(String username,String password);

    Boolean checkUserAndRefreshToken(String username,String refreshtoken);
}
