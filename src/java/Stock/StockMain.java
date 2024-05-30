/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stock;

import es.inerttia.ittws.controllers.CentroAlmacenController;
import es.inerttia.ittws.controllers.CentroController;
import es.inerttia.ittws.controllers.HuecoController;
import es.inerttia.ittws.controllers.StockController;
import es.inerttia.ittws.controllers.UnidadController;
import es.inerttia.ittws.controllers.entities.Articulo;
import es.inerttia.ittws.controllers.entities.ArticuloClasificacion;
import es.inerttia.ittws.controllers.entities.ArticuloHueco;
import es.inerttia.ittws.controllers.entities.ArticuloLote;
import es.inerttia.ittws.controllers.entities.Centro;
import es.inerttia.ittws.controllers.entities.CentroAlmacen;
import es.inerttia.ittws.controllers.entities.Familia;
import es.inerttia.ittws.controllers.entities.Marca;
import es.inerttia.ittws.controllers.entities.PedidoSalidaClasificacion;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittws.controllers.entities.TerceroCentro;
import es.inerttia.ittws.controllers.entities.TipoHueco;
import es.inerttia.ittws.controllers.entities.Unidad;
import es.inerttia.ittws.controllers.entities.custom.ArticuloStockCentroAlmacen;
import es.inerttia.ittws.controllers.entities.custom.Zona;
import es.inerttia.ittws.controllers.parameters.consultaStock.PConsultaStockArticulo;
import es.inerttia.ittws.controllers.parameters.consultaStock.PConsultaStockIN;
import es.inerttia.ittws.controllers.parameters.consultaStock.PConsultaStockLote;
import es.inerttia.ittwsEntidades.Respuesta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DialogFrameworkOptions;

@ManagedBean(name = "Stock")
@ViewScoped
public class StockMain {

    private int centerId = 1;
    private int warehouseId = 1;

    private List<Centro> listaCentros;
    private List<CentroAlmacen> listaAlmacenes;
    private List<CentroAlmacen> listaAlmacenesSelected;
    private String idAlmacenes;

    private List<Articulo> listaArticulos;
    private List<Articulo> listaArticulosSelected;
    private String idArticulo;
    private String articuloString;

    //------Filter-----//
    private List<TipoHueco> listaTipoHuecos;
    private String idTipoHueco;

    private List<Familia> listaFamilia;
    private List<Familia> listaFamiliaSelected;
    private List<Familia> listaFamiliaFiltered;
    private List<Marca> listaMarcas;
    private List<Marca> listaMarcasSelected;
    private List<Marca> listaMarcasFiltered;
    private List<TipoHueco> listaUbi;
    private List<TipoHueco> listaUbiSelected;
    private List<TipoHueco> listaUbiFiltered;
    private List<ArticuloClasificacion> listaArticClas;
    private List<ArticuloClasificacion> listaArticClasSelected;
    private List<ArticuloClasificacion> listaArticClasFiltered;
    private List<Tercero> listaTercero;
    private List<Tercero> listaTerceroSelected;
    private List<Tercero> listaTerceroFiltered;
    private List<Tercero> listaTercerDepo;
    private List<Tercero> listaTercerDepoSelected;
    private List<Tercero> listaTercerDepoFiltered;
    private List<Tercero> listaTercerSecun;
    private List<Tercero> listaTercerSecunSelected;
    private List<Tercero> listaTercerSecunFiltered;

    private List<Unidad> listaUnidad;

    private int controlSeries;

    private String familiasString;
    private String marcaString;
    private String ubiString;
    private String clasifString;
    private String terceroString;
    private String tercerDepoString;
    private String tercerSecunString;

    private String idFamilia;
    private String idMarca;
    private String idUbi;
    private String idClasif;
    private String idTercero;
    private String idTercerDepo;
    private String idTercerSecun;

    private String tipoSelection;
    private String referenciaString;
    private String descripciónString;
    private String loteSerString;
    private String codBarString;

    private int estado;

    private boolean agruparLote;
    private boolean quitarSinStock;

    private PConsultaStockIN stockFilter = new PConsultaStockIN();
    private List<ArticuloStockCentroAlmacen> items;
    private List<ArticuloStockCentroAlmacen> itemsFiltered;
    private ArticuloStockCentroAlmacen itemsSelected;

    private List<PConsultaStockLote> listaLotes;
    private ArticuloLote loteSelected;
    private List<ArticuloHueco> listaUbicaciones;
    private ArticuloHueco ubicacionSelected;

    // <editor-fold defaultstate="collapsed" desc=" getters y setters ">
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public List<CentroAlmacen> getListaAlmacenesSelected() {
        return listaAlmacenesSelected;
    }

    public void setListaAlmacenesSelected(List<CentroAlmacen> listaAlmacenesSelected) {
        this.listaAlmacenesSelected = listaAlmacenesSelected;
    }

    public String getIdAlmacenes() {
        return idAlmacenes;
    }

    public void setIdAlmacenes(String idAlmacenes) {
        this.idAlmacenes = idAlmacenes;
    }

    public List<ArticuloStockCentroAlmacen> getItemsFiltered() {
        return itemsFiltered;
    }

    public void setItemsFiltered(List<ArticuloStockCentroAlmacen> itemsFiltered) {
        this.itemsFiltered = itemsFiltered;
    }

    public List<Familia> getListaFamiliaFiltered() {
        return listaFamiliaFiltered;
    }

    public void setListaFamiliaFiltered(List<Familia> listaFamiliaFiltered) {
        this.listaFamiliaFiltered = listaFamiliaFiltered;
    }

    public List<Marca> getListaMarcasFiltered() {
        return listaMarcasFiltered;
    }

    public void setListaMarcasFiltered(List<Marca> listaMarcasFiltered) {
        this.listaMarcasFiltered = listaMarcasFiltered;
    }

    public List<TipoHueco> getListaUbiFiltered() {
        return listaUbiFiltered;
    }

    public void setListaUbiFiltered(List<TipoHueco> listaUbiFiltered) {
        this.listaUbiFiltered = listaUbiFiltered;
    }

    public List<ArticuloClasificacion> getListaArticClasFiltered() {
        return listaArticClasFiltered;
    }

    public void setListaArticClasFiltered(List<ArticuloClasificacion> listaArticClasFiltered) {
        this.listaArticClasFiltered = listaArticClasFiltered;
    }

    public List<Tercero> getListaTerceroFiltered() {
        return listaTerceroFiltered;
    }

    public void setListaTerceroFiltered(List<Tercero> listaTerceroFiltered) {
        this.listaTerceroFiltered = listaTerceroFiltered;
    }

    public List<Tercero> getListaTercerDepoFiltered() {
        return listaTercerDepoFiltered;
    }

    public void setListaTercerDepoFiltered(List<Tercero> listaTercerDepoFiltered) {
        this.listaTercerDepoFiltered = listaTercerDepoFiltered;
    }

    public List<Tercero> getListaTercerSecunFiltered() {
        return listaTercerSecunFiltered;
    }

    public void setListaTercerSecunFiltered(List<Tercero> listaTercerSecunFiltered) {
        this.listaTercerSecunFiltered = listaTercerSecunFiltered;
    }

    public List<ArticuloHueco> getListaUbicaciones() {
        return listaUbicaciones;
    }

    public void setListaUbicaciones(List<ArticuloHueco> listaUbicaciones) {
        this.listaUbicaciones = listaUbicaciones;
    }

    public ArticuloHueco getUbicacionSelected() {
        return ubicacionSelected;
    }

    public void setUbicacionSelected(ArticuloHueco ubicacionSelected) {
        this.ubicacionSelected = ubicacionSelected;
    }

    public ArticuloStockCentroAlmacen getItemsSelected() {
        return itemsSelected;
    }

    public void setItemsSelected(ArticuloStockCentroAlmacen itemsSelected) {
        this.itemsSelected = itemsSelected;
    }

    public List<PConsultaStockLote> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(List<PConsultaStockLote> listaLotes) {
        this.listaLotes = listaLotes;
    }

    public ArticuloLote getLoteSelected() {
        return loteSelected;
    }

    public void setLoteSelected(ArticuloLote loteSelected) {
        this.loteSelected = loteSelected;
    }

    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public List<Articulo> getListaArticulosSelected() {
        return listaArticulosSelected;
    }

    public void setListaArticulosSelected(List<Articulo> listaArticulosSelected) {
        this.listaArticulosSelected = listaArticulosSelected;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getIdTipoHueco() {
        return idTipoHueco;
    }

    public void setIdTipoHueco(String idTipoHueco) {
        this.idTipoHueco = idTipoHueco;
    }

    public PConsultaStockIN getStockFilter() {
        return stockFilter;
    }

    public List<ArticuloStockCentroAlmacen> getItems() {
        return items;
    }

    public void setStockFilter(PConsultaStockIN stockFilter) {
        this.stockFilter = stockFilter;
    }

    public void setItems(List<ArticuloStockCentroAlmacen> items) {
        this.items = items;
    }

    public List<Tercero> getListaTercero() {
        return listaTercero;
    }

    public void setListaTercero(List<Tercero> listaTercero) {
        this.listaTercero = listaTercero;
    }

    public List<Tercero> getListaTerceroSelected() {
        return listaTerceroSelected;
    }

    public void setListaTerceroSelected(List<Tercero> listaTerceroSelected) {
        this.listaTerceroSelected = listaTerceroSelected;
    }

    public List<Tercero> getListaTercerDepo() {
        return listaTercerDepo;
    }

    public void setListaTercerDepo(List<Tercero> listaTercerDepo) {
        this.listaTercerDepo = listaTercerDepo;
    }

    public List<Tercero> getListaTercerDepoSelected() {
        return listaTercerDepoSelected;
    }

    public void setListaTercerDepoSelected(List<Tercero> listaTercerDepoSelected) {
        this.listaTercerDepoSelected = listaTercerDepoSelected;
    }

    public List<Tercero> getListaTercerSecun() {
        return listaTercerSecun;
    }

    public void setListaTercerSecun(List<Tercero> listaTercerSecun) {
        this.listaTercerSecun = listaTercerSecun;
    }

    public List<Tercero> getListaTercerSecunSelected() {
        return listaTercerSecunSelected;
    }

    public void setListaTercerSecunSelected(List<Tercero> listaTercerSecunSelected) {
        this.listaTercerSecunSelected = listaTercerSecunSelected;
    }

    public List<Unidad> getListaUnidad() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        UnidadController ctl = new UnidadController(conf);
        listaUnidad = ctl.getUnidades();
        conf.cerrar();
        return listaUnidad;
    }

    public void setListaUnidad(List<Unidad> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    public List<TipoHueco> getListaUbi() {
        return listaUbi;
    }

    public void setListaUbi(List<TipoHueco> listaUbi) {
        this.listaUbi = listaUbi;
    }

    public List<TipoHueco> getListaUbiSelected() {
        return listaUbiSelected;
    }

    public void setListaUbiSelected(List<TipoHueco> listaUbiSelected) {
        this.listaUbiSelected = listaUbiSelected;
    }

    public List<ArticuloClasificacion> getListaArticClas() {
        return listaArticClas;
    }

    public void setListaArticClas(List<ArticuloClasificacion> listaArticClas) {
        this.listaArticClas = listaArticClas;
    }

    public List<ArticuloClasificacion> getListaArticClasSelected() {
        return listaArticClasSelected;
    }

    public void setListaArticClasSelected(List<ArticuloClasificacion> listaArticClasSelected) {
        this.listaArticClasSelected = listaArticClasSelected;
    }

    public String getTercerSecunString() {
        return tercerSecunString;
    }

    public void setTercerSecunString(String tercerSecunString) {
        this.tercerSecunString = tercerSecunString;
    }

    public String getIdTercerSecun() {
        return idTercerSecun;
    }

    public void setIdTercerSecun(String idTercerSecun) {
        this.idTercerSecun = idTercerSecun;
    }

    public String getTerceroString() {
        return terceroString;
    }

    public void setTerceroString(String terceroString) {
        this.terceroString = terceroString;
    }

    public String getTercerDepoString() {
        return tercerDepoString;
    }

    public void setTercerDepoString(String tercerDepoString) {
        this.tercerDepoString = tercerDepoString;
    }

    public String getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(String idTercero) {
        this.idTercero = idTercero;
    }

    public String getIdTercerDepo() {
        return idTercerDepo;
    }

    public void setIdTercerDepo(String idTercerDepo) {
        this.idTercerDepo = idTercerDepo;
    }

    public List<Familia> getListaFamilia() {
        return listaFamilia;
    }

    public void setListaFamilia(List<Familia> listaFamilia) {
        this.listaFamilia = listaFamilia;
    }

    public List<Familia> getListaFamiliaSelected() {
        return listaFamiliaSelected;
    }

    public void setListaFamiliaSelected(List<Familia> listaFamiliaSelected) {
        this.listaFamiliaSelected = listaFamiliaSelected;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List<Marca> getListaMarcasSelected() {
        return listaMarcasSelected;
    }

    public void setListaMarcasSelected(List<Marca> listaMarcasSelected) {
        this.listaMarcasSelected = listaMarcasSelected;
    }

    public String getFamiliasString() {
        return familiasString;
    }

    public void setFamiliasString(String familiasString) {
        this.familiasString = familiasString;
    }

    public String getMarcaString() {
        return marcaString;
    }

    public void setMarcaString(String marcaString) {
        this.marcaString = marcaString;
    }

    public String getUbiString() {
        return ubiString;
    }

    public void setUbiString(String ubiString) {
        this.ubiString = ubiString;
    }

    public String getClasifString() {
        return clasifString;
    }

    public void setClasifString(String clasifString) {
        this.clasifString = clasifString;
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

    public String getIdUbi() {
        return idUbi;
    }

    public void setIdUbi(String idUbi) {
        this.idUbi = idUbi;
    }

    public String getIdClasif() {
        return idClasif;
    }

    public void setIdClasif(String idClasif) {
        this.idClasif = idClasif;
    }

    public List<TipoHueco> getListaTipoHuecos() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        HuecoController ctl = new HuecoController(conf);
        listaTipoHuecos = ctl.getTiposHuecos();
        conf.cerrar();
        return listaTipoHuecos;
    }

    public void setListaTipoHuecos(List<TipoHueco> listaTipoHuecos) {
        this.listaTipoHuecos = listaTipoHuecos;
    }

    public int getControlSeries() {
        return controlSeries;
    }

    public void setControlSeries(int controlSeries) {
        this.controlSeries = controlSeries;
    }

    public String getReferenciaString() {
        return referenciaString;
    }

    public void setReferenciaString(String referenciaString) {
        this.referenciaString = referenciaString;
    }

    public String getDescripciónString() {
        return descripciónString;
    }

    public void setDescripciónString(String descripciónString) {
        this.descripciónString = descripciónString;
    }

    public String getLoteSerString() {
        return loteSerString;
    }

    public void setLoteSerString(String loteSerString) {
        this.loteSerString = loteSerString;
    }

    public String getCodBarString() {
        return codBarString;
    }

    public void setCodBarString(String codBarString) {
        this.codBarString = codBarString;
    }

    public boolean isQuitarSinStock() {
        return quitarSinStock;
    }

    public void setQuitarSinStock(boolean quitarSinStock) {
        this.quitarSinStock = quitarSinStock;
    }

    public boolean isAgruparLote() {
        return agruparLote;
    }

    public void setAgruparLote(boolean agruparLote) {
        this.agruparLote = agruparLote;
    }

    public String getTipoSelection() {
        return tipoSelection;
    }

    public void setTipoSelection(String tipoSelection) {
        this.tipoSelection = tipoSelection;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getArticuloString() {
        return articuloString;
    }

    public void setArticuloString(String articuloString) {
        this.articuloString = articuloString;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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

    public List<Centro> getListaCentros() {
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        CentroController ctl = new CentroController(conf);
        listaCentros = ctl.getCentros(false, 1);
        conf.cerrar();
        return listaCentros;
    }

    public void setListaCentros(List<Centro> listaCentros) {
        this.listaCentros = listaCentros;
    }
    // </editor-fold>

    public void buscar() {
        if (listaAlmacenes.isEmpty()) {
            idAlmacenes = "";
        } else if (listaAlmacenes.size() == 1) {
            idAlmacenes = "('" + listaAlmacenes.get(0).getCentroAlmacenPK().getIdCentro() + "')";
        } else {
            idAlmacenes = "('" + listaAlmacenes.get(0).getCentroAlmacenPK().getIdCentro() + "'";
            for (int i = 1; i < listaAlmacenes.size(); i++) {
                idAlmacenes += ",'" + listaAlmacenes.get(i).getCentroAlmacenPK().getIdCentro() + "'";
            }
            idAlmacenes += ")";
        }

        if (listaTipoHuecos.isEmpty()) {
            idTipoHueco = "";
        } else if (listaTipoHuecos.size() == 1) {
            idTipoHueco = "('" + listaTipoHuecos.get(0).getIdTipoHueco() + "')";
        } else {
            idTipoHueco = "('" + listaTipoHuecos.get(0).getIdTipoHueco() + "'";
            for (int i = 1; i < listaTipoHuecos.size(); i++) {
                idTipoHueco += ",'" + listaTipoHuecos.get(i).getIdTipoHueco() + "'";
            }
            idTipoHueco += ")";
        }

        stockFilter.setTiposHueco(idTipoHueco);

        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.StockController ctl = new StockController(conf);
        items = ctl.getConsultaStock(centerId, stockFilter);
        conf.cerrar();

        if (itemsSelected == null) {
            itemsSelected = items.get(0);
//            listaLotes = itemsSelected.;
        } else {
//            listaLotes = itemsSelected.getArticulo().getArticuloLoteList();
        }

//        if(loteSelected == null){
//            loteSelected=listaLotes.get(0);
//            listaUbicaciones=loteSelected.getArticulo().getArticuloHuecoList();
//        }else{
//            listaUbicaciones=loteSelected.getArticulo().getArticuloHuecoList();
//        }
    }

    public void busquedaLotes() {
        Respuesta r = new Respuesta();
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.StockController ctl = new StockController(conf);
        PConsultaStockArticulo itemStock = new PConsultaStockArticulo();

        itemsSelected;
        itemStock.setLista_lotes(new ArrayList<>());

        r = ctl.getConsultaStock_Cargar_Lista_Lotes(stockFilter, itemStock, null);
        conf.cerrar();

        if (r.correcto()) {
            listaLotes = itemStock.getLista_lotes();
        }
    }

    public void busquedaUbicacion() {
        Respuesta r = new Respuesta();
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.StockController ctl = new StockController(conf);
        batchStock.setLista_ubicaciones(new ArrayList<>());
        r = ctl.getConsultaStock_Cargar_Lista_Ubicaciones(stockFilter, itemStock, batchStock);
        conf.cerrar();

        if (r.correcto()) {
            locations = batchStock.getLista_ubicaciones();
        }
    }

    public void busquedaPalets() {
        Respuesta r = new Respuesta();
        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.StockController ctl = new StockController(conf);
        locationSelected.setLista_palet(new ArrayList<>());
        r = ctl.getConsultaStock_Cargar_Lista_Palets(itemStock, batchStock, locationSelected);
        conf.cerrar();

        if (r.correcto()) {
            pallets = locationSelected.getLista_palet();
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
            case "articulos": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "familias": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "marcas": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "clasif": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "tercero": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "tercerDepo": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

                PrimeFaces.current().dialog().openDynamic("components/selection", options, params);
                break;
            }
            case "tercerSecun": {
                String[] paletDetailList = {String.valueOf(clave)};
                String[] paletDetailArray = Arrays.copyOf(paletDetailList, paletDetailList.length);
                String paletDetail = Arrays.stream(paletDetailArray).collect(Collectors.joining(","));
                Map<String, List<String>> params = new HashMap<>();
                params.put("selection", Arrays.asList(paletDetail));

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
                case "articulos": {
                    List<Articulo> selectedItems = (List<Articulo>) event.getObject();
                    this.listaArticulosSelected = selectedItems;
                    if (listaArticulosSelected.isEmpty()) {
                        articuloString = "Sin articulo";
                    } else if (listaArticulosSelected.size() == 1) {
                        idArticulo = "('" + listaArticulosSelected.get(0).getIdArticulo() + "')";
                        articuloString = idArticulo + "";
                    } else {
                        articuloString = "VARIOS";
                        idArticulo = "('" + listaArticulosSelected.get(0).getIdArticulo() + "'";
                        for (int i = 1; i < listaArticulosSelected.size(); i++) {
                            idArticulo += ",'" + listaArticulosSelected.get(i).getIdArticulo() + "'";
                        }
                        idArticulo += ")";
                    }
                    stockFilter.setIdArticulos(idArticulo);
                    break;
                }
                case "familias": {
                    List<Familia> selectedItems = (List<Familia>) event.getObject();
                    this.listaFamiliaSelected = selectedItems;
                    if (listaFamiliaSelected.isEmpty()) {
                        familiasString = "Sin familia";
                    } else if (listaFamiliaSelected.size() == 1) {
                        familiasString = "" + listaFamiliaSelected.get(0).getNombre();
                        idFamilia = "('" + listaFamiliaSelected.get(0).getIdFamilia() + "')";
                    } else {
                        familiasString = "VARIOS";
                        idFamilia = "('" + listaFamiliaSelected.get(0).getIdFamilia() + "'";
                        for (int i = 1; i < listaFamiliaSelected.size(); i++) {
                            idFamilia += ",'" + listaFamiliaSelected.get(i).getIdFamilia() + "'";
                        }
                        idFamilia += ")";
                    }
                    stockFilter.setFamilias(idFamilia);
                    break;
                }
                case "marcas": {
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
                    stockFilter.setMarcas(idMarca);
                    break;
                }
                case "ubicacion": {
                    List<TipoHueco> selectedItems = (List<TipoHueco>) event.getObject();
                    this.listaUbiSelected = selectedItems;
                    if (listaUbiSelected.isEmpty()) {
                        ubiString = "Sin ubicación";
                    } else if (listaUbiSelected.size() == 1) {
                        ubiString = "" + listaUbiSelected.get(0).getDescripcion();
                        idUbi = "('" + listaUbiSelected.get(0).getIdTipoHueco() + "')";
                    } else {
                        ubiString = "VARIOS";
                        idUbi = "('" + listaUbiSelected.get(0).getIdTipoHueco() + "'";
                        for (int i = 1; i < listaUbiSelected.size(); i++) {
                            idUbi += ",'" + listaUbiSelected.get(i).getIdTipoHueco() + "'";
                        }
                        idUbi += ")";
                    }
                    stockFilter.setTiposHueco(idUbi);
                    break;
                }
                case "clasif": {
                    List<ArticuloClasificacion> selectedItems = (List<ArticuloClasificacion>) event.getObject();
                    this.listaArticClasSelected = selectedItems;
                    if (listaArticClasSelected.isEmpty()) {
                        clasifString = "Sin clasificación";
                    } else if (listaArticClasSelected.size() == 1) {
                        clasifString = "" + listaArticClasSelected.get(0).getDescripcion();
                        idClasif = "('" + listaArticClasSelected.get(0).getIdArticuloClasificacion() + "')";
                    } else {
                        clasifString = "VARIOS";
                        idClasif = "('" + listaArticClasSelected.get(0).getIdArticuloClasificacion() + "'";
                        for (int i = 1; i < listaArticClasSelected.size(); i++) {
                            idClasif += ",'" + listaArticClasSelected.get(i).getIdArticuloClasificacion() + "'";
                        }
                        idClasif += ")";
                    }
                    stockFilter.setClasificaciones(idClasif);
                    break;
                }
                case "tercero": {
                    List<Tercero> selectedItems = (List<Tercero>) event.getObject();
                    this.listaTerceroSelected = selectedItems;
                    if (listaTerceroSelected.isEmpty()) {
                        terceroString = "Sin terceros";
                    } else if (listaTerceroSelected.size() == 1) {
                        terceroString = "" + listaTerceroSelected.get(0).getRazonSocial();
                        idTercero = "('" + listaTerceroSelected.get(0).getIdTercero() + "')";
                    } else {
                        terceroString = "VARIOS";
                        idTercero = "('" + listaTerceroSelected.get(0).getIdTercero() + "'";
                        for (int i = 1; i < listaTerceroSelected.size(); i++) {
                            idTercero += ",'" + listaTerceroSelected.get(i).getIdTercero() + "'";
                        }
                        idTercero += ")";
                    }
                    stockFilter.setTerceros(idTercero);
                    break;
                }
                case "tercerDepo": {
                    List<Tercero> selectedItems = (List<Tercero>) event.getObject();
                    this.listaTercerDepoSelected = selectedItems;
                    if (listaTercerDepoSelected.isEmpty()) {
                        tercerDepoString = "Sin clasificación";
                    } else if (listaTercerDepoSelected.size() == 1) {
                        tercerDepoString = "" + listaTercerDepoSelected.get(0).getRazonSocial();
                        idTercerDepo = "('" + listaTercerDepoSelected.get(0).getIdTercero() + "')";
                    } else {
                        tercerDepoString = "VARIOS";
                        idTercerDepo = "('" + listaTercerDepoSelected.get(0).getIdTercero() + "'";
                        for (int i = 1; i < listaTercerDepoSelected.size(); i++) {
                            idTercerDepo += ",'" + listaTercerDepoSelected.get(i).getIdTercero() + "'";
                        }
                        idTercerDepo += ")";
                    }
                    stockFilter.setTerceroDeposito(idTercerDepo);
                    break;
                }
                case "tercerSecun": {
                    List<Tercero> selectedItems = (List<Tercero>) event.getObject();
                    this.listaTercerSecunSelected = selectedItems;
                    if (listaTercerSecunSelected.isEmpty()) {
                        tercerSecunString = "Sin clasificación";
                    } else if (listaTercerSecunSelected.size() == 1) {
                        tercerSecunString = "" + listaTercerSecunSelected.get(0).getRazonSocial();
                        idTercerSecun = "('" + listaTercerSecunSelected.get(0).getIdTercero() + "')";
                    } else {
                        tercerSecunString = "VARIOS";
                        idTercerSecun = "('" + listaTercerSecunSelected.get(0).getIdTercero() + "'";
                        for (int i = 1; i < listaTercerSecunSelected.size(); i++) {
                            idTercerSecun += ",'" + listaTercerSecunSelected.get(i).getIdTercero() + "'";
                        }
                        idTercerSecun += ")";
                    }
                    stockFilter.setTerceroSecundario(tercerSecunString);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void limpiarArticulo() {
        if (!articuloString.isEmpty()) {
            articuloString = "";
            idArticulo = "";
        }
    }

    public void limpiarFamilia() {
        if (!familiasString.isEmpty()) {
            familiasString = "";
            idFamilia = "";
        }
    }

    public void limpiarMarca() {
        if (!marcaString.isEmpty()) {
            marcaString = "";
            idMarca = "";
        }
    }

    public void limpiarUbicacion() {
        if (!ubiString.isEmpty()) {
            ubiString = "";
            idUbi = "";
        }
    }

    public void limpiarClasif() {
        if (!clasifString.isEmpty()) {
            clasifString = "";
            idClasif = "";
        }
    }

    public void limpiarTercero() {
        if (!terceroString.isEmpty()) {
            terceroString = "";
            idTercero = "";
        }
    }

    public void limpiarTercerDepo() {
        if (!tercerDepoString.isEmpty()) {
            tercerDepoString = "";
            idTercerDepo = "";
        }
    }

    public void limpiarTercerSecun() {
        if (!tercerSecunString.isEmpty()) {
            tercerSecunString = "";
            idTercerSecun = "";
        }
    }

    public void onRowSelect(SelectEvent<ArticuloStockCentroAlmacen> event) {
        buscar();
    }

}
