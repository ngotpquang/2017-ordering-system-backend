/*
 * Copyright (c) 2017. All rights reserved.
 */

package com.alfrescos.orderingsystem.common;

/**
 * Created by Liger on 05-May-17.
 */
public class TableStatus {
    public static final int FREE = 0;
    public static final int ORDERING = 1;
    public static final int FOOD_IS_MADE = 2;
    public static final int CLEANING = 3;
    public static final int RESERVED = 4;
    public static final int RESERVING = 10;
    public static final int USED = 11;
    public static final int USER_CANCEL = 12;
    public static final int TIME_OUT = 13;
    public static final int ADMIN_CANCEL = 14;
}
