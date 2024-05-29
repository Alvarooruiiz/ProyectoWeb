/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Stock;

import es.inerttia.ittws.controllers.CentroAlmacenController;
import es.inerttia.ittws.controllers.CentroController;
import es.inerttia.ittws.controllers.entities.Centro;
import es.inerttia.ittws.controllers.entities.CentroAlmacen;
import es.inerttia.ittws.controllers.entities.PedidoSalidaClasificacion;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittws.controllers.entities.TerceroCentro;
import es.inerttia.ittws.controllers.entities.custom.Zona;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private String articuloString;
    private String tipoSelection;

    private int estado;
    
    private boolean agruparLote;
    private boolean quitarSinStock;

    // <editor-fold defaultstate="collapsed" desc=" getters y setters ">
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
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

                }
                break;
            }
        }
    }
}
