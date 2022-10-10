package com.rea.app.security.controller;

import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.common.model.Response;
import com.rea.app.security.dtos.AdminLoginRequestDto;
import com.rea.app.security.dtos.UserLoginRequestDto;
import com.rea.app.security.jwt.JwtTokenHelper;
import com.rea.app.security.jwt.response.JwtAuthResponse;
import com.rea.app.user.dtos.UserResponseDto;
import com.rea.app.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Response> createToken(@Valid @RequestBody UserLoginRequestDto request) throws Exception {
        this.authenticate(request.getPhone(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getPhone());
        String accessToken = this.jwtTokenHelper.generateToken(userDetails);
        String refreshToken = this.jwtTokenHelper.generateRefreshToken(userDetails);

        String username = userDetails.getUsername();
        this.userService.updateRefreshToken(username,refreshToken);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(accessToken,refreshToken);
        Response<JwtAuthResponse> response = new Response<>(200,true,"Successfully Logged In",jwtAuthResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/admin/login")
    public ResponseEntity<Response> createAdminToken(@Valid @RequestBody AdminLoginRequestDto request) throws Exception {
        this.authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String accessToken = this.jwtTokenHelper.generateToken(userDetails);
        String refreshToken = this.jwtTokenHelper.generateRefreshToken(userDetails);

        String username = userDetails.getUsername();
        this.userService.updateRefreshToken(username,refreshToken);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(accessToken,refreshToken);
        Response<JwtAuthResponse> response = new Response<>(200,true,"Successfully Logged In",jwtAuthResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAccess")
    public ResponseEntity<Response> getAccessToken(@RequestHeader (name="Authorization") String token){
        String username = null;

        String refreshToken = null;
        if (token != null && token.startsWith("Bearer")) {
            refreshToken = token.substring(7);

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(refreshToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Jwt token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("invalid jwt");
            } catch (SignatureException e){
                System.out.println("invalid jwt");
            }

            if(this.userService.checkUserAndRefreshToken(username,refreshToken)){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                String accessToken = this.jwtTokenHelper.generateToken(userDetails);

                JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(accessToken,refreshToken);
                Response<JwtAuthResponse> response = new Response<>(200,true,"New Access Token Generated",jwtAuthResponse);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                Response response = new Response<>();
                response.setStatus(400);
                response.setMessage("Bad Token");
                response.setData("");
                response.setSuccess(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }else {
            Response response = new Response<>();
            response.setStatus(400);
            response.setMessage("Bad Token Name");
            response.setData("");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(String username, String password) throws Exception {

        if(this.userService.checkPasswordAndOTPExpiration(username,password)){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                    password);
            try {
                this.authenticationManager.authenticate(authenticationToken);
            } catch (BadCredentialsException e) {
                System.out.println("Invalid Detials !!");
//            throw new ApiException("Invalid username or password !!");
            }
        }else {
            throw new ResourceNotFoundException("OTP Exception","OTP is Expired, Please request for a new OTP",username);
        }

    }

}