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

//    public static void main(String[] args) {
//        FoodAndDrink foodAndDrink1 = new FoodAndDrink();
//        foodAndDrink1.setName("1");
//        FoodAndDrink foodAndDrink2 = new FoodAndDrink();
//        foodAndDrink2.setName("2");
//        FoodAndDrink foodAndDrink3 = new FoodAndDrink();
//        foodAndDrink3.setName("3");
//        FoodAndDrink foodAndDrink4 = new FoodAndDrink();
//        foodAndDrink4.setName("4");
//        List<FoodAndDrink> list = new ArrayList<>();
//        list.add(foodAndDrink1);
//        list.add(foodAndDrink2);
//        list.add(foodAndDrink3);
//        list.add(foodAndDrink4);
//        for (FoodAndDrink fad: list){
//            System.out.println(fad.getName());
//        }
//        list = changePosition(list, foodAndDrink2, foodAndDrink4);
//        for (FoodAndDrink fad: list){
//            System.out.println(fad.getName());
//        }
//        list = changePosition(list, foodAndDrink2, foodAndDrink3);
//        for (FoodAndDrink fad: list){
//            System.out.println(fad.getName());
//        }
//    }


//    public static void main(String[] args) {
//        Date date = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        System.out.println(cal.get(Calendar.MONTH) + 1 + " - " + cal.get(Calendar.YEAR));
//        System.out.println(date.getMonth() + ?" - " + date.getYear());
//    }
}
