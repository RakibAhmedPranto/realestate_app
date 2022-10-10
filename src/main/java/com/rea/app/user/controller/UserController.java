package com.rea.app.user.controller;

import com.rea.app.common.model.Response;
import com.rea.app.user.dtos.AdminRequestDto;
import com.rea.app.user.dtos.UserRequestDto;
import com.rea.app.user.dtos.UserResponseDto;
import com.rea.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create/users")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        Response<UserResponseDto> response = this.userService.createUser(userRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/create/admin")
    public ResponseEntity<Response> createAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto){
        Response<UserResponseDto> response = this.userService.createAdmin(adminRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getOTP/{userName}")
    public ResponseEntity<Response<String>> getOTP(@PathVariable String userName){
//        System.out.println(userName);

        String newOTP = this.userService.getNewOTP(userName);

        Response<String> response = new Response<>();
        response.setData(newOTP);
        response.setMessage("New OTP Generated");
        response.setSuccess(true);
        response.setStatus(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
