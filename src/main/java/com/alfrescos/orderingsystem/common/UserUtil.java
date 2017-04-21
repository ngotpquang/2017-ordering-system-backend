/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.common;

import com.alfrescos.orderingsystem.entity.Permission;
import com.alfrescos.orderingsystem.entity.User;
import com.alfrescos.orderingsystem.security.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Created by Liger on 09-Mar-17.
 */
public class UserUtil {
    public static Long getIdByAuthorization(){
        return ((JwtUser) (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    public static String getAccountCodeByAuthorization(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean checkAdminOrManagerAccount(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("MANAGER") ||
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("ADMIN");
    }

    public static boolean checkStaffAccount(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains("STAFF");
    }

    public static boolean checkStaffAccount(User user){
        List<Permission> permissionList = user.getPermissionList();
        for(Permission p : permissionList){
            if (p.getRole().getName().contains("STAFF")){
                return true;
            }
        }
        return false;
    }

}
