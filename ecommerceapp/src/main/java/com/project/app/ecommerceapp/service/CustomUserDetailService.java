package com.project.app.ecommerceapp.service;

import com.project.app.ecommerceapp.model.CustomerUserDetail;
import com.project.app.ecommerceapp.model.User;
import com.project.app.ecommerceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));

        return user.map(CustomerUserDetail::new).get();
    }
}
