package Tabla;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import es.inerttia.ittwsEntidades.wsAlmacen.Palet;
import es.inerttia.ittwsEntidades.wsAlmacen.PaletLinea;
import es.inerttia.ittwsEntidades.wsAlmacen.PaletsRespuesta;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.animation.Animation;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PF;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DialogFrameworkOptions;
import org.primefaces.model.LazyDataModel;
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
    private List<Product> listProductsLazyFiltered;
    private List<Product> listProductsFiltroFilterValue;
    private List<Lugar> listaLugares;
    private List<Lugar> listaLugaresFilterValue;
    private Product selectedProduct;
    private Product auxProd;
    private boolean editando = false;
    private ArrayList<String> availableCategories;
    private BarChartModel barChartModel;
    private boolean mostrarTablaLugares = false;
    private boolean mostrarLazy = false;
    private boolean mostrarDataTable = false;
    private boolean activarBtnAdd = true;
    private String selectedCategory;
    private Date selectedDate;
    private Date filtroFecha1;
    private Date filtroFecha2;
    private Boolean fecha1Ingresada;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private boolean dragEnabled = true;
    private boolean filtroActivo = false;
    private int posicionTab = 0;
    private double totalPrecio = 0;
    private boolean hayFiltro = true;

    private LazyDataModel<Product> lazyModel;

    private static final long serialVersionUID = 2431097566797234783L;

    private TabView tabView;

    private boolean globalFilterOnly;
    private ArrayList<Product> filteredGlobalProduct;

    private PeticionJSON peticion;
    private List<Palet> listadoPalets;
    private List<PaletLinea> listadoLineasPalet;
    private Palet palet;
    private String ssccIntroducido = "";

    private boolean mostrarBotonDownload = true;
    private boolean mostrarBotonExportExcel = true;
    private boolean mostrarBotonExportPdf = true;

    private boolean seleccionStock;

    private ArrayList<Product> listLazyRows;

    // <editor-fold defaultstate="collapsed" desc=" getters y setters "> 
    public Boolean getFecha1Ingresada() {
        return fecha1Ingresada;
    }

    public boolean isSeleccionStock() {
        return selectedProduct.getStock() == 0;
    }

    public void setSeleccionStock(boolean seleccionStock) {
        this.seleccionStock = seleccionStock;
        if (seleccionStock) {
            selectedProduct.setStock((short) 0);
        } else {
            selectedProduct.setStock((short) 1);
        }

    }

    public ArrayList<Product> getListLazyRows() {
        return listLazyRows;
    }

    public void setListLazyRows(ArrayList<Product> listLazyRows) {
        this.listLazyRows = listLazyRows;
    }

    public boolean isActivarBtnAdd() {
        return activarBtnAdd;
    }

    public void setActivarBtnAdd(boolean activarBtnAdd) {
        this.activarBtnAdd = activarBtnAdd;
    }

    public boolean isMostrarBotonDownload() {
        return mostrarBotonDownload;
    }

    public void setMostrarBotonDownload(boolean mostrarBotonDownload) {
        this.mostrarBotonDownload = mostrarBotonDownload;
    }

    public boolean isMostrarBotonExportExcel() {
        return mostrarBotonExportExcel;
    }

    public void setMostrarBotonExportExcel(boolean mostrarBotonExportExcel) {
        this.mostrarBotonExportExcel = mostrarBotonExportExcel;
    }

    public boolean isMostrarBotonExportPdf() {
        return mostrarBotonExportPdf;
    }

    public void setMostrarBotonExportPdf(boolean mostrarBotonExportPdf) {
        this.mostrarBotonExportPdf = mostrarBotonExportPdf;
    }

    public boolean isMostrarLazy() {
        return mostrarLazy;
    }

    public void setMostrarLazy(boolean mostrarLazy) {
        this.mostrarLazy = mostrarLazy;
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
        if (filtroFecha2 == null) {
            filtroFecha2 = new Date();
        }
        return filtroFecha2;
    }

    public void setFiltroFecha2(Date filtroFecha2) {
        this.filtroFecha2 = filtroFecha2;
    }

    public void cambiarFecha() {
        fecha1Ingresada = false;
    }

    public boolean isMostrarDataTable() {
        return mostrarDataTable;
    }

    public void setMostrarDataTable(boolean mostrarDataTable) {
        this.mostrarDataTable = mostrarDataTable;
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

    public List<Lugar> getListaLugares() {
        return listaLugares;
    }

    public void setListaLugares(List<Lugar> listaLugares) {
        this.listaLugares = listaLugares;
    }

    public int getPosicionTab() {
        return posicionTab;
    }

    public void setPosicionTab(int posicionTab) {
        this.posicionTab = posicionTab;
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

    public boolean isFiltroActivo() {
        return filtroActivo;
    }

    public void setFiltroActivo(boolean filtroActivo) {
        this.filtroActivo = filtroActivo;
    }

    public List<Product> getListProductsFiltroFilterValue() {
        return listProductsFiltroFilterValue;
    }

    public void setListProductsFiltroFilterValue(List<Product> listProductsFiltroFilterValue) {
        this.listProductsFiltroFilterValue = listProductsFiltroFilterValue;
    }

    public List<Lugar> getListaLugaresFilterValue() {
        return listaLugaresFilterValue;
    }

    public void setListaLugaresFilterValue(List<Lugar> listaLugaresFilterValue) {
        this.listaLugaresFilterValue = listaLugaresFilterValue;
    }

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public LazyDataModel<Product> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Product> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    public boolean isGlobalFilterOnly() {
        return globalFilterOnly;
    }

    public void setGlobalFilterOnly(boolean globalFilterOnly) {
        this.globalFilterOnly = globalFilterOnly;
    }

    public ArrayList<Product> getFilteredGlobalProduct() {
        return filteredGlobalProduct;
    }

    public void setFilteredGlobalProduct(ArrayList<Product> filteredGlobalProduct) {
        this.filteredGlobalProduct = filteredGlobalProduct;
    }

    public List<Palet> getListadoPalets() {
        return listadoPalets;
    }

    public void setListadoPalets(List<Palet> listadoPalets) {
        this.listadoPalets = listadoPalets;
    }

    public Palet getPalet() {
        return palet;
    }

    public void setPalet(Palet palet) {
        this.palet = palet;
    }

    public String getSsccIntroducido() {
        return ssccIntroducido;
    }

    public void setSsccIntroducido(String ssccIntroducido) {
        this.ssccIntroducido = ssccIntroducido;
    }

    public List<PaletLinea> getListadoLineasPalet() {
        return listadoLineasPalet;
    }

    public void setListadoLineasPalet(List<PaletLinea> listadoLineasPalet) {
        this.listadoLineasPalet = listadoLineasPalet;
    }

    public List<Product> getListProductsLazyFiltered() {
        return listProductsLazyFiltered;
    }

    public void setListProductsLazyFiltered(List<Product> listProductsLazyFiltered) {
        this.listProductsLazyFiltered = listProductsLazyFiltered;
    }

    // </editor-fold>
    @PostConstruct
    public void init() {
        peticion = new PeticionJSON();
        selectedProduct = new Product();
        listaProducts = new ArrayList<>();
        fecha1Ingresada = true;
        barChartModel = new BarChartModel();
        listadoPalets = new ArrayList<>();
        listLazyRows = new ArrayList<>();

        try {
            listaProducts.add(new Product("f230fh0g3", "Bamboo", new Categoria(1, "Accessories"), 5, 25.50, sdf.parse("2024/01/04"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("nvklal433", "Black Watch", new Categoria(2, "Electronics"), 5, 5.50, sdf.parse("2024/01/01"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("zz21cz3c1", "Blue Band", new Categoria(3, "Fitness"), 2, 50.50, sdf.parse("2002/03/23"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("244wgerg2", "Blue T-Shirt",new Categoria(4, "Clothing"), 25, 100, sdf.parse("2010/12/01"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("8sn329d", "Red Headphones", new Categoria(2, "Electronics"), 0, 49.99, sdf.parse("2018/06/15"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("acv435s", "Silver Necklace", new Categoria(1, "Accessories"), 15, 79.99, sdf.parse("2017/10/10"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("1plm09s", "Green Yoga Mat",  new Categoria(3, "Fitness"), 30, 29.99, sdf.parse("2019/04/05"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("q1p9n3m", "Leather Jacket", new Categoria(4, "Clothing"), 0, 199.99, sdf.parse("2015/11/20"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("dk39n2p", "Wireless Mouse", new Categoria(2, "Electronics"), 20, 19.99, sdf.parse("2020/03/02"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("3km4n9s", "Running Shoes", new Categoria(5,"Footwear"), 10, 79.99, sdf.parse("2021/09/12"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("fg42k9s", "Stainless Steel Water Bottle", new Categoria(1, "Accessories"), 20, 15.99, sdf.parse("2023/07/20"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("12poq93", "Smartphone Holder", new Categoria(2, "Electronics"), 15, 9.99, sdf.parse("2022/11/10"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("klsdj90", "Yoga Block",  new Categoria(3, "Fitness"), 30, 10.50, sdf.parse("2023/03/05"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("qowpq3l", "Denim Jeans", new Categoria(4, "Clothing"), 30, 49.99, sdf.parse("2023/08/15"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("poiu876", "Portable Bluetooth Speaker", new Categoria(2, "Electronics"), 25, 29.99, sdf.parse("2022/06/20"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("zxcm32l", "Dumbbell Set",  new Categoria(3, "Fitness"), 20, 99.99, sdf.parse("2022/01/10"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("sd9fj23", "Hiking Boots", new Categoria(5,"Footwear"), 15, 129.99, sdf.parse("2024/05/05"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("nmbv34d", "Leather Wallet", new Categoria(1, "Accessories"), 10, 39.99, sdf.parse("2023/09/30"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("1iowq3d", "Wireless Earbuds", new Categoria(2, "Electronics"), 20, 79.99, sdf.parse("2021/12/12"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("alw32fl", "Yoga Mat Bag",  new Categoria(3, "Fitness"), 15, 19.99, sdf.parse("2023/02/18"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("fgo54n3", "Cotton T-Shirt", new Categoria(4, "Clothing"), 30, 19.99, sdf.parse("2024/03/24"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("jfdp32n", "Gaming Mouse", new Categoria(2, "Electronics"), 25, 49.99, sdf.parse("2022/07/09"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("qwpoi89", "Fitness Tracker",  new Categoria(3, "Fitness"), 20, 69.99, sdf.parse("2023/08/03"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("sdmz32l", "Sports Watch", new Categoria(1, "Accessories"), 20, 29.99, sdf.parse("2023/10/11"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("pokm98s", "Bluetooth Headphones", new Categoria(2, "Electronics"), 15, 99.99, sdf.parse("2022/05/17"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("awod32l", "Jump Rope",  new Categoria(3, "Fitness"), 25, 12.99, sdf.parse("2023/11/22"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("qweo87n", "Leather Belt", new Categoria(4, "Clothing"), 20, 24.99, sdf.parse("2023/01/01"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("mnzl23l", "Portable Power Bank", new Categoria(2, "Electronics"), 30, 39.99, sdf.parse("2022/09/29"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("zckm32l", "Resistance Bands Set",  new Categoria(3, "Fitness"), 20, 29.99, sdf.parse("2023/04/14"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("sdfg87n", "Canvas Backpack", new Categoria(1, "Accessories"), 15, 34.99, sdf.parse("2023/06/05"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("zxcv123", "Sunglasses", new Categoria(1, "Accessories"), 30, 19.99, sdf.parse("2024/07/15"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("qwer456", "Smartwatch", new Categoria(2, "Electronics"), 25, 79.99, sdf.parse("2023/10/20"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("lkjh789", "Exercise Ball",  new Categoria(3, "Fitness"), 20, 14.99, sdf.parse("2024/05/05"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("uiop321", "Hoodie", new Categoria(4, "Clothing"), 25, 39.99, sdf.parse("2023/12/10"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("bnmq654", "USB Flash Drive", new Categoria(2, "Electronics"), 40, 9.99, sdf.parse("2022/08/22"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("plko987", "Resistance Bands Set",  new Categoria(3, "Fitness"), 15, 24.99, sdf.parse("2023/02/18"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("jhgf654", "Messenger Bag", new Categoria(1, "Accessories"), 20, 29.99, sdf.parse("2024/04/03"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("vcxz321", "External Hard Drive", new Categoria(2, "Electronics"), 20, 69.99, sdf.parse("2022/11/12"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("poiuy098", "Yoga Pants",  new Categoria(3, "Fitness"), 30, 29.99, sdf.parse("2024/03/28"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("mnbv789", "Scarf", new Categoria(4, "Clothing"), 20, 19.99, sdf.parse("2023/09/15"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("lkjh098", "Wireless Charger", new Categoria(2, "Electronics"), 25, 34.99, sdf.parse("2022/07/06"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("poiuy456", "Foam Roller",  new Categoria(3, "Fitness"), 15, 19.99, sdf.parse("2023/10/11"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("qazx123", "Beanie", new Categoria(1, "Accessories"), 20, 14.99, sdf.parse("2023/11/29"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("wsxc456", "Bluetooth Speaker", new Categoria(2, "Electronics"), 30, 49.99, sdf.parse("2022/05/08"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("edcv789", "Exercise Mat",  new Categoria(3, "Fitness"), 20, 24.99, sdf.parse("2024/02/17"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("rfvb321", "Socks", new Categoria(4, "Clothing"), 35, 9.99, sdf.parse("2023/10/24"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("tgbn654", "Power Strip",new Categoria(2, "Electronics"), 20, 19.99, sdf.parse("2022/06/05"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("yhnm098", "Resistance Loop Bands",  new Categoria(3, "Fitness"), 25, 19.99, sdf.parse("2023/09/14"), (short) 1, obtenerLugares()));
            listaProducts.add(new Product("ujmi123", "Hat", new Categoria(1, "Accessories"), 30, 12.99, sdf.parse("2023/12/20"), (short) 0, obtenerLugares()));
            listaProducts.add(new Product("qwer789", "Wireless Keyboard", new Categoria(2, "Electronics"), 20, 29.99, sdf.parse("2022/08/01"), (short) 1, obtenerLugares()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        totalPrecioAño();

    }

    public void createBarModel(ArrayList<Product> lista) {
        if (posicionTab == 1) {
            barChartModel = new BarChartModel();
            ChartData data = new ChartData();

            BarChartDataSet barDataSet = new BarChartDataSet();
            barDataSet.setLabel("Precios");

            List<Number> values = new ArrayList<>();
            for (Product p : lista) {
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
            for (Product p : lista) {
                labels.add(p.getName());
            }
            data.setLabels(labels);
            barChartModel.setData(data);
        }

    }

    public void openNew() {
        selectedProduct = new Product();
        editando = false;
        selectedCategory = null;
        selectedDate = null;
    }

    public void addProduct() {
        if (listProductsFiltro.isEmpty()) {
            showError("Actualice la tabla antes");
        } else {
            if (!editando) {
                if (selectedProduct.getCode() == null || selectedProduct.getCode().isEmpty()) {
                    showError("El código no es correcto o está vacío");
                } else if (selectedProduct.getName().isEmpty()) {
                    showError("El nombre no es correcto o está vacío");
                } else if (selectedProduct.getCategory() == null || selectedProduct.getCategory().getCod() <= 0) {
                    showError("La categoría no es correcta o está vacía");
                } else if (selectedProduct.getQuantity() < 0) {
                    showError("La cantidad no puede ser menor de 0");
                } else if (selectedProduct.getBirth() == null || selectedProduct.getBirth().toString().equals("")) {
                    showError("Introduzca la fecha");
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
                        if (seleccionStock) {
                            selectedProduct.setStock((short) 0);
                        } else {
                            selectedProduct.setStock((short) 1);
                        }

                        listaProducts.add(selectedProduct);
                        showMessaggeGood("Se ha añadido un producto");
                        PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                        mostrarTablaFiltro();

                    }
                }
            } else {
                for (Product product : listaProducts) {
                    if (product.getCode().equals(selectedProduct.getCode())) {
                        product = selectedProduct;
                        PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                        showMessaggeGood("Se ha editado el producto correctamente");
                    }
                }
            }
        }
        totalPrecioAño();

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

        PrimeFaces.current().ajax().update("mainForm");

        showMessaggeGood("Producto eliminado con éxito");
    }

    public void deleteProductsList(ArrayList<Product> list) {
        for (Product p : list) {
            listaProducts.remove(p);
        }
        listLazyRows = new ArrayList<>();
        mostrarTablaFiltro();

        PrimeFaces.current().ajax().update("mainForm");

        showMessaggeGood("Producto eliminado con éxito");
    }

    public void deleteLugar(Lugar l) {
        selectedProduct.getLugares().remove(l);
        listaLugares.remove(l);
        actualizarOrden();
        showMessaggeGood("Lugar eliminado con éxito");
        PrimeFaces.current().ajax().update("mainForm");
    }

    public void deleteLugarExpansion(Lugar l, Product p) {
        p.getLugares().remove(l);
        showMessaggeGood("Lugar eliminado con éxito");
        PrimeFaces.current().ajax().update("mainForm");
    }

    public List<String> getAvailableCategories() {
        Set<String> categoriesSet = new HashSet<>();
        for (Product product : listaProducts) {
            categoriesSet.add(product.getCategory().getNombre());
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

        Collections.shuffle(listaLugares);

        for (int i = 0; i < 3; i++) {
            lugaresAleatorios.add(listaLugares.get(i));
            lugaresAleatorios.get(i).setOrden(i + 1);
        }

        return lugaresAleatorios;
    }

    public void onRowSelect() {
        if (selectedProduct != null) {
            listaLugares = selectedProduct.getLugares();
            mostrarTablaLugares = true;
            PrimeFaces.current().executeScript("PF('dataTableLugares').clearFilters();");

        }
    }

    public void handleTabChange(AjaxBehaviorEvent event) {
        switch (posicionTab) {
            case 0:
                mostrarBotonDownload = true;
                mostrarBotonExportExcel = true;
                mostrarBotonExportPdf = true;
                activarBtnAdd = false;
                break;
            case 1:
                mostrarBotonDownload = false;
                mostrarBotonExportExcel = false;
                mostrarBotonExportPdf = false;
                activarBtnAdd = true;

                PrimeFaces.current().ajax().update("mainForm");
                break;
            case 2:
                mostrarBotonDownload = false;
                mostrarBotonExportExcel = false;
                mostrarBotonExportPdf = false;
                activarBtnAdd = true;
                break;
            case 3:
                mostrarBotonDownload = false;
                mostrarBotonExportExcel = false;
                mostrarBotonExportPdf = false;
                activarBtnAdd = true;
                break;
            default:
                break;
        }
    }

    public void mostrarTablaFiltro() {
        listProductsFiltro = null;
        listaLugares = null;

        switch (posicionTab) {
            case 0:
                mostrarDataTable = true;
                mostrarTablaLugares = true;
                activarBtnAdd = false;
                mostrarLazy = false;
                listProductsFiltro = new ArrayList<>();
                ArrayList<Product> p = new ArrayList<>();
                listaLugares = new ArrayList<>();
                lazyModel = new LazyCustomerDataModel(p);

                if (selectedCategory != null && filtroFecha2 != null) {
                    if (filtroFecha1 == null) {
                        hayFiltro = true;
                        Date fechaActual = new Date();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaActual);

                        calendar.add(Calendar.DAY_OF_YEAR, -31);

                        filtroFecha1 = calendar.getTime();
                    }
                    for (Product product : listaProducts) {
                        if (selectedCategory.equals(product.getCategory()) && comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else if (selectedCategory != null && filtroFecha1 == null) {
                    hayFiltro = true;
                    for (Product product : listaProducts) {
                        if (selectedCategory.equals(product.getCategory())) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else if (selectedCategory == null && filtroFecha1 != null && filtroFecha2 != null) {
                    hayFiltro = true;
                    for (Product product : listaProducts) {
                        if (comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else {
                    hayFiltro = false;
                    listProductsFiltro.addAll(listaProducts);
                }
                if (!listProductsFiltro.isEmpty()) {
                    selectedProduct = listProductsFiltro.get(0);
                    onRowSelect();
                } else {
                    selectedProduct = null;
                    showError("No se ha encontrado ningún resultado");
                }
                PF.current().executeScript("PF('dataTable').clearFilters();");

                PrimeFaces.current().ajax().update("mainForm");
                break;
            case 1:

                listProductsFiltro = new ArrayList();
                listaLugares = new ArrayList();
                lazyModel = new LazyCustomerDataModel(listProductsFiltro);
                activarBtnAdd = true;
                if (selectedCategory != null && filtroFecha2 != null) {
                    if (filtroFecha1 == null) {
                        hayFiltro = true;
                        Date fechaActual = new Date();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaActual);

                        calendar.add(Calendar.DAY_OF_YEAR, -31);

                        filtroFecha1 = calendar.getTime();
                    }
                    for (Product product : listaProducts) {
                        if (selectedCategory.equals(product.getCategory()) && comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else if (selectedCategory != null && filtroFecha1 == null) {
                    hayFiltro = true;
                    for (Product product : listaProducts) {
                        if (selectedCategory.equals(product.getCategory())) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else if (selectedCategory == null && filtroFecha1 != null && filtroFecha2 != null) {
                    hayFiltro = true;
                    for (Product product : listaProducts) {
                        if (comprobarFechas(product.getBirth(), filtroFecha1, filtroFecha2)) {
                            listProductsFiltro.add(product);
                        }
                    }
                } else {
                    hayFiltro = false;
                    listProductsFiltro.addAll(listaProducts);
                }
                if (!listProductsFiltro.isEmpty()) {
                    selectedProduct = listProductsFiltro.get(0);
                    onRowSelect();
                } else {
                    selectedProduct = null;
                    showError("No se ha encontrado ningún resultado");
                }
                createBarModel((ArrayList<Product>) listProductsFiltro);
                mostrarDataTable = false;
                mostrarTablaLugares = false;
                mostrarLazy = false;
                PrimeFaces.current().ajax().update("mainForm:tabPanel:barChart");
                PrimeFaces.current().ajax().update("mainForm");

                break;
            case 2:
                barChartModel = new BarChartModel();
                activarBtnAdd = true;
                mostrarLazy = true;

                listProductsFiltro = new ArrayList<>();

                if (selectedCategory != null && filtroFecha2 != null) {
                    if (filtroFecha1 == null) {
                        Date fechaActual = new Date();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaActual);

                        calendar.add(Calendar.DAY_OF_YEAR, -31);

                        filtroFecha1 = calendar.getTime();
                    }
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
                lazyModel = new LazyCustomerDataModel(listProductsFiltro);
                mostrarTablaLugares = false;
                mostrarDataTable = false;

                if (listProductsFiltro.isEmpty()) {
                    showError("No se ha encontrado ningún resultado");
                }
                PrimeFaces.current().ajax().update("mainForm:");
                listProductsFiltro = null;
                break;

            default:
                break;
        }
    }

    public static String formatProductBirth(Date birthDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(birthDate);
    }

    public boolean comprobarFechas(Date fecha, Date fechaFiltro1, Date fechaFiltro2) {
        return !fecha.before(fechaFiltro1) && !fecha.after(fechaFiltro2);
    }

    private void applyStyleToRow(Row row, CellStyle style) {
        for (Cell cell : row) {
            cell.setCellStyle(style);
        }
    }

    public void rowReorder(ReorderEvent event) {
        if (listaLugaresFilterValue.size() == listaLugares.size()) {
            Object elementoMovido = listaLugares.remove(event.getFromIndex());
            listaLugares.add(event.getToIndex(), (Lugar) elementoMovido);
            actualizarOrden();
        }
    }

    private void actualizarOrden() {
        for (int i = 0; i < listaLugares.size(); i++) {
            listaLugares.get(i).setOrden(i + 1);
        }
    }

    public boolean hayRegistros() {
        return (!listProductsFiltro.isEmpty());
    }

    public void filtrado(FilterEvent event) {
        setFiltroActivo(false);
    }

    public void limpiarFiltro() {
        setFiltroActivo(false);
        PrimeFaces.current().executeScript("PF('dataTableLugares').clearFilters();");
    }

    public void preProcessPDF(Object document) {
        Document doc = (Document) document;
        doc.setPageSize(PageSize.A4.rotate());
    }

    public void onTabChange(TabChangeEvent event) {
        showMessaggeGood(event.getTab().getTitle());
    }

    public void onTabClose(TabCloseEvent event) {
        showMessaggeGood(event.getTab().getTitle());
    }

    public void totalPrecioAño() {
        totalPrecio = 0;
        for (Product p : listaProducts) {
            totalPrecio += p.getPrice();
        }

    }

    public void chooseLugar() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(false)
                .draggable(true)
                .modal(true)
                .build();

        PrimeFaces.current().dialog().openDynamic("selectCity", options, null);
    }

    public void onLugarChosen(SelectEvent event) {
        ArrayList<Lugar> lugaresNuevos = (ArrayList<Lugar>) event.getObject();
        List<Lugar> lugaresActuales = selectedProduct.getLugares();
        List<String> lugaresAgregados = new ArrayList<>();
        List<String> lugaresNoAgregados = new ArrayList<>();

        for (Lugar nuevoLugar : lugaresNuevos) {
            boolean existe = false;
            for (Lugar lugarExistente : lugaresActuales) {
                if (nuevoLugar.getId() == lugarExistente.getId()) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                lugaresActuales.add(nuevoLugar);
                lugaresAgregados.add(String.valueOf(nuevoLugar.getId()));
            } else {
                lugaresNoAgregados.add(String.valueOf(nuevoLugar.getId()));
            }
        }

        if (!lugaresAgregados.isEmpty()) {
            StringBuilder messageAgregados = new StringBuilder("Se han añadido los siguientes lugares correctamente: ");
            for (String lugarAgregado : lugaresAgregados) {
                messageAgregados.append(lugarAgregado).append(", ");
            }
            messageAgregados.setLength(messageAgregados.length() - 2);
            showMessaggeGood(messageAgregados.toString());
        }

        if (!lugaresNoAgregados.isEmpty()) {
            StringBuilder messageNoAgregados = new StringBuilder("Los siguientes lugares no se han añadido porque ya estaban presentes: ");
            for (String lugarNoAgregado : lugaresNoAgregados) {
                messageNoAgregados.append(lugarNoAgregado).append(", ");
            }
            messageNoAgregados.setLength(messageNoAgregados.length() - 2);
            showError(messageNoAgregados.toString());
        }

        actualizarOrden();
        PF.current().ajax().update("mainForm");
    }

    public void exportToExcel() throws IOException {

        int numRow = 0;

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 3000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 3000);

            if (!hayFiltro) {
                Row headerRow = sheet.createRow(0);
                String[] columns = {"Code", "Name", "Category", "Quantity", "Price", "Date", "Stock"};
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                }

                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle redRowStyle = workbook.createCellStyle();
                redRowStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                redRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle greenRowStyle = workbook.createCellStyle();
                greenRowStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                greenRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle precioCellStyle = workbook.createCellStyle();
                precioCellStyle.setAlignment(HorizontalAlignment.RIGHT);

                for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                    headerRow.getCell(i).setCellStyle(headerCellStyle);
                }

                NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

                int rowNum = 1;
                for (Product product : listProductsFiltro) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getCode());
                    row.createCell(1).setCellValue(product.getName());
                    row.createCell(2).setCellValue(product.getCategory().getNombre());
                    row.createCell(3).setCellValue(product.getQuantity());
                    row.createCell(4).setCellValue(formatoImporte.format(product.getPrice()));
                    row.createCell(5).setCellValue(sdf.format(product.getBirth()));
                    String val = (product.getStock() == 0) ? "Si" : "No";
                    row.createCell(6).setCellValue(val);

                    if (product.getQuantity() == 0) {
                        applyStyleToRow(row, redRowStyle);
                    } else if (product.getQuantity() >= 20) {
                        applyStyleToRow(row, greenRowStyle);
                    }

                }

                CellRangeAddress range = new CellRangeAddress(0, rowNum - 1, 0, 6);
                sheet.setAutoFilter(range);

                Row totalRow = sheet.createRow(rowNum);

                totalRow.createCell(3).setCellValue("Total");
                totalRow.createCell(4).setCellValue(formatoImporte.format(totalPrecio));
            } else {
                Row headerRow = sheet.createRow(0);
                if (filtroFecha1 != null) {
                    headerRow.createCell(0).setCellValue("Filtro fecha inicio");
                    headerRow.createCell(1).setCellValue(filtroFecha1);
                }
                if (filtroFecha2 != null) {
                    headerRow.createCell(2).setCellValue("Filtro fecha fin");
                    headerRow.createCell(3).setCellValue(filtroFecha2);
                }
                if (selectedCategory != null) {
                    headerRow.createCell(4).setCellValue("Filtro categoria");
                    headerRow.createCell(5).setCellValue(selectedCategory);
                }

                Row headerTable = sheet.createRow(2);
                String[] columns = {"Code", "Name", "Category", "Quantity", "Price", "Date", "Stock"};
                for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerTable.createCell(i);
                    cell.setCellValue(columns[i]);
                }

                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle redRowStyle = workbook.createCellStyle();
                redRowStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                redRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle greenRowStyle = workbook.createCellStyle();
                greenRowStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                greenRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                CellStyle precioCellStyle = workbook.createCellStyle();
                precioCellStyle.setAlignment(HorizontalAlignment.RIGHT);

                for (int i = 0; i < headerTable.getLastCellNum(); i++) {
                    headerTable.getCell(i).setCellStyle(headerCellStyle);
                }

                NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

                int rowNum = 3;
                for (Product product : listProductsFiltro) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(product.getCode());
                    row.createCell(1).setCellValue(product.getName());
                    row.createCell(2).setCellValue(product.getCategory().getNombre());
                    row.createCell(3).setCellValue(product.getQuantity());
                    row.createCell(4).setCellValue(formatoImporte.format(product.getPrice()));
                    row.createCell(5).setCellValue(sdf.format(product.getBirth()));
                    String val = (product.getStock() == 0) ? "Si" : "No";
                    row.createCell(6).setCellValue(val);

                    if (product.getQuantity() == 0) {
                        applyStyleToRow(row, redRowStyle);
                    } else if (product.getQuantity() >= 20) {
                        applyStyleToRow(row, greenRowStyle);
                    }

                }

                CellRangeAddress range = new CellRangeAddress(2, rowNum + 1, 0, 6);
                sheet.setAutoFilter(range);

                Row totalRow = sheet.createRow(rowNum);

                totalRow.createCell(3).setCellValue("Total");
                totalRow.createCell(4).setCellValue(formatoImporte.format(totalPrecio));
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

    public void exportToZip() throws IOException {
        try (ByteArrayOutputStream zipBuffer = new ByteArrayOutputStream(); ZipOutputStream zipOut = new ZipOutputStream(zipBuffer)) {

            File fileExcel = excel2();
            String zipFileName = getCurrentDateTime() + ".zip";

            ZipEntry excelEntry = new ZipEntry(fileExcel.getName());
            zipOut.putNextEntry(excelEntry);
            try (FileInputStream fis = new FileInputStream(fileExcel)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, length);
                }
            }
            File fileExcelLugares = excelLugares();

            ZipEntry excelEntryLugares = new ZipEntry(fileExcelLugares.getName());
            zipOut.putNextEntry(excelEntryLugares);
            try (FileInputStream fis = new FileInputStream(fileExcelLugares)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zipOut.write(buffer, 0, length);
                }
            }
            zipOut.closeEntry();

            zipOut.finish();
            byte[] zipContent = zipBuffer.toByteArray();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;filename=" + zipFileName);
            response.setContentLength(zipContent.length);
            response.getOutputStream().write(zipContent);
            FacesContext.getCurrentInstance().responseComplete();
        }
    }

    public File excel2() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 3000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 4000);
            sheet.setColumnWidth(6, 3000);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Code", "Name", "Category", "Quantity", "Price", "Date", "Stock"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle redRowStyle = workbook.createCellStyle();
            redRowStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            redRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle greenRowStyle = workbook.createCellStyle();
            greenRowStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            greenRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle precioCellStyle = workbook.createCellStyle();
            precioCellStyle.setAlignment(HorizontalAlignment.RIGHT);

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headerRow.getCell(i).setCellStyle(headerCellStyle);
            }

            NumberFormat formatoImporte = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));

            int rowNum = 1;
            for (Product product : listProductsFiltro) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getCode());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getCategory().getNombre());
                row.createCell(3).setCellValue(product.getQuantity());
                row.createCell(4).setCellValue(formatoImporte.format(product.getPrice()));
                row.createCell(5).setCellValue(sdf.format(product.getBirth()));
                String val = (product.getStock() == 0) ? "Si" : "No";
                row.createCell(6).setCellValue(val);

                if (product.getQuantity() == 0) {
                    applyStyleToRow(row, redRowStyle);
                } else if (product.getQuantity() >= 20) {
                    applyStyleToRow(row, greenRowStyle);
                }

            }

            CellRangeAddress range = new CellRangeAddress(0, rowNum - 1, 0, 6);
            sheet.setAutoFilter(range);

            Row totalRow = sheet.createRow(rowNum);

            totalRow.createCell(3).setCellValue("Total");
            totalRow.createCell(4).setCellValue(formatoImporte.format(totalPrecio));

            workbook.write(out);

            File excelFile = new File(System.getProperty("java.io.tmpdir"), selectedProduct.getName() + ".xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                out.writeTo(fileOut);
            }

            return excelFile;
        }

    }

    public boolean convertirStock(Product p) {
        return p.getStock() == 0;
    }

    public File excelLugares() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Products");
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Id", "Ciudad", "Pais"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (Lugar l : selectedProduct.getLugares()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(l.getId());
                row.createCell(1).setCellValue(l.getCiudad());
                row.createCell(2).setCellValue(l.getPais());
            }

            workbook.write(out);

            File excelFileLugares = new File(System.getProperty("java.io.tmpdir"), "Lugares.xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(excelFileLugares)) {
                out.writeTo(fileOut);
            }
            return excelFileLugares;
        }

    }

    private static String getCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMHHmm");
        return currentDateTime.format(formatter);
    }

    public void buscarPaletPorSSCC() throws MalformedURLException {
        ssccIntroducido = ssccIntroducido.trim();
        if (!ssccIntroducido.equals("")) {
            peticion.hacerLlamada(ssccIntroducido);

            listadoPalets = peticion.getPaletsRespuesta().getPalets();

            if (listadoPalets.isEmpty()) {
                showError("No se han encontrado palet por ese codigo");
                PrimeFaces.current().ajax().update("mainForm");

            } else {
                palet = listadoPalets.get(0);
                listadoLineasPalet = palet.getLineas();
                openDialogPaletArticulo();
                System.out.println(palet);
            }
        } else {
            showError("El SSCC introducido no es correcto");
            PrimeFaces.current().ajax().update("mainForm");

        }

    }

    public void openDialogPaletArticulo() {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(true)
                .draggable(true)
                .modal(true)
                .width("95%")
                .height("95%")
                .contentWidth("100%")
                .contentHeight("100%")
                .build();

        String[] paletDetailList = {String.valueOf(ssccIntroducido)};
        String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
        String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
        Map<String, List<String>> params = new HashMap<>();
        params.put("palet", Arrays.asList(paletDetail));

        PrimeFaces.current().dialog().openDynamic("dialogPalet", options, params);
    }

}
