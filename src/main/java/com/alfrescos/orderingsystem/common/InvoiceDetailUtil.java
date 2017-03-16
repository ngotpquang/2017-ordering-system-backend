package com.alfrescos.orderingsystem.common;

import com.alfrescos.orderingsystem.entity.FoodAndDrink;
import com.alfrescos.orderingsystem.entity.Invoice;
import com.alfrescos.orderingsystem.entity.InvoiceDetail;
import com.alfrescos.orderingsystem.service.FoodAndDrinkService;
import com.alfrescos.orderingsystem.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by Liger on 16-Mar-17.
 */
public class InvoiceDetailUtil {
    public static boolean addInvoiceDetail(int numberOfInvoiceDetails, Map<String, String> data, Invoice invoice, Date timeOrdered, FoodAndDrinkService foodAndDrinkService, InvoiceDetailService invoiceDetailService){
        System.out.println(timeOrdered + "-" + data + "-" + foodAndDrinkService + "-" + invoiceDetailService);
        boolean isInvoiceDetailAllCreated = true;
        for (int i = 1; i <= numberOfInvoiceDetails; i++) {
            String foodAndDrinkId = data.get("foodAndDrinkId" + i).trim();
            int quantity = Integer.parseInt(data.get("quantity" + i).trim());
            FoodAndDrink foodAndDrink = foodAndDrinkService.findById(Long.parseLong(foodAndDrinkId));
            InvoiceDetail invoiceDetail = invoiceDetailService.create(new InvoiceDetail(invoice, foodAndDrink, quantity, timeOrdered, foodAndDrink.getPrice()));
            if (invoiceDetail == null) {
                isInvoiceDetailAllCreated = false;
                break;
            }
        }
        return isInvoiceDetailAllCreated;
    }
}
