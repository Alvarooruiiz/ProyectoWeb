/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author aruize
 */
public class Product {
    private String code;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private Date birth;
    private boolean stock;
    private ArrayList<Lugar> lugares;
   
    

    public Product() {
    }

    public Product(String code, String name, String category, int quantity, double price, Date birth, boolean stock, ArrayList<Lugar> lugares) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.birth = birth;
        this.stock = stock;
        this.lugares = lugares;
    }

    public ArrayList<Lugar> getLugares() {
        return lugares;
    }

   

    
    public void setLugares(ArrayList<Lugar> lugares) {
        this.lugares = lugares;
    }

    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
    

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
