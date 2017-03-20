package com.alfrescos.orderingsystem.common;

import com.alfrescos.orderingsystem.security.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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

}
