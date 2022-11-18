package com.project.app.ecommerceapp.global;

import com.project.app.ecommerceapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static List<Product> cart;

    static {
        cart = new ArrayList<Product>();
    }
}
