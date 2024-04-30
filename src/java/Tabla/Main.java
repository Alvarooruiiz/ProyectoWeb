package Tabla;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;

/**
 *
 * @author aruize
 */
@ManagedBean(name = "Main")
@ViewScoped
public class Main {

    private List<Product> listaProducts;
    private List<Lugar> listaLugares;
    private Product selectedProduct;
    private boolean editando;
    private ArrayList<String> availableCategories;
    private BarChartModel barChartModel;
    private boolean mostrarTablaLugares;

    @PostConstruct
    public void init() {
        listaProducts = new ArrayList<>();
        listaLugares = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            listaProducts.add(new Product("f230fh0g3", "Bamboo", "Accessories", 24, 25.50, sdf.parse("11/02/2005"), true, obtenerLugares()));
            listaProducts.add(new Product("nvklal433", "Black Watch", "Electronics", 5, 5.50, sdf.parse("01/01/1995"), false,obtenerLugares()));
            listaProducts.add(new Product("zz21cz3c1", "Blue Band", "Fitness", 2, 50.50, sdf.parse("23/03/2002"), true,obtenerLugares()));
            listaProducts.add(new Product("244wgerg2", "Blue T-Shirt", "Clothing", 25, 100, sdf.parse("01/12/2010"), false,obtenerLugares()));
            listaProducts.add(new Product("8sn329d", "Red Headphones", "Electronics", 0, 49.99, sdf.parse("15/06/2018"), true,obtenerLugares()));
            listaProducts.add(new Product("acv435s", "Silver Necklace", "Accessories", 15, 79.99, sdf.parse("10/10/2017"), false,obtenerLugares()));
            listaProducts.add(new Product("1plm09s", "Green Yoga Mat", "Fitness", 30, 29.99, sdf.parse("05/04/2019"), true,obtenerLugares()));
            listaProducts.add(new Product("q1p9n3m", "Leather Jacket", "Clothing", 0, 199.99, sdf.parse("20/11/2015"), false,obtenerLugares()));
            listaProducts.add(new Product("dk39n2p", "Wireless Mouse", "Electronics", 20, 19.99, sdf.parse("02/03/2020"), true,obtenerLugares()));
            listaProducts.add(new Product("3km4n9s", "Running Shoes", "Footwear", 10, 79.99, sdf.parse("12/09/2021"), false,obtenerLugares()));

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        createBarModel();

    }

    public List<Product> getListaProducts() {
        return listaProducts;
    }

    public void setListaProducts(List<Product> listaProducts) {
        this.listaProducts = listaProducts;
    }

    public boolean isMostrarTablaLugares() {
        return mostrarTablaLugares;
    }

    public void setMostrarTablaLugares(boolean mostrarTablaLugares) {
        this.mostrarTablaLugares = mostrarTablaLugares;
    }
    

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public void createBarModel() {
        barChartModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Precios");

        List<Number> values = new ArrayList<>();
        for (Product p : listaProducts) {
            values.add(p.getPrice());
        }
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        bgColor.add("rgba(75, 192, 192, 0.2)");
        bgColor.add("rgba(54, 162, 235, 0.2)");
        bgColor.add("rgba(153, 102, 255, 0.2)");
        bgColor.add("rgba(201, 203, 207, 0.2)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        borderColor.add("rgb(75, 192, 192)");
        borderColor.add("rgb(54, 162, 235)");
        borderColor.add("rgb(153, 102, 255)");
        borderColor.add("rgb(201, 203, 207)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        for (Product p : listaProducts) {
            labels.add(p.getName());
        }
        data.setLabels(labels);
        barChartModel.setData(data);
    }

    public List<Product> getLista() {
        return listaProducts;
    }

    public void setLista(List<Product> lista) {
        this.listaProducts = lista;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void openNew() {
        selectedProduct = new Product();
        editando = false;
    }

    public void addProduct() {
        if (!editando) {
            if (selectedProduct.getCode() == null || selectedProduct.getCode().isEmpty()) {
                showError("El código no es correcto o está vacío");
            } else if (selectedProduct.getName().isEmpty()) {
                showError("El nombre no es correcto o está vacío");
            } else if (selectedProduct.getCategory().isEmpty() || selectedProduct.getCategory() == null) {
                showError("La categoría no es correcta o está vacía");
            } else if (selectedProduct.getQuantity() < 0) {
                showError("La cantidad no puede ser menor de 0");
            } else {
                boolean codeExists = false;
                for (Product product : listaProducts) {
                    if (product.getCode().equals(selectedProduct.getCode())) {
                        codeExists = true;
                        break;
                    }
                }

                if (codeExists) {
                    showError("El código del producto ya existe en la lista");
                } else {
                    selectedProduct.setLugares(obtenerLugares());
                    listaProducts.add(selectedProduct);
                    PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                }
            }
        } else {
            for (Product product : listaProducts) {
                if (product.getCode().equals(selectedProduct.getCode())) {
                    product = selectedProduct;
                    PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                    showMessaggeGood("Se ha añadido un producto");
                }
            }
        }
        createBarModel();

    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
    }

    public void deleteProduct(Product p) {
        listaProducts.remove(p);
        createBarModel();
        listaLugares = null;
    }
    public void deleteLugar(Lugar l) {
        selectedProduct.getLugares().remove(l);
    }

    public void editProduct(Product p) {
        editando = true;
        this.selectedProduct = p;
        
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public Date fechaHoy() {
        return new Date();
    }

    public List<String> getAvailableCategories() {
        Set<String> categoriesSet = new HashSet<>();
        for (Product product : listaProducts) {
            categoriesSet.add(product.getCategory());
        }
        return new ArrayList<>(categoriesSet);
    }
    
    

    private ArrayList<Lugar> obtenerLugares() {
        listaLugares = new ArrayList<>();
        listaLugares.add(new Lugar(1, "Malaga", "España"));
        listaLugares.add(new Lugar(2, "Madrid", "España"));
        listaLugares.add(new Lugar(3, "Paris", "Francia"));
        listaLugares.add(new Lugar(4, "Roma", "Italia"));
        listaLugares.add(new Lugar(5, "Barcelona", "España"));

        ArrayList<Lugar> lugaresAleatorios = new ArrayList<>();
        Random random = new Random();

        Collections.shuffle(listaLugares);

        for (int i = 0; i < 3; i++) {
            lugaresAleatorios.add(listaLugares.get(i));
        }

        return lugaresAleatorios;
    }

    public List<Lugar> getListaLugares() {
        return listaLugares;
    }

    public void setListaLugares(List<Lugar> listaLugares) {
        this.listaLugares = listaLugares;
    }
    
    public void onRowSelect() {
        if (selectedProduct != null) {
            listaLugares = selectedProduct.getLugares();
            mostrarTablaLugares = true;
        }
    }
    


    
}
