package com.alfrescos.orderingsystem.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Liger on 20-Mar-17.
 */
public class CommonUtil {
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}
