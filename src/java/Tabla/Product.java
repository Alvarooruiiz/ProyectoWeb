package Tabla;

import java.util.ArrayList;
import java.util.Date;

public class Product {
    private String code;
    private String name;
    private Categoria category;
    private int quantity;
    private double price;
    private Date birth;
    private short stock;
    private ArrayList<Lugar> lugares;
    
   
    

    public Product() {
    }

    public Product(String code, String name, Categoria category, int quantity, double price, Date birth, short stock, ArrayList<Lugar> lugares) {
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

    public short getStock() {
        return stock;
    }

    public void setStock(short stock) {
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

    public Categoria getCategory() {
        return category;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
