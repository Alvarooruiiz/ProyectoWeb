/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPanel;

import es.inerttia.ittws.controllers.CentroAlmacenController;
import es.inerttia.ittws.controllers.CentroController;
import es.inerttia.ittws.controllers.ColaTrabajoController;
import es.inerttia.ittws.controllers.HuecoController;
import es.inerttia.ittws.controllers.PedidoSalidaController;
import es.inerttia.ittws.controllers.entities.Centro;
import es.inerttia.ittws.controllers.entities.CentroAlmacen;
import es.inerttia.ittws.controllers.entities.EmpaquetadoActualDetalle;
import es.inerttia.ittws.controllers.entities.NivelClasificacion;
import es.inerttia.ittws.controllers.entities.PedidoSalidaClasificacion;
import es.inerttia.ittws.controllers.entities.TipoPedido;
import es.inerttia.ittws.controllers.entities.custom.ArticuloPendiente;
import es.inerttia.ittws.controllers.entities.custom.Comun;
import es.inerttia.ittws.controllers.entities.custom.PedidoSalida;
import es.inerttia.ittws.controllers.entities.custom.PedidoSalidaLinea;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittws.controllers.TerceroController;
import es.inerttia.ittws.controllers.TipoPedidoController;
import es.inerttia.ittws.controllers.entities.Cola;
import es.inerttia.ittws.controllers.entities.TerceroCentro;
import es.inerttia.ittws.controllers.entities.custom.Respuesta;
import es.inerttia.ittws.controllers.entities.custom.Zona;
import es.inerttia.ittwsEntidades.wsAlmacen.Pedido;
import es.inerttia.ittwscomun.Configuracion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import static org.primefaces.behavior.validate.ClientValidator.PropertyKeys.event;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DialogFrameworkOptions;

@ManagedBean(name = "ControlPanel")
@ViewScoped
public class ControlPanel {

    private int indexTab;

    private List<Centro> listaCentros;
    private List<CentroAlmacen> listaAlmacenes;

    private List<ArticuloPendiente> pendingItems = new ArrayList();
    private List<ArticuloPendiente> pendingItemsAllCenters = new ArrayList();
    private List<PedidoSalidaLinea> orders = new ArrayList();
    private List<PedidoSalidaLinea> pendingOrders = new ArrayList();
    private List<PedidoSalidaLinea> preparedOrders = new ArrayList();
    private List<PedidoSalidaLinea> verificatedOrders = new ArrayList();
    private List<PedidoSalidaLinea> closedOrders = new ArrayList();
    private List<PedidoSalidaLinea> sentOrders = new ArrayList();
    private List<EmpaquetadoActualDetalle> packedOrders = new ArrayList();

    private ArticuloPendiente pendingItemSelected;
    private PedidoSalidaLinea orderSelected;
    private PedidoSalidaLinea pendingOrderSelected;
    private PedidoSalidaLinea preparedOrderSelected;
    private PedidoSalidaLinea verificatedOrderSelected;
    private PedidoSalidaLinea closedOrderSelected;
    private PedidoSalidaLinea sentOrderSelected;

    private int orderType = 0;
    private int centerId = 1;
    private int warehouseId = 1;

    private int idCenterSearch = 1;
    private int idWarehouseSearch = 1;

    private int totalPendingItems;
    private int totalOrders;
    private int totalPendingOrders;
    private int totalPreparedOrders;
    private int totalVerificatedOrders;
    private int totalClosedOrders;
    private int totalSendOrders;
    private int totalPackedOrders;

    private String idZona = "";
    private String idClasif = "";
    private List<Zona> listaZonaSelected;
    private List<PedidoSalidaClasificacion> listaClasifSelected;
    private String zonaString = "";
    private String clasifString = "";
    private String tipoSelection = "";

    private int totalMaxOrders = 0;

    private CentroAlmacen centroAlmacenSelected;

    private List<es.inerttia.ittws.controllers.entities.custom.PedidoSalida> listPedidos;
    private List<es.inerttia.ittws.controllers.entities.custom.PedidoSalida> listPedidosSelected;

    private Date fechInicPed;
    private Date fechFinPed;

    private Date fechInicSer;
    private Date fechFinSer;

    private Tercero terceroSingle;

    private String terceroString;
    private String centroTerceroString;

    private List<TipoPedido> listaTipologiaPedido;

    private String idCentroTercero;
    private TerceroCentro centroTercero;

    private List<TipoPedido> listaTipologiaPedidosSelected;

    private int lineasPendientes = 0;
    private int lineasPendientesHasta = 0;

    private String selectedOrderTypologies;

    private int radioSelected;

    private List<Cola> listColas;
    private Cola colaSelected;
    private boolean locateOrder;

    private Date fecInic;
    private Date fecFin;

    private int idCola;

    public boolean artPendTodosCentros;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    // <editor-fold defaultstate="collapsed" desc=" getters y setters ">
    public List<Centro> getListaCentros() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        CentroController ctl = new CentroController(conf);
        listaCentros = ctl.getCentros(false, 1);
        conf.cerrar();
        return listaCentros;
    }

    public List<ArticuloPendiente> getPendingItemsAllCenters() {
        return pendingItemsAllCenters;
    }

    public void setPendingItemsAllCenters(List<ArticuloPendiente> pendingItemsAllCenters) {
        this.pendingItemsAllCenters = pendingItemsAllCenters;
    }

    public int getIdCola() {
        return idCola;
    }

    public void setIdCola(int idCola) {
        this.idCola = idCola;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public void setListaCentros(List<Centro> listaCentros) {
        this.listaCentros = listaCentros;
    }

    public boolean isArtPendTodosCentros() {
        return artPendTodosCentros;
    }

    public void setArtPendTodosCentros(boolean artPendTodosCentros) {
        this.artPendTodosCentros = artPendTodosCentros;
    }

    public boolean isLocateOrder() {
        return locateOrder;
    }

    public Date getFecInic() {
        if (fecInic == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2024);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 01);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            fecInic = cal.getTime();
        }

        return fecInic;
    }

    public void setFecInic(Date fecInic) {
        this.fecInic = fecInic;
    }

    public Date getFecFin() {
        if (fecFin == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2024);
            cal.set(Calendar.MONTH, Calendar.MAY);
            cal.set(Calendar.DAY_OF_MONTH, 25);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            fecFin = cal.getTime();
        }

        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public void setLocateOrder(boolean locateOrder) {
        this.locateOrder = locateOrder;
    }

    public Cola getColaSelected() {
        return colaSelected;
    }

    public void setColaSelected(Cola colaSelected) {
        this.colaSelected = colaSelected;
    }

    public List<Cola> getListColas() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        ColaTrabajoController ctl = new ColaTrabajoController(conf);
        listColas = ctl.getColas(centerId, warehouseId, 2);
        conf.cerrar();
        return listColas;
    }

    public void setListColas(List<Cola> listColas) {
        this.listColas = listColas;
    }

    public String getSelectedOrderTypologies() {
        return selectedOrderTypologies;
    }

    public void setSelectedOrderTypologies(String selectedOrderTypologies) {
        this.selectedOrderTypologies = selectedOrderTypologies;
    }

    public int getRadioSelected() {
        return radioSelected;

    }

    public void setRadioSelected(int radioSelected) {
        this.radioSelected = radioSelected;
    }

    public Tercero getTerceroSingle() {
        return terceroSingle;
    }

    public void setTerceroSingle(Tercero terceroSingle) {
        this.terceroSingle = terceroSingle;
    }

    public String getIdCentroTercero() {
        return idCentroTercero;
    }

    public void setIdCentroTercero(String idCentroTercero) {
        this.idCentroTercero = idCentroTercero;
    }

    public TerceroCentro getCentroTercero() {
        return centroTercero;
    }

    public void setCentroTercero(TerceroCentro centroTercero) {
        this.centroTercero = centroTercero;
    }

    public int getLineasPendientes() {
        return lineasPendientes;
    }

    public void setLineasPendientes(int lineasPendientes) {
        this.lineasPendientes = lineasPendientes;
    }

    public int getLineasPendientesHasta() {
        return lineasPendientesHasta;
    }

    public void setLineasPendientesHasta(int lineasPendientesHasta) {
        this.lineasPendientesHasta = lineasPendientesHasta;
    }

    public List<TipoPedido> getListaTipologiaPedido() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        TipoPedidoController ctl = new TipoPedidoController(conf);
        listaTipologiaPedido = ctl.getTiposPedido();
        conf.cerrar();
        return listaTipologiaPedido;
    }

    public void setListaTipologiaPedido(List<TipoPedido> listaTipologiaPedido) {
        this.listaTipologiaPedido = listaTipologiaPedido;
    }

    public List<PedidoSalida> getListPedidos() {
        return listPedidos;
    }

    public List<TipoPedido> getListaTipologiaPedidosSelected() {
        return listaTipologiaPedidosSelected;
    }

    public void setListaTipologiaPedidosSelected(List<TipoPedido> listaTipologiaPedidosSelected) {
        this.listaTipologiaPedidosSelected = listaTipologiaPedidosSelected;
    }

    public Date getFechInicPed() {
        return fechInicPed;
    }

    public void setFechInicPed(Date fechInicPed) {
        this.fechInicPed = fechInicPed;
    }

    public Date getFechFinPed() {
        return fechFinPed;
    }

    public void setFechFinPed(Date fechFinPed) {
        this.fechFinPed = fechFinPed;
    }

    public CentroAlmacen getCentroAlmacenSelected() {
        return centroAlmacenSelected;
    }

    public void setCentroAlmacenSelected(CentroAlmacen centroAlmacenSelected) {
        this.centroAlmacenSelected = centroAlmacenSelected;
    }

    public void setListPedidos(List<PedidoSalida> listPedidos) {
        this.listPedidos = listPedidos;
    }

    public List<PedidoSalida> getListPedidosSelected() {
        return listPedidosSelected;
    }

    public void setListPedidosSelected(List<PedidoSalida> listPedidosSelected) {
        this.listPedidosSelected = listPedidosSelected;
    }

    public Date getFechInicSer() {
        return fechInicSer;
    }

    public void setFechInicSer(Date fechInicSer) {
        this.fechInicSer = fechInicSer;
    }

    public Date getFechFinSer() {
        return fechFinSer;
    }

    public void setFechFinSer(Date fechFinSer) {
        this.fechFinSer = fechFinSer;
    }

    public String getTerceroString() {
        return terceroString;
    }

    public void setTerceroString(String terceroString) {
        this.terceroString = terceroString;
    }

    public String getCentroTerceroString() {
        return centroTerceroString;
    }

    public void setCentroTerceroString(String centroTerceroString) {
        this.centroTerceroString = centroTerceroString;
    }

    public int getTotalMaxOrders() {
        return totalMaxOrders;
    }

    public void setTotalMaxOrders(int totalMaxOrders) {
        this.totalMaxOrders = totalMaxOrders;
    }

    public String getIdZona() {
        return idZona;
    }

    public void setIdZona(String idZona) {
        this.idZona = idZona;
    }

    public String getIdClasif() {
        return idClasif;
    }

    public void setIdClasif(String idClasif) {
        this.idClasif = idClasif;
    }

    public List<Zona> getListaZonaSelected() {
        return listaZonaSelected;
    }

    public void setListaZonaSelected(List<Zona> listaZonaSelected) {
        this.listaZonaSelected = listaZonaSelected;
    }

    public List<PedidoSalidaClasificacion> getListaClasifSelected() {
        return listaClasifSelected;
    }

    public void setListaClasifSelected(List<PedidoSalidaClasificacion> listaClasifSelected) {
        this.listaClasifSelected = listaClasifSelected;
    }

    public String getZonaString() {
        return zonaString;
    }

    public void setZonaString(String zonaString) {
        this.zonaString = zonaString;
    }

    public String getClasifString() {
        return clasifString;
    }

    public void setClasifString(String clasifString) {
        this.clasifString = clasifString;
    }

    public String getTipoSelection() {
        return tipoSelection;
    }

    public void setTipoSelection(String tipoSelection) {
        this.tipoSelection = tipoSelection;
    }

    public List<CentroAlmacen> getListaAlmacenes() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        CentroAlmacenController ctl = new CentroAlmacenController(conf);
        listaAlmacenes = ctl.getCentrosAlmacenByCentroBaja0(centerId, 1);
        conf.cerrar();
        return listaAlmacenes;
    }

    public void setListaAlmacenes(List<CentroAlmacen> listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

    public List<ArticuloPendiente> getPendingItems() {
        return pendingItems;
    }

    public void setPendingItems(List<ArticuloPendiente> pendingItems) {
        this.pendingItems = pendingItems;
    }

    public List<PedidoSalidaLinea> getOrders() {
        return orders;
    }

    public void setOrders(List<PedidoSalidaLinea> orders) {
        this.orders = orders;
    }

    public List<PedidoSalidaLinea> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(List<PedidoSalidaLinea> pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public List<PedidoSalidaLinea> getPreparedOrders() {
        return preparedOrders;
    }

    public void setPreparedOrders(List<PedidoSalidaLinea> preparedOrders) {
        this.preparedOrders = preparedOrders;
    }

    public List<PedidoSalidaLinea> getVerificatedOrders() {
        return verificatedOrders;
    }

    public void setVerificatedOrders(List<PedidoSalidaLinea> verificatedOrders) {
        this.verificatedOrders = verificatedOrders;
    }

    public List<PedidoSalidaLinea> getClosedOrders() {
        return closedOrders;
    }

    public void setClosedOrders(List<PedidoSalidaLinea> closedOrders) {
        this.closedOrders = closedOrders;
    }

    public List<PedidoSalidaLinea> getSentOrders() {
        return sentOrders;
    }

    public void setSentOrders(List<PedidoSalidaLinea> sentOrders) {
        this.sentOrders = sentOrders;
    }

    public List<EmpaquetadoActualDetalle> getPackedOrders() {
        return packedOrders;
    }

    public void setPackedOrders(List<EmpaquetadoActualDetalle> packedOrders) {
        this.packedOrders = packedOrders;
    }

    public ArticuloPendiente getPendingItemSelected() {
        return pendingItemSelected;
    }

    public void setPendingItemSelected(ArticuloPendiente pendingItemSelected) {
        this.pendingItemSelected = pendingItemSelected;
    }

    public PedidoSalidaLinea getOrderSelected() {
        return orderSelected;
    }

    public void setOrderSelected(PedidoSalidaLinea orderSelected) {
        this.orderSelected = orderSelected;
    }

    public PedidoSalidaLinea getPendingOrderSelected() {
        return pendingOrderSelected;
    }

    public void setPendingOrderSelected(PedidoSalidaLinea pendingOrderSelected) {
        this.pendingOrderSelected = pendingOrderSelected;
    }

    public PedidoSalidaLinea getPreparedOrderSelected() {
        return preparedOrderSelected;
    }

    public void setPreparedOrderSelected(PedidoSalidaLinea preparedOrderSelected) {
        this.preparedOrderSelected = preparedOrderSelected;
    }

    public PedidoSalidaLinea getVerificatedOrderSelected() {
        return verificatedOrderSelected;
    }

    public void setVerificatedOrderSelected(PedidoSalidaLinea verificatedOrderSelected) {
        this.verificatedOrderSelected = verificatedOrderSelected;
    }

    public PedidoSalidaLinea getClosedOrderSelected() {
        return closedOrderSelected;
    }

    public void setClosedOrderSelected(PedidoSalidaLinea closedOrderSelected) {
        this.closedOrderSelected = closedOrderSelected;
    }

    public PedidoSalidaLinea getSentOrderSelected() {
        return sentOrderSelected;
    }

    public void setSentOrderSelected(PedidoSalidaLinea sentOrderSelected) {
        this.sentOrderSelected = sentOrderSelected;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getIdCenterSearch() {
        return idCenterSearch;
    }

    public void setIdCenterSearch(int idCenterSearch) {
        this.idCenterSearch = idCenterSearch;
    }

    public int getIdWarehouseSearch() {
        return idWarehouseSearch;
    }

    public void setIdWarehouseSearch(int idWarehouseSearch) {
        this.idWarehouseSearch = idWarehouseSearch;
    }

    public int getTotalPendingItems() {
        return totalPendingItems;
    }

    public void setTotalPendingItems(int totalPendingItems) {
        this.totalPendingItems = totalPendingItems;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalPendingOrders() {
        return totalPendingOrders;
    }

    public void setTotalPendingOrders(int totalPendingOrders) {
        this.totalPendingOrders = totalPendingOrders;
    }

    public int getTotalPreparedOrders() {
        return totalPreparedOrders;
    }

    public void setTotalPreparedOrders(int totalPreparedOrders) {
        this.totalPreparedOrders = totalPreparedOrders;
    }

    public int getTotalVerificatedOrders() {
        return totalVerificatedOrders;
    }

    public void setTotalVerificatedOrders(int totalVerificatedOrders) {
        this.totalVerificatedOrders = totalVerificatedOrders;
    }

    public int getTotalClosedOrders() {
        return totalClosedOrders;
    }

    public void setTotalClosedOrders(int totalClosedOrders) {
        this.totalClosedOrders = totalClosedOrders;
    }

    public int getTotalSendOrders() {
        return totalSendOrders;
    }

    public void setTotalSendOrders(int totalSendOrders) {
        this.totalSendOrders = totalSendOrders;
    }

    public int getTotalPackedOrders() {
        return totalPackedOrders;
    }

    public void setTotalPackedOrders(int totalPackedOrders) {
        this.totalPackedOrders = totalPackedOrders;
    }

    public int getIndexTab() {
        return indexTab;
    }

    public void setIndexTab(int indexTab) {
        this.indexTab = indexTab;
    }

    // </editor-fold>
    public void buscar() {
        PrimeFaces.current().ajax().update("formControlPanel");
        Configuracion conf = new Configuracion();
        es.inerttia.ittws.controllers.CuadroMandosController ctl = new es.inerttia.ittws.controllers.CuadroMandosController(conf);
        switch (indexTab) {
            case 0:

                pendingItems = ctl.getArticulosPendientesCuadroMandos(orderType, centerId, warehouseId);
                conf.cerrar();
                orders.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();

                break;

            case 1:

                orders = ctl.getPedidos(orderType, centerId, warehouseId, false);
                conf.cerrar();
                pendingItems.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();

                break;

            case 2:

                pendingOrders = ctl.getPedidos(orderType, centerId, warehouseId, true);
                conf.cerrar();
                pendingItems.clear();
                orders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();

                break;

            case 3:

                preparedOrders = ctl.getPedidosPreparacion(orderType, centerId, warehouseId);
                conf.cerrar();
                pendingItems.clear();
                orders.clear();
                pendingOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();

                break;

            case 4:

                verificatedOrders = ctl.getPedidosVerificacion(orderType, centerId, warehouseId);
                conf.cerrar();
                orders.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();

                break;

            case 5:

                closedOrders = ctl.getPedidosCerrados(orderType, centerId, warehouseId);
                conf.cerrar();
                orders.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                sentOrders.clear();
                packedOrders.clear();
                break;

            case 6:

                sentOrders = ctl.getPedidosEnviados(orderType, centerId, warehouseId);
                conf.cerrar();
                orders.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                packedOrders.clear();
                break;

            case 7:

                packedOrders = ctl.getPedidosEmpaquetados(centerId);
                conf.cerrar();
                orders.clear();
                pendingOrders.clear();
                preparedOrders.clear();
                verificatedOrders.clear();
                closedOrders.clear();
                sentOrders.clear();
                break;

        }
    }

    public void handleTabChange(TabChangeEvent event) {

        Configuracion conf = new Configuracion();
        es.inerttia.ittws.controllers.CuadroMandosController ctl = new es.inerttia.ittws.controllers.CuadroMandosController(conf);
        switch (event.getTab().getId()) {
            case "itemsPending":
                indexTab = 0;
                if ((pendingItems == null || pendingItems.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    pendingItems = ctl.getArticulosPendientesCuadroMandos(orderType, centerId, warehouseId);
                    if (!pendingItems.isEmpty()) {
                        pendingItemSelected = pendingItems.get(0);
                        //searchPendingItemsDetail(pendingItems.get(0).getArticulo().getIdArticulo());
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        pendingItemsDetail.clear();
//                        pendingItemsDetailFiltered.clear();
                    }
                    totalPendingItems = pendingItems.size();
                    PrimeFaces.current().executeScript("PF('dtArtPend').clearFilters();");
                }
                break;
            case "orders":
                indexTab = 1;
                if ((orders == null || orders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    orders = ctl.getPedidos(orderType, centerId, warehouseId, false);
                    if (!orders.isEmpty()) {
                        orderSelected = orders.get(0);
                        //searchOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        itemOrdersDetail.clear();
//                        itemOrdersDetailFiltered.clear();
                    }
//                    idStatus = 0;
//                    searchOrdersSummary();
                    totalOrders = orders.size();
//                    PrimeFaces.current().executeScript("PF('dtOrders').clearFilters();");
                }
                break;
            case "pendingOrders":
                indexTab = 2;
                if ((pendingOrders == null || pendingOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    pendingOrders = ctl.getPedidos(orderType, centerId, warehouseId, true);
                    if (!pendingOrders.isEmpty()) {
                        pendingOrderSelected = pendingOrders.get(0);
                        //searchOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        pendingOrdersDetail.clear();
//                        pendingOrdersDetailFiltered.clear();
                    }
//                    PrimeFaces.current().executeScript("PF('dtPendingOrders').clearFilters();");
//                    pendingOrdersChecked.clear();
//                    idStatus = -1;
//                    searchOrdersSummary();
                    totalPendingOrders = pendingOrders.size();
//                    PrimeFaces.current().executeScript("PF('dtPendingOrders').clearFilters();");
                }
                break;
            case "preparedOrders":
                indexTab = 3;
                if ((preparedOrders == null || preparedOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    preparedOrders = ctl.getPedidosPreparacion(orderType, centerId, warehouseId);
                    if (!preparedOrders.isEmpty()) {
                        preparedOrderSelected = preparedOrders.get(0);
                        //searchOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        preparedOrdersDetail.clear();
//                        preparedOrdersDetailFiltered.clear();
                    }
//                    preparedOrdersChecked.clear();
//                    idStatus = 5;
//                    searchOrdersSummary();
                    totalPreparedOrders = preparedOrders.size();
//                    PrimeFaces.current().executeScript("PF('dtPreparedOrders').clearFilters();");
                }
                break;
            case "verificationOrders":
                indexTab = 4;
                if ((verificatedOrders == null || verificatedOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    verificatedOrders = ctl.getPedidosVerificacion(orderType, centerId, warehouseId);
                    if (!verificatedOrders.isEmpty()) {
                        verificatedOrderSelected = verificatedOrders.get(0);
                        //searchOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        verificatedOrdersDetail.clear();
//                        verificatedOrdersDetailFiltered.clear();
                    }
//                    idStatus = 4;
                    //searchOrdersSummary();
                    totalVerificatedOrders = verificatedOrders.size();
//                    PrimeFaces.current().executeScript("PF('dtVerificatedOrders').clearFilters();");
                }
                break;
            case "closedOrders":
                indexTab = 5;
                if ((closedOrders == null || closedOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    closedOrders = ctl.getPedidosCerrados(orderType, centerId, warehouseId);
                    if (!closedOrders.isEmpty()) {
                        closedOrderSelected = closedOrders.get(0);
                        //searchClosedOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        itemClosedOrdersDetail.clear();
//                        itemClosedOrdersDetailFiltered.clear();
                    }
                    totalClosedOrders = closedOrders.size();
//                    PrimeFaces.current().executeScript("PF('dtClosedOrders').clearFilters();");
                }
                break;
            case "sentOrders":
                indexTab = 6;
                if ((sentOrders == null || sentOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
                    sentOrders = ctl.getPedidosEnviados(orderType, centerId, warehouseId);
                    if (!sentOrders.isEmpty()) {
                        sentOrderSelected = sentOrders.get(0);
                        //searchClosedOrdersDetail();
                    } else {
//                        Comun.infoMessage(msg.getString("no_results"));
//                        itemSentOrdersDetail.clear();
//                        itemSentOrdersDetailFiltered.clear();
                    }
//                    PrimeFaces.current().executeScript("PF('dtSentOrders').clearFilters();");
                    totalSendOrders = sentOrders.size();
                }
                break;
            case "packed":
                indexTab = 7;
                if ((packedOrders == null || packedOrders.isEmpty()) && (idCenterSearch == centerId && idWarehouseSearch == warehouseId)) {
//                    if (showPackedOrders) {
                    packedOrders = ctl.getPedidosEmpaquetados(centerId);
//                        if (packedOrders.isEmpty()) {
//                            Comun.infoMessage(msg.getString("no_results"));
//                        }
//
//                        PrimeFaces.current().executeScript("PF('dtPacked').clearFilters();");
//                        totalPackedOrders = packedOrders.size();
//                    }

                    break;
                }
                conf.cerrar();
        }

    }

    public void actualizarAlmacenCombo() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        CentroAlmacenController ctl = new CentroAlmacenController(conf);
        listaAlmacenes = ctl.getCentrosAlmacenByCentroBaja0(centerId, 1);
        conf.cerrar();
    }

    public void selectionDialog(String clave) {
        tipoSelection = "" + clave;
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(true)
                .draggable(true)
                .modal(true)
                .width("95%")
                .height("95%")
                .contentWidth("100%")
                .contentHeight("100%")
                .build();

        switch (clave) {
            case "calles": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));
                params.put("idCenter", Arrays.asList(String.valueOf(centerId)));
                params.put("idWarehouse", Arrays.asList(String.valueOf(warehouseId)));
                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "centroalmacen": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));
                params.put("idCenter", Arrays.asList(String.valueOf(centerId)));
                params.put("idWarehouse", Arrays.asList(String.valueOf(warehouseId)));
                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "centroTercero": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));
                params.put("idCentroTercero", Arrays.asList(String.valueOf(idCentroTercero)));
                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            default: {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));
                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
        }

    }

    public void dialogReturn(SelectEvent event) {
        if (tipoSelection != null) {
            switch (tipoSelection) {
                case "calles": {
                    List<Zona> selectedItems = (List<Zona>) event.getObject();
                    this.listaZonaSelected = selectedItems;
                    if (listaZonaSelected.isEmpty()) {
                        zonaString = "Sin calle";
                    } else if (listaZonaSelected.size() == 1) {
                        zonaString = listaZonaSelected.get(0).getNombre();
                        idZona = "('" + listaZonaSelected.get(0).getIdentidadZona() + "')";
                    } else {
                        zonaString = "VARIOS";
                        idZona = "('" + listaZonaSelected.get(0).getIdentidadZona() + "'";
                        for (int i = 1; i < listaZonaSelected.size(); i++) {
                            idZona += ",'" + listaZonaSelected.get(i).getIdentidadZona() + "'";
                        }
                        idZona += ")";
                    }
                    break;
                }
                case "clasif": {
                    List<PedidoSalidaClasificacion> selectedItems = (List<PedidoSalidaClasificacion>) event.getObject();
                    this.listaClasifSelected = selectedItems;
                    if (listaClasifSelected.isEmpty()) {
                        clasifString = "Sin nivel de clasificacion";
                    } else if (listaClasifSelected.size() == 1) {
                        clasifString = listaClasifSelected.get(0).getDescripcion();
                        idClasif = "('" + listaClasifSelected.get(0).getIdPedidoSalidaClasificacion() + "')";
                    } else {
                        clasifString = "VARIOS";
                        idClasif = "('" + listaClasifSelected.get(0).getIdPedidoSalidaClasificacion() + "'";
                        for (int i = 1; i < listaClasifSelected.size(); i++) {
                            idClasif += ",'" + listaClasifSelected.get(i).getIdPedidoSalidaClasificacion() + "'";
                        }
                        idClasif += ")";
                    }
                    break;
                }
                case "centroalmacen": {
                    CentroAlmacen selectedItems = (CentroAlmacen) event.getObject();
                    this.centroAlmacenSelected = selectedItems;
                    mostrarTablaPedidos();
                    break;
                }
                case "terceroSingle": {
                    Tercero selectedItems = (Tercero) event.getObject();
                    this.terceroSingle = selectedItems;
                    idCentroTercero = terceroSingle.getIdTercero() + "";
                    terceroString = terceroSingle.getRazonSocial();
                    break;
                }
                case "centroTercero": {
                    TerceroCentro selectedItems = (TerceroCentro) event.getObject();
                    this.centroTercero = selectedItems;
                    centroTerceroString = centroTercero.getNombre();
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void limpiarCalle() {
        zonaString = "";
    }

    public void limpiarClasif() {
        clasifString = "";
    }

    public void limpiarTercero() {
        terceroString = "";
        centroTerceroString = "";
    }

    public void limpiarCentroTercero() {
        centroTerceroString = "";
    }

    public void ubicacionesDisponibles() {
        Configuracion conf = new Configuracion();

        HuecoController hc = new HuecoController(conf);
        totalMaxOrders = hc.getTotalHuecosLibres(centerId, warehouseId);
        conf.cerrar();
    }

    public void motrarPedidos() {
        Configuracion conf = new Configuracion();
        PedidoSalidaController ctl = new PedidoSalidaController(conf);
        getSelectedOrder();
        listPedidos = ctl.getAsignarPedidos(centroAlmacenSelected.getCentroAlmacenPK().getIdCentro(), centroAlmacenSelected.getCentroAlmacenPK().getIdAlmacen(), fechInicPed, fechFinPed, fechInicSer, fechFinSer, (terceroSingle == null ? 0 : (int) terceroSingle.getIdTercero()), (centroTercero == null ? 0 : centroTercero.getIdTerceroCentro()), selectedOrderTypologies, lineasPendientes, lineasPendientesHasta);
        conf.cerrar();
        PrimeFaces.current().ajax().update("dlgAsigPed:dialogSelect");
        PrimeFaces.current().executeScript("PF('dialogSelect').show()");
    }

    public void mostrarTablaPedidos() {
//        Configuracion conf = new Configuracion();
//        PedidoSalidaController ctl = new PedidoSalidaController(conf);
        listPedidos = new ArrayList();
//        listPedidos = ctl.getAsignarPedidos(centroAlmacenSelected.getCentroAlmacenPK().getIdCentro(), centroAlmacenSelected.getCentroAlmacenPK().getIdAlmacen(), null, null, null, null, 0, 0, null, 0, 0);
//        conf.cerrar();
        PrimeFaces.current().ajax().update("dlgAsigPed:dialogSelect");
        PrimeFaces.current().executeScript("PF('dialogSelect').show()");
    }

    public void getSelectedOrder() {
        selectedOrderTypologies = "";
        if (listaTipologiaPedidosSelected != null && !listaTipologiaPedidosSelected.isEmpty()) {
            selectedOrderTypologies = "('" + listaTipologiaPedidosSelected.get(0).getIdTipoPedido() + "'";
            for (int i = 1; i < listaTipologiaPedidosSelected.size(); i++) {
                selectedOrderTypologies += ",'" + listaTipologiaPedidosSelected.get(i).getIdTipoPedido() + "'";
            }
            selectedOrderTypologies += ")";
        }

    }

    public boolean isPedidoSelected() {
        return listPedidosSelected != null && !listPedidosSelected.isEmpty();
    }

    public void exportToExcel() throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Articulos Pendientes");
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 5000);
            sheet.setColumnWidth(2, 5000);
            sheet.setColumnWidth(3, 5000);
            sheet.setColumnWidth(4, 5000);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 6000);
            sheet.setColumnWidth(7, 6000);
            sheet.setColumnWidth(8, 5000);
            sheet.setColumnWidth(9, 5000);
            sheet.setColumnWidth(10, 5000);
            sheet.setColumnWidth(11, 5000);
            sheet.setColumnWidth(12, 5000);
            sheet.setColumnWidth(13, 3000);
            sheet.setColumnWidth(14, 5000);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            String[] columnHeaders = {
                "Artículo", "Referencia", "Descripción", "Código de barras",
                "Min.Fec.Pedido", "Servido", "Pedido", "Se necesita",
                "Stock playa", "Stock suelo", "Stock muelle", "F.Últ.Coloc.Playa",
                "Stock Almacén", "F.Últ.Coloc.Alamacén", "Clasificación"
            };
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }
            int rowNum;
            if (!artPendTodosCentros) {
                rowNum = 1;
                for (ArticuloPendiente artPen : pendingItems) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(artPen.getArticulo().getIdArticulo());
                    row.createCell(1).setCellValue(artPen.getArticulo().getReferencia());
                    row.createCell(2).setCellValue(artPen.getArticulo().getDescripcion());
                    row.createCell(3).setCellValue(artPen.getArticulo().getCodigoBarras());
                    row.createCell(4).setCellValue(sdf.format(artPen.getFechaPedido()));
                    row.createCell(5).setCellValue(artPen.getServido());
                    row.createCell(6).setCellValue(artPen.getPedido());
                    row.createCell(7).setCellValue(artPen.getNecesario());
                    row.createCell(8).setCellValue(artPen.getStockPlaya());
                    row.createCell(9).setCellValue(artPen.getStockSuelo());
                    row.createCell(10).setCellValue(artPen.getStockMuelle());
                    row.createCell(11).setCellValue(artPen.getFechaUltimaColocacionPlaya() != null ? sdf.format(artPen.getFechaUltimaColocacionPlaya()) : "");
                    row.createCell(12).setCellValue(artPen.getStockAlmacen());
                    row.createCell(13).setCellValue(artPen.getFechaUltimaColocacionAlmacen() != null ? sdf.format(artPen.getFechaUltimaColocacionAlmacen()) : "");
                    row.createCell(14).setCellValue(artPen.getClasificacion());
                }
            } else {
                llamadaATodosCentros();
                rowNum = 1;
                for (ArticuloPendiente artPenAll : pendingItemsAllCenters) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(artPenAll.getArticulo().getIdArticulo());
                    row.createCell(1).setCellValue(artPenAll.getArticulo().getReferencia());
                    row.createCell(2).setCellValue(artPenAll.getArticulo().getDescripcion());
                    row.createCell(3).setCellValue(artPenAll.getArticulo().getCodigoBarras());
                    row.createCell(4).setCellValue(sdf.format(artPenAll.getFechaPedido()));
                    row.createCell(5).setCellValue(artPenAll.getServido());
                    row.createCell(6).setCellValue(artPenAll.getPedido());
                    row.createCell(7).setCellValue(artPenAll.getNecesario());
                    row.createCell(8).setCellValue(artPenAll.getStockPlaya());
                    row.createCell(9).setCellValue(artPenAll.getStockSuelo());
                    row.createCell(10).setCellValue(artPenAll.getStockMuelle());
                    row.createCell(11).setCellValue(artPenAll.getFechaUltimaColocacionPlaya() != null ? sdf.format(artPenAll.getFechaUltimaColocacionPlaya()) : "");
                    row.createCell(12).setCellValue(artPenAll.getStockAlmacen());
                    row.createCell(13).setCellValue(artPenAll.getFechaUltimaColocacionAlmacen() != null ? sdf.format(artPenAll.getFechaUltimaColocacionAlmacen()) : "");
                    row.createCell(14).setCellValue(artPenAll.getClasificacion());
                }

            }

            CellRangeAddress range = new CellRangeAddress(0, rowNum - 1, 0, 15);
            sheet.setAutoFilter(range);

            workbook.write(out);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"pedidosPendientesCentro.xls\"");

            workbook.write(externalContext.getResponseOutputStream());
            facesContext.responseComplete();

            File excelFile = File.createTempFile("pedidosPendientesCentro", ".xls");
            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                out.writeTo(fileOut);
            }
        }

    }

    public void showExcel() {
        PrimeFaces.current().executeScript("PF('dialogExport').show()");
    }

    public void llamadaATodosCentros() {
        Configuracion conf = new Configuracion();
        es.inerttia.ittws.controllers.CuadroMandosController ctl = new es.inerttia.ittws.controllers.CuadroMandosController(conf);
        pendingItemsAllCenters = ctl.getArticulosPendientesCuadroMandos(0, -1, -1);
        conf.cerrar();
    }

    public void asignarPedido() {
        boolean hayCambio = false;
        Configuracion conf = new Configuracion();
        es.inerttia.ittws.controllers.ConfiguracionController ctl2 = new es.inerttia.ittws.controllers.ConfiguracionController(conf);

        for (PedidoSalida p : listPedidosSelected) {
            List<String> l = new ArrayList<>();
            l.add(p.getIdPedidoSalida() + "");
            l.add("0");
            l.add(String.valueOf(centerId));
            l.add(String.valueOf(warehouseId));
            l.add(String.valueOf(p.getIdCentro()));
            l.add(String.valueOf(p.getIdAlmacen()));
            l.add("0");
            l.add(String.valueOf(radioSelected));
            l.add(String.valueOf(idCola));
            l.add(locateOrder ? "1" : "0");

            PedidoSalidaController ctl = new PedidoSalidaController(conf);

            es.inerttia.ittws.controllers.entities.Configuracion c = ctl2.getConfiguracion();
            Respuesta r = ctl.cambiarPedidoCentroAlmacen(l, "admin", "admin", c);
            if (r != null) {
                if (r.correcto()) {
                    hayCambio = true;

                } else {
                    showError("No se ha podido cambiar el " + p.getIdPedidoSalida() + " " + r.getMensaje());
                }
            } else {
                showError("No se ha podido cambiar");
            }

        }
        if (hayCambio) {
            showMessaggeGood("Se ha cambiado correctamente");
            System.out.println("Se ha cambiado ");
        }
        conf.cerrar();
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
        PrimeFaces.current().ajax().update("controlPanel");

    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
        PrimeFaces.current().ajax().update("controlPanel");
    }

    public void dialogContenedor(int idTercero, int idTerceroCentro) {
        DialogFrameworkOptions options = DialogFrameworkOptions.builder()
                .resizable(true)
                .draggable(true)
                .modal(true)
                .width("95%")
                .height("95%")
                .contentWidth("100%")
                .contentHeight("100%")
                .build();

        Map<String, List<String>> params = new HashMap<>();
        params.put("thirdPartyId", Arrays.asList(idTercero + ""));
        params.put("thirdPartyCenterId", Arrays.asList(idTerceroCentro + ""));
        params.put("centerId", Arrays.asList(centerId + ""));

        PrimeFaces.current().dialog().openDynamic("components/empaquetado", options, params);
    }
}
