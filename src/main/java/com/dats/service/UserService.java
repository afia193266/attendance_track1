package com.dats.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dats.dto.Role;
import com.dats.dto.User;
import com.dats.entity.UserEntity;


public interface UserService extends UserDetailsService{
    User addUser(User user);
    User getUser(Long userId);
    UserEntity getUser(String username);
    void updateUser(UserEntity user);
    void updateUser(User user);
    void deleteUser(Long userId);
    List<User> getUsers();
    List<User> getTeachers(); 
    List<User> getStudents(); 
    void assignRoleToUser(Role role, User user);
    void updateProfile(User user);
    public long countTotalUsers();
    //List<UserAutocompleteInfo> getMatchingNames(String name);
}
