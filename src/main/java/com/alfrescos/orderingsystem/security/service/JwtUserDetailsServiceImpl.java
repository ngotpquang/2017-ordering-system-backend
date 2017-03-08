package com.alfrescos.orderingsystem.security.service;

import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtUser;
import com.alfrescos.orderingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stephan on 20.03.16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String accountCode) throws UsernameNotFoundException {
        User user = userService.findByAccountCode(accountCode);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", accountCode));
        } else {
            return new JwtUser(
                    user.getId(),
                    user.getAccountCode(),
                    user.getName(),
                    this.mapToGrantedAuthorities(user.getId()));
        }
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(Long userId) {
        return this.userService.getAllRoleByUserId(userId)
                .stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }
}
