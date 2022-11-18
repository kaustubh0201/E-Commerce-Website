package com.project.app.ecommerceapp.service;

import com.project.app.ecommerceapp.model.Role;
import com.project.app.ecommerceapp.model.User;
import com.project.app.ecommerceapp.repository.RoleRepository;
import com.project.app.ecommerceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void processOAuthPostLogin(OAuth2AuthenticationToken token) {

        // getting the email information from the token given the oauth
        String email = token.getPrincipal().getAttributes().get("email").toString();

        // we try to find the user using the email ID
        Optional<User> existUser = userRepository.findUserByEmail(email);

        // if the user does not exist then we create a new user
        if(existUser.isEmpty()) {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles((roles));

            userRepository.save(user);
        }
    }
}
