package com.Jguides.spingboot.service;

import java.util.List;
import com.Jguides.spingboot.Model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);

    List<User> searchUser(String keyword);
}
