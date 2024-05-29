/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Items;

import es.inerttia.ittws.controllers.ArticuloClasificacionController;
import es.inerttia.ittws.controllers.ArticuloController;
import es.inerttia.ittws.controllers.ClasificacionController;
import es.inerttia.ittws.controllers.PaisController;
import es.inerttia.ittws.controllers.TipoArticuloController;
import es.inerttia.ittws.controllers.TipoPickingController;
import es.inerttia.ittws.controllers.entities.ArticuloClasificacion;
import es.inerttia.ittws.controllers.entities.Clasificacion;
import es.inerttia.ittws.controllers.entities.Familia;
import es.inerttia.ittws.controllers.entities.Marca;
import es.inerttia.ittws.controllers.entities.NivelClasificacion;
import es.inerttia.ittws.controllers.entities.Pais;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittws.controllers.entities.TipoArticulo;
import es.inerttia.ittws.controllers.entities.TipoPicking;
import es.inerttia.ittws.controllers.entities.custom.Articulo;
import es.inerttia.ittws.controllers.entities.custom.Comun;
import es.inerttia.ittwscomun.Configuracion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.internal.util.logging.Log;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DialogFrameworkOptions;
import org.primefaces.model.LazyDataModel;

@ManagedBean(name = "MainItems")
@ViewScoped
public class MainItems {

    private LazyDataModel<es.inerttia.ittws.controllers.entities.custom.Articulo> lazyDataModel;

    private String idDeposito = "";
    private String idArticulo = "";
    private String idDescripcion = "";
    private String idReferencia = "";
    private String idCodBarras = "";
    private String idClasificacion = "-1";
    private int idEstado = -1;

    private String idTercero;
    private String idFamilia;
    private String idMarca;
    private String idNivelClasif;

    private int idBloqueo = -1;
    private int idGrupoArticulo = -1;
    private String idEtiquetaDe = "-1";
    private String idEtiquetado = "-1";
    private int idControlDeLote = -1;
    private int idControlDeCaducidad = -1;
    private int idPendienteVerificacion = -1;
    private int idFechUltModif = -1;
    private int idPesoBruto = -1;
    private int idVolumentBruto = -1;
    private int idControlSeries = -1;
    private int idTipoArticulo = -1;
    private int idStock = -1;
    private int idPreventa = -1;
    private int idTipoPicking = -1;
    private int idArtClasif = -1;
    private double minPesoBruto;
    private double maxPesoBruto;
    private List<Pais> listaPaises;
    private List<TipoArticulo> listaTiposArticulo;
    private List<TipoPicking> listaTiposPicking;
    private List<ArticuloClasificacion> listaArticuloClasificacions;
    private List<Clasificacion> listaClasificaciones;

    private List<es.inerttia.ittws.controllers.entities.custom.Articulo> listaArticulos;
    private List<es.inerttia.ittws.controllers.entities.custom.Articulo> listArticulosLazyFiltered;

    private List<Tercero> listaTerceroSelected;
    private List<Familia> listaFamiliaSelected;
    private List<Marca> listaMarcasSelected;
    private List<NivelClasificacion> listaNivelesSelected;
    private String terceroString;
    private String familiaString;
    private String marcaString;
    private String nivelString;
    private String tipoSelection;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    // <editor-fold defaultstate="collapsed" desc=" getters y setters "> 
    public String getIdClasificacion() {
        return idClasificacion;
    }

    public String getTerceroString() {
        return terceroString;
    }

    public String getIdTercerdo() {
        return idTercero;
    }

    public void setIdTercerdo(String idTercerdo) {
        this.idTercero = idTercerdo;
    }

    public void setTerceroString(String terceroString) {
        this.terceroString = terceroString;
    }

    public void setIdClasificacion(String idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getFamiliaString() {
        return familiaString;
    }

    public void setFamiliaString(String familiaString) {
        this.familiaString = familiaString;
    }

    public String getMarcaString() {
        return marcaString;
    }

    public void setMarcaString(String marcaString) {
        this.marcaString = marcaString;
    }

    public String getNivelString() {
        return nivelString;
    }

    public void setNivelString(String nivelString) {
        this.nivelString = nivelString;
    }

    public String getTipoSelection() {
        return tipoSelection;
    }

    public void setTipoSelection(String tipoSelection) {
        this.tipoSelection = tipoSelection;
    }

    public List<Familia> getListaFamiliaSelected() {
        return listaFamiliaSelected;
    }

    public void setListaFamiliaSelected(List<Familia> listaFamiliaSelected) {
        this.listaFamiliaSelected = listaFamiliaSelected;
    }

    public List<Marca> getListaMarcasSelected() {
        return listaMarcasSelected;
    }

    public void setListaMarcasSelected(List<Marca> listaMarcasSelected) {
        this.listaMarcasSelected = listaMarcasSelected;
    }

    public List<NivelClasificacion> getListaNivelesSelected() {
        return listaNivelesSelected;
    }

    public void setListaNivelesSelected(List<NivelClasificacion> listaNivelesSelected) {
        this.listaNivelesSelected = listaNivelesSelected;
    }

    public List<Tercero> getListaTerceroSelected() {
        return listaTerceroSelected;
    }

    public void setListaTerceroSelected(List<Tercero> listaTerceroSelected) {
        this.listaTerceroSelected = listaTerceroSelected;
    }

    public double getMinPesoBruto() {
        return minPesoBruto;
    }

    public void setMinPesoBruto(double minPesoBruto) {
        this.minPesoBruto = minPesoBruto;
    }

    public double getMaxPesoBruto() {
        return maxPesoBruto;
    }

    public void setMaxPesoBruto(double maxPesoBruto) {
        this.maxPesoBruto = maxPesoBruto;
    }

    public List<Articulo> getListArticulosLazyFiltered() {
        return listArticulosLazyFiltered;
    }

    public void setListArticulosLazyFiltered(List<Articulo> listArticulosLazyFiltered) {
        this.listArticulosLazyFiltered = listArticulosLazyFiltered;
    }

    public LazyDataModel<Articulo> getLazyDataModel() {
        return lazyDataModel;
    }

    public void setLazyDataModel(LazyDataModel<Articulo> lazyDataModel) {
        this.lazyDataModel = lazyDataModel;
    }

    public String getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(String idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getIdNivelClasif() {
        return idNivelClasif;
    }

    public void setIdNivelClasif(String idNivelClasif) {
        this.idNivelClasif = idNivelClasif;
    }

    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public String getIdDeposito() {
        return idDeposito;
    }

    public void setIdDeposito(String idDeposito) {
        this.idDeposito = idDeposito;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getIdDescripcion() {
        return idDescripcion;
    }

    public void setIdDescripcion(String idDescripcion) {
        this.idDescripcion = idDescripcion;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getIdCodBarras() {
        return idCodBarras;
    }

    public void setIdCodBarras(String idCodBarras) {
        this.idCodBarras = idCodBarras;
    }

    public String getIdEtiquetaDe() {
        return idEtiquetaDe;
    }

    public void setIdEtiquetaDe(String idEtiquetaDe) {
        this.idEtiquetaDe = idEtiquetaDe;
    }

    public List<Clasificacion> getListaClasificaciones() {
        if (listaClasificaciones == null) {
            Configuracion conf = new Configuracion();
            ClasificacionController ctl = new ClasificacionController(conf);
            listaClasificaciones = ctl.getClasificaciones();
            conf.cerrar();
        }

        return listaClasificaciones;
    }

    public void setListaClasificaciones(List<Clasificacion> listaClasificaciones) {
        this.listaClasificaciones = listaClasificaciones;
    }

    public List<TipoArticulo> getListaTiposArticulo() {
        if (listaTiposArticulo == null) {
            Configuracion conf = new Configuracion();
            TipoArticuloController ctl = new TipoArticuloController(conf);
            listaTiposArticulo = ctl.getTiposArticulo();
            conf.cerrar();
        }
        return listaTiposArticulo;
    }

    public void setListaTiposArticulo(List<TipoArticulo> listaTiposArticulo) {
        this.listaTiposArticulo = listaTiposArticulo;

    }

    public List<ArticuloClasificacion> getListaArticuloClasificacions() {
        if (listaArticuloClasificacions == null) {
            Configuracion conf = new Configuracion();
            ArticuloClasificacionController ctl = new ArticuloClasificacionController(conf);
            listaArticuloClasificacions = ctl.getArticuloClasificacions();
            conf.cerrar();
        }
        return listaArticuloClasificacions;
    }

    public void setListaArticuloClasificacions(List<ArticuloClasificacion> listaArticuloClasificacions) {
        this.listaArticuloClasificacions = listaArticuloClasificacions;
    }

    public List<TipoPicking> getListaTiposPicking() {
        if (listaTiposPicking == null) {
            Configuracion conf = new Configuracion();
            TipoPickingController ctl = new TipoPickingController(conf);
            listaTiposPicking = ctl.getTiposPicking();
            conf.cerrar();
        }
        return listaTiposPicking;
    }

    public void setListaTiposPicking(List<TipoPicking> listaTiposPicking) {
        this.listaTiposPicking = listaTiposPicking;
    }

    public List<Pais> getListaPaises() {
        if (listaPaises == null) {
            Configuracion conf = new Configuracion();
            es.inerttia.ittws.controllers.PaisController ctl = new es.inerttia.ittws.controllers.PaisController(conf);
            listaPaises = ctl.getPaises();
            conf.cerrar();
        }
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdBloqueo() {
        return idBloqueo;
    }

    public void setIdBloqueo(int idBloqueo) {
        this.idBloqueo = idBloqueo;
    }

    public int getIdGrupoArticulo() {
        return idGrupoArticulo;
    }

    public void setIdGrupoArticulo(int idGrupoArticulo) {
        this.idGrupoArticulo = idGrupoArticulo;
    }

    public String getIdEtiquetado() {
        return idEtiquetado;
    }

    public void setIdEtiquetado(String idEtiquetado) {
        this.idEtiquetado = idEtiquetado;
    }

    public int getIdControlDeLote() {
        return idControlDeLote;
    }

    public void setIdControlDeLote(int idControlDeLote) {
        this.idControlDeLote = idControlDeLote;
    }

    public int getIdControlDeCaducidad() {
        return idControlDeCaducidad;
    }

    public void setIdControlDeCaducidad(int idControlDeCaducidad) {
        this.idControlDeCaducidad = idControlDeCaducidad;
    }

    public int getIdPendienteVerificacion() {
        return idPendienteVerificacion;
    }

    public void setIdPendienteVerificacion(int idPendienteVerificacion) {
        this.idPendienteVerificacion = idPendienteVerificacion;
    }

    public int getIdFechUltModif() {
        return idFechUltModif;
    }

    public void setIdFechUltModif(int idFechUltModif) {
        this.idFechUltModif = idFechUltModif;
    }

    public int getIdPesoBruto() {
        return idPesoBruto;
    }

    public void setIdPesoBruto(int idPesoBruto) {
        this.idPesoBruto = idPesoBruto;
    }

    public int getIdVolumentBruto() {
        return idVolumentBruto;
    }

    public void setIdVolumentBruto(int idVolumentBruto) {
        this.idVolumentBruto = idVolumentBruto;
    }

    public int getIdControlSeries() {
        return idControlSeries;
    }

    public void setIdControlSeries(int idControlSeries) {
        this.idControlSeries = idControlSeries;
    }

    public int getIdTipoArticulo() {
        return idTipoArticulo;
    }

    public void setIdTipoArticulo(int idTipoArticulo) {
        this.idTipoArticulo = idTipoArticulo;
    }

    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdPreventa() {
        return idPreventa;
    }

    public void setIdPreventa(int idPreventa) {
        this.idPreventa = idPreventa;
    }

    public int getIdTipoPicking() {
        return idTipoPicking;
    }

    public void setIdTipoPicking(int idTipoPicking) {
        this.idTipoPicking = idTipoPicking;
    }

    public int getIdArtClasif() {
        return idArtClasif;
    }

    public void setIdArtClasif(int idArtClasif) {
        this.idArtClasif = idArtClasif;
    }

    // </editor-fold>
    @PostConstruct
    public void init() {
        terceroString = "";
        familiaString = "";
        marcaString = "";
        nivelString = "";
        idTercero = "";
        
        idFamilia = "";
        idMarca = "";
        idNivelClasif = "";
    }

    public void search() {
        Configuracion conf = new Configuracion();
        ArticuloController ctl = new ArticuloController(conf);
        listaArticulos = ctl.getArticulosConsultaGeneral(1, 1, idEtiquetado, idArticulo, idDescripcion, idReferencia, idCodBarras, idClasificacion, idEstado, idBloqueo, idTipoArticulo, idTercero, idFamilia, idMarca, idNivelClasif, idControlDeLote, idControlSeries, idTipoArticulo, idControlDeCaducidad, idPendienteVerificacion, idFechUltModif, idPesoBruto, idVolumentBruto, -1, idStock, idPreventa, 0, idEtiquetaDe, idTipoPicking, idArtClasif, minPesoBruto, maxPesoBruto);

        lazyDataModel = new LazyCustomerDataModel(listaArticulos);
        conf.cerrar();
        PrimeFaces.current().ajax().update("formArticulo:datatableArticulos");
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

        String[] paletDetailList = {String.valueOf(clave)};
        String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
        String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
        Map<String, List<String>> params = new HashMap<>();
        params.put("selection", Arrays.asList(paletDetail));

        PrimeFaces.current().dialog().openDynamic("selection", options, params);
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
        PrimeFaces.current().ajax().update("formArticulo");

    }

    public void dialogReturn(SelectEvent event) {

        if (null != tipoSelection) {
            switch (tipoSelection) {
                case "tercero": {
                    List<Tercero> selectedItems = (List<Tercero>) event.getObject();
                    this.listaTerceroSelected = selectedItems;
                    if (listaTerceroSelected.isEmpty()) {
                        terceroString = "Sin tercero";
                    } else if (listaTerceroSelected.size() == 1) {
                        terceroString = "" + listaTerceroSelected.get(0).getNombreComercial();
                        idTercero = "('" + listaTerceroSelected.get(0).getIdTercero() + "')";
                    } else {
                        terceroString = "VARIOS";
                        idTercero = "('" + listaTerceroSelected.get(0).getIdTercero() + "'";
                        for (int i = 1; i < listaTerceroSelected.size(); i++) {
                            idTercero += ",'" + listaTerceroSelected.get(i).getIdTercero() + "'";
                        }
                        idTercero += ")";
                    }
                    break;
                }
                case "familia": {
                    List<Familia> selectedItems = (List<Familia>) event.getObject();
                    this.listaFamiliaSelected = selectedItems;
                    if (listaFamiliaSelected.isEmpty()) {
                        familiaString = "Sin familia";
                    } else if (listaFamiliaSelected.size() == 1) {
                        familiaString = "" + listaFamiliaSelected.get(0).getIdFamilia();
                        idFamilia = "('" + listaFamiliaSelected.get(0).getIdFamilia() + "')";
                    } else {
                        familiaString = "VARIOS";
                        idFamilia = "('" + listaFamiliaSelected.get(0).getIdFamilia() + "'";
                        for (int i = 1; i < listaFamiliaSelected.size(); i++) {
                            idFamilia += ",'" + listaFamiliaSelected.get(i).getIdFamilia() + "'";
                        }
                        idFamilia += ")";
                    }
                    break;
                }
                case "marca": {
                    List<Marca> selectedItems = (List<Marca>) event.getObject();
                    this.listaMarcasSelected = selectedItems;
                    if (listaMarcasSelected.isEmpty()) {
                        marcaString = "Sin marca";
                    } else if (listaMarcasSelected.size() == 1) {
                        marcaString = "" + listaMarcasSelected.get(0).getNombre();
                        idMarca = "('" + listaMarcasSelected.get(0).getIdMarca() + "')";
                    } else {
                        marcaString = "VARIOS";
                        idMarca = "('" + listaMarcasSelected.get(0).getIdMarca() + "'";
                        for (int i = 1; i < listaMarcasSelected.size(); i++) {
                            idMarca += ",'" + listaMarcasSelected.get(i).getIdMarca() + "'";
                        }
                        idMarca += ")";
                    }
                    break;
                }
                case "nivel": {
                    List<NivelClasificacion> selectedItems = (List<NivelClasificacion>) event.getObject();
                    this.listaNivelesSelected = selectedItems;
                    if (listaNivelesSelected.isEmpty()) {
                        nivelString = "Sin nivel";
                    } else if (listaNivelesSelected.size() == 1) {
                        nivelString = "" + listaNivelesSelected.get(0).getDescripcion();
                        idNivelClasif = "('" + listaNivelesSelected.get(0).getIdNivelClasificacion() + "')";
                    } else {
                        nivelString = "VARIOS";
                        idNivelClasif = "('" + listaNivelesSelected.get(0).getIdNivelClasificacion() + "'";
                        for (int i = 1; i < listaNivelesSelected.size(); i++) {
                            idNivelClasif += ",'" + listaNivelesSelected.get(i).getIdNivelClasificacion() + "'";
                        }
                        idNivelClasif += ")";
                    }
                    break;
                }
                default:
                    break;
            }
        }

    }

    public void limpiarTercero() {
        if (!terceroString.isEmpty()) {
            terceroString = "";
            idTercero = "";
        }
    }

    public void limpiarFamilia() {
        if (!familiaString.isEmpty()) {
            familiaString = "";
            idFamilia = "";
        }
    }

    public void limpiarMarca() {
        if (!marcaString.isEmpty()) {
            marcaString = "";
            idMarca = "";
        }
    }

    public void limpiarNivel() {
        if (!nivelString.isEmpty()) {
            nivelString = "";
            idNivelClasif = "";
        }
    }

    public void onRowExportXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = Math.min(sheet.getPhysicalNumberOfRows(), 2);
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            HSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                    HSSFCell cell = row.getCell(i);
                    if (cell != null) {
                        CellStyle cellStyle = wb.createCellStyle();
                        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
        }
    }

    public void exportToExcel(String nombreArchivo) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Articulos");
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
            sheet.setColumnWidth(15, 5000);
            sheet.setColumnWidth(16, 5000);
            sheet.setColumnWidth(17, 5000);
            sheet.setColumnWidth(18, 5000);
            sheet.setColumnWidth(19, 5000);
            sheet.setColumnWidth(20, 3000);
            sheet.setColumnWidth(21, 5000);
            sheet.setColumnWidth(22, 5000);
            sheet.setColumnWidth(23, 5000);
            sheet.setColumnWidth(24, 5000);
            sheet.setColumnWidth(25, 5000);
            sheet.setColumnWidth(26, 5000);
            sheet.setColumnWidth(27, 5000);
            sheet.setColumnWidth(28, 5000);
            sheet.setColumnWidth(29, 5000);
            sheet.setColumnWidth(30, 5000);
            sheet.setColumnWidth(31, 5000);
            sheet.setColumnWidth(32, 5000);
            sheet.setColumnWidth(33, 5000);
            sheet.setColumnWidth(34, 5000);

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Articulo");
            header.createCell(1).setCellValue("Referencia");
            header.createCell(2).setCellValue("Descripcion");
            header.createCell(3).setCellValue("Cdigo de barras");
            header.createCell(4).setCellValue("Id Artículo Depósito");
            header.createCell(5).setCellValue("Fecha de Alta");
            header.createCell(6).setCellValue("Marca");
            header.createCell(7).setCellValue("Familia");
            header.createCell(8).setCellValue("Fecha Ult. Modif.");
            header.createCell(9).setCellValue("Estado");
            header.createCell(10).setCellValue("B.V.");
            header.createCell(11).setCellValue("Usuario Último Bloqueo Ventas");
            header.createCell(12).setCellValue("Fecha Último Bloqueo Ventas");
            header.createCell(13).setCellValue("B.C.");
            header.createCell(14).setCellValue("Grupo Art.");
            header.createCell(15).setCellValue("Tipo Picking");
            header.createCell(16).setCellValue("Art. Clasificación");
            header.createCell(17).setCellValue("Clasificación");
            header.createCell(18).setCellValue("Con etiquetas");
            header.createCell(19).setCellValue("Etiq. Portuguesa");
            header.createCell(20).setCellValue("Etiquetado Port.");
            header.createCell(21).setCellValue("Etiq. Italiana");
            header.createCell(22).setCellValue("Etiquetado Ita.");
            header.createCell(23).setCellValue("Etiq. Europea");
            header.createCell(24).setCellValue("Propiedad de");
            header.createCell(25).setCellValue("Pndte. Verificar");
            header.createCell(26).setCellValue("Fecha Verificación");
            header.createCell(27).setCellValue("Altura(cm)");
            header.createCell(28).setCellValue("Ancho(cm)");
            header.createCell(29).setCellValue("Largo(cm)");
            header.createCell(30).setCellValue("Peso Neto(gr)");
            header.createCell(31).setCellValue("Volumen Neto(cm3)");
            header.createCell(32).setCellValue("Peso Bruto(gr)");
            header.createCell(33).setCellValue("Volumen Bruto(cm3)");
            header.createCell(34).setCellValue("Preventa");

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

            for (int i = 0; i < header.getLastCellNum(); i++) {
                header.getCell(i).setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (es.inerttia.ittws.controllers.entities.custom.Articulo articulo : listaArticulos ){
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(articulo.getIdArticulo());
                row.createCell(1).setCellValue(articulo.getReferencia());
                row.createCell(2).setCellValue(articulo.getDescripcion());
                row.createCell(3).setCellValue(articulo.getCodigoBarras());
                row.createCell(4).setCellValue(articulo.getIdArticuloDeposito());
                row.createCell(5).setCellValue(sdf.format(articulo.getFechaAlta()));
                row.createCell(6).setCellValue(articulo.getIdMarca().getNombre());
                row.createCell(7).setCellValue(articulo.getIdFamilia().getNombre());
                row.createCell(8).setCellValue(sdf.format(articulo.getFechaUltModif()));
                row.createCell(9).setCellValue(articulo.getEstado() == 0 ? "ACTIVO" : "INACTIVO");
                row.createCell(10).setCellValue(articulo.isBloqueoVentas() ? "Sí" : "No");
                row.createCell(11).setCellValue(articulo.getUsuarioUltBloqueoVentas().getUsername());
                row.createCell(12).setCellValue(articulo.getFechaUltBloqueoVentas()!=null ? sdf.format(articulo.getFechaUltBloqueoVentas()) : "" );
                row.createCell(13).setCellValue(articulo.isBloqueoCompras() ? "Sí" : "No"); 
                row.createCell(14).setCellValue(articulo.getArticuloGrupo().getEtiquetaGrupo().getDescripcion());
                row.createCell(15).setCellValue(articulo.getTxtTipoPicking());
                row.createCell(16).setCellValue(articulo.getIdArticuloClasificacion().getDescripcion());
                row.createCell(17).setCellValue(articulo.getTxtClasificacion());
                row.createCell(18).setCellValue(articulo.isEtiqueta() ? "Sí" : "No");
                row.createCell(19).setCellValue(articulo.isEtiquetaProtuguesa() ? "Sí" : "No");
                row.createCell(20).setCellValue(articulo.isEtiquetadoPortugues() ? "Sí" : "No");
                row.createCell(21).setCellValue(articulo.isEtiquetaItaliana() ? "Sí" : "No");
                row.createCell(22).setCellValue(articulo.isEtiquetadoItaliano() ? "Sí" : "No");
                row.createCell(23).setCellValue(articulo.isEtiquetaEuropea() ? "Sí" : "No");
                row.createCell(24).setCellValue(articulo.getIdUsuarioCreacion());
                row.createCell(25).setCellValue(articulo.isPendienteVerificar() ? "Sí" : "No");
                row.createCell(26).setCellValue(articulo.getFechaUltimaVerificacion() != null ? "Sí" : "No");
                row.createCell(27).setCellValue(articulo.getAlto().toString());
                row.createCell(28).setCellValue(articulo.getAncho().toString());
                row.createCell(29).setCellValue(articulo.getLargo().toString());
                row.createCell(30).setCellValue(articulo.getPesoNeto().toString());
                row.createCell(31).setCellValue(articulo.getVolumenNeto().toString());
                row.createCell(32).setCellValue(articulo.getPesoBruto().toString());
                row.createCell(33).setCellValue(articulo.getVolumen().toString());
                row.createCell(34).setCellValue(articulo.isPreventa() ? "Sí" : "No");

            }
            CellRangeAddress range = new CellRangeAddress(0, rowNum - 1, 0, 35);
            sheet.setAutoFilter(range);

            workbook.write(out);
            byte[] content = out.toByteArray();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + ".xlsx");
            response.setContentLength(content.length);

            response.getOutputStream().write(content);

            FacesContext.getCurrentInstance().responseComplete();

        }

    }
}
