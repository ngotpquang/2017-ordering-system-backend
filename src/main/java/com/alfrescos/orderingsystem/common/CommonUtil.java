/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.common;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
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

    public static String getLocalTime(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        hour = (hour < 17) ? hour + 7 : hour + 7 - 24;
        return hour + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    public static List<FoodAndDrink> changePosition(List<FoodAndDrink> foodAndDrinkList, FoodAndDrink foodAndDrink1, FoodAndDrink foodAndDrink2) {
        int index1 = 0;
        int index2 = 0;
        for (FoodAndDrink foodAndDrink : foodAndDrinkList) {
            if (foodAndDrink.getName().equals(foodAndDrink1.getName())) {
                index1 = foodAndDrinkList.indexOf(foodAndDrink);
            } else if (foodAndDrink.getName().equals(foodAndDrink2.getName())) {
                index2 = foodAndDrinkList.indexOf(foodAndDrink);
            }
        }
        foodAndDrinkList.set(index1, foodAndDrink2);
        foodAndDrinkList.set(index2, foodAndDrink1);
        return foodAndDrinkList;
    }

    public static void main(String[] args) {
        System.out.println(getLocalTime());;
    }

}
