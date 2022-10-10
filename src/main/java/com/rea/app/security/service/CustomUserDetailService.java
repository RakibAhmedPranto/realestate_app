package com.rea.app.security.service;

import com.rea.app.common.exceptions.ResourceNotFoundException;
import com.rea.app.user.entity.User;
import com.rea.app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
//        Matcher matEmail = emailPattern.matcher(username);
//
//        Pattern phonePattern = Pattern.compile("^\\d{10}$");
//        Matcher matPhone = phonePattern.matcher(username);

//        if(matEmail.matches()){
//            User user = this.userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email",username));
//            return user;
//        }
//        else if (matPhone.matches()){
//            User user = this.userRepository.findByPhone(username).orElseThrow(()->new ResourceNotFoundException("User","phone",username));
//            return user;
//        }else {
//            throw new UsernameNotFoundException("Wrong Email or Phone number");
//        }

        User user = this.userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User","username",username));
        return user;
    }
}
