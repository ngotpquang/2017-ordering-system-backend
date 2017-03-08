package com.alfrescos.orderingsystem.service;

import com.alfrescos.orderingsystem.entity.Role;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Liger on 06-Mar-17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private boolean checkString(String value) {
        if (value != null) {
            if (!value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByAccountCode(String accountCode) {
        return userRepository.findByAccountCode(accountCode);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User update(User user) {
        String accountCode = SecurityContextHolder.getContext().getAuthentication().getName();
        User oldUser = this.userRepository.findByAccountCode(accountCode);
        if (checkString(user.getName())) {
            oldUser.setName(user.getName());
        }
        if (checkString(user.getPassword())) {
            oldUser.setPassword(user.getPassword());
        }
        if (checkString(user.getEmail())) {
            oldUser.setEmail(user.getEmail());
        }
        if (checkString(user.getDetail())) {
            oldUser.setDetail(user.getDetail());
        }
        return this.userRepository.save(oldUser);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoleByUserId(Long id) {
        List<Role> list =  this.userRepository.findOne(id).getPermissionList().stream().map(x -> x.getRole()).collect(Collectors.toList());
        return list;
    }
}
