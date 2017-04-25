/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.common;

import com.google.common.collect.Collections2;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Liger on 20-Mar-17.
 */
public class CommonUtil {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }


//    public static void main(String[] args) {
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        System.out.println(cal.get(Calendar.MONTH) + 1 + " - " + cal.get(Calendar.YEAR));
//        System.out.println(date.getMonth() + ?" - " + date.getYear());
//    }
}
