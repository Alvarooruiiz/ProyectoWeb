package Tabla;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.text.NumberFormat;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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
    private List<Product> listProductsFiltro;
    private List<Lugar> listaLugares;
    private Product selectedProduct;
    private Product auxProd;
    private boolean editando;
    private ArrayList<String> availableCategories;
    private BarChartModel barChartModel;
    private boolean mostrarTablaLugares;
    private String selectedCategory;
    private Date selectedDate;
    private Date filtroFecha1;
    private Date filtroFecha2;
    private Boolean fecha1Ingresada;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @PostConstruct
    public void init() {
        listaProducts = new ArrayList<>();
        listProductsFiltro = new ArrayList<>();
        fecha1Ingresada = true;

        listaLugares = new ArrayList<>();

        try {
            listaProducts.add(new Product("f230fh0g3", "Bamboo", "Accessories", 24, 25.50, sdf.parse("01/04/2024"), true, obtenerLugares()));
            listaProducts.add(new Product("nvklal433", "Black Watch", "Electronics", 5, 5.50, sdf.parse("01/01/2024"), false, obtenerLugares()));
            listaProducts.add(new Product("zz21cz3c1", "Blue Band", "Fitness", 2, 50.50, sdf.parse("23/03/2002"), true, obtenerLugares()));
            listaProducts.add(new Product("244wgerg2", "Blue T-Shirt", "Clothing", 25, 100, sdf.parse("01/12/2010"), false, obtenerLugares()));
            listaProducts.add(new Product("8sn329d", "Red Headphones", "Electronics", 0, 49.99, sdf.parse("15/06/2018"), true, obtenerLugares()));
            listaProducts.add(new Product("acv435s", "Silver Necklace", "Accessories", 15, 79.99, sdf.parse("10/10/2017"), false, obtenerLugares()));
            listaProducts.add(new Product("1plm09s", "Green Yoga Mat", "Fitness", 30, 29.99, sdf.parse("05/04/2019"), true, obtenerLugares()));
            listaProducts.add(new Product("q1p9n3m", "Leather Jacket", "Clothing", 0, 199.99, sdf.parse("20/11/2015"), false, obtenerLugares()));
            listaProducts.add(new Product("dk39n2p", "Wireless Mouse", "Electronics", 20, 19.99, sdf.parse("02/03/2020"), true, obtenerLugares()));
            listaProducts.add(new Product("3km4n9s", "Running Shoes", "Footwear", 10, 79.99, sdf.parse("12/09/2021"), false, obtenerLugares()));

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Product p : listaProducts) {
            listProductsFiltro.add(p);
        }
        createBarModel();

    }

    public Boolean getFecha1Ingresada() {
        return fecha1Ingresada;
    }

    public void setFecha1Ingresada(Boolean fecha1Ingresada) {
        this.fecha1Ingresada = fecha1Ingresada;
    }

    public Date getFiltroFecha1() {
        return filtroFecha1;
    }

    public void setFiltroFecha1(Date filtroFecha1) {
        this.filtroFecha1 = filtroFecha1;
    }

    public Date getFiltroFecha2() {
        return filtroFecha2;
    }

    public void setFiltroFecha2(Date filtroFecha2) {
        this.filtroFecha2 = filtroFecha2;
    }

    public void cambiarFecha() {
        fecha1Ingresada = false;
    }

    public List<Lugar> getListaLugaresExpansion(Product p) {

        if (p != null) {
            auxProd = p;
            return p.getLugares();
        } else {
            return auxProd.getLugares();
        }

    }

    public List<Product> getListProductsFiltro() {
        return listProductsFiltro;
    }

    public void setListProductsFiltro(List<Product> listProductsFiltro) {
        this.listProductsFiltro = listProductsFiltro;
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
        selectedCategory = null;
        selectedDate = null;
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
                    mostrarTablaFiltro();
                    createBarModel();
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

    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
    }

    public void deleteProduct(Product p) {
        listaProducts.remove(p);
        mostrarTablaFiltro();
        createBarModel();
        listaLugares = null;
    }

    public void deleteLugar(Lugar l) {
        selectedProduct.getLugares().remove(l);
    }

    public void deleteLugarExpansion(Lugar l, Product p) {
        p.getLugares().remove(l);
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

//    public void mostrarTablaFiltro() {
//        listProductsFiltro.clear();
//        mostrarTablaLugares = false;
//        PrimeFaces.current().executeScript("PF('dataTable').clearFilters()");
//
//        if (selectedCategory != null && selectedDate != null) {
//            for (Product product : listaProducts) {
//                if (selectedCategory.equals(product.getCategory()) && selectedDate.equals(product.getBirth())) {
//                    listProductsFiltro.add(product);
//                }
//            }
//        } else if (selectedCategory != null && selectedDate == null) {
//            for (Product product : listaProducts) {
//                if (selectedCategory.equals(product.getCategory())) {
//                    listProductsFiltro.add(product);
//                }
//            }
//        } else if (selectedCategory == null && selectedDate != null) {
//            for (Product product : listaProducts) {
//                if (selectedDate.equals(product.getBirth())) {
//                    listProductsFiltro.add(product);
//                }
//            }
//        } else {
//            listProductsFiltro.addAll(listaProducts);
//        }
//    }
    public void mostrarTablaFiltro() {
        listProductsFiltro.clear();
        mostrarTablaLugares = false;
        PrimeFaces.current().executeScript("PF('dataTable').clearFilters()");

        if (selectedCategory != null && filtroFecha1 != null && filtroFecha2 != null) {

            for (Product product : listaProducts) {
                if (selectedCategory.equals(product.getCategory()) && comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                    listProductsFiltro.add(product);
                }
            }
        } else if (selectedCategory != null && filtroFecha1 == null) {
            for (Product product : listaProducts) {
                if (selectedCategory.equals(product.getCategory())) {
                    listProductsFiltro.add(product);
                }
            }
        } else if (selectedCategory == null && filtroFecha1 != null && filtroFecha2 != null) {
            for (Product product : listaProducts) {
                if (comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                    listProductsFiltro.add(product);
                }
            }
        } else {
            listProductsFiltro.addAll(listaProducts);
        }
        showMessaggeGood("Se han encontrado " + listProductsFiltro.size() + " resultados");
    }

    public boolean comprobarFechas(Date fecha, Date fechaFiltro1, Date fechaFiltro2) {
        return !fecha.before(fechaFiltro1) && !fecha.after(fechaFiltro2);
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public void exportToExcel() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Code", "Name", "Category", "Quantity", "Price", "Date", "Stock"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headerRow.getCell(i).setCellStyle(headerCellStyle);
            }

            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

            int rowNum = 1;
            for (Product product : listProductsFiltro) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getCode());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getCategory());
                row.createCell(3).setCellValue(product.getQuantity());
                row.createCell(4).setCellValue(formatoImporte.format(product.getPrice()));
                row.createCell(5).setCellValue(sdf.format(product.getBirth()));
                String val = (product.isStock()) ? "Si" : "No";
                row.createCell(6).setCellValue(val);
            }

            workbook.write(out);
            byte[] content = out.toByteArray();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=productos.xlsx");
            response.setContentLength(content.length);

            response.getOutputStream().write(content);

            FacesContext.getCurrentInstance().responseComplete();
        }
    }
}
