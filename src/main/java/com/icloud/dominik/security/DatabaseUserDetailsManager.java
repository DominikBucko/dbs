package com.icloud.dominik.security;

import backend.entity.User;
import backend.service.LogService;
import backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class DatabaseUserDetailsManager implements UserDetailsManager {
    UserService userService = new UserService();

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userService.getUserByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        String[] role;
        if (user.getIs_admin()) {
            role = new String[]{"Admin", "User"};
        } else {
            role = new String[]{"User"};
        }
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password("{noop}"+user.getPassword())
                .roles(role)
                .build();
        LogService.log(user.getUser_id(), "Login");
        return userDetails;
    }
}
