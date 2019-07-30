package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service // this class performs service tasks or business logics
    public class UserService {
        @Autowired
        UserRepository userRepository;

        @Autowired
        RoleRepository roleRepository;

        @Autowired
        public UserService(UserRepository userRepository){
            this.userRepository = userRepository;
        }

        public User findByEmail(String email){
            return userRepository.findByEmail(email);
        }

        public Long countByEmail(String email){
            return userRepository.countByEmail(email);
        }

        public User findByUsername(String username){
            return userRepository.findByUsername(username);
        }

        public void saveUser(User user){
            user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
            user.setEnabled(true);
            userRepository.save(user);
        }

        public void saveAdmin(User user){
            user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
            user.setEnabled(true);
            userRepository.save(user);
        }

        // returns currently logged in user
        public User getUser(){
            // retrieve the currently authenticated principal via a static call to SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // check if there is an authenticated user
//        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String currentPrincipalName = authentication.getName();
//        }
            User user = userRepository.findByUsername(currentPrincipalName);
            return user;
        }
    }

