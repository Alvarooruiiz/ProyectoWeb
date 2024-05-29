/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Items;

import Tabla.PeticionJSON;
import es.inerttia.ittws.controllers.CentroAlmacenController;
import es.inerttia.ittws.controllers.PedidoSalidaClasificacionController;
import es.inerttia.ittws.controllers.PedidoSalidaController;
import es.inerttia.ittws.controllers.TerceroCentroController;
import es.inerttia.ittws.controllers.TerceroController;
import es.inerttia.ittws.controllers.TipoPedidoController;
import es.inerttia.ittws.controllers.entities.Calle;
import es.inerttia.ittws.controllers.entities.CentroAlmacen;
import es.inerttia.ittws.controllers.entities.Clasificacion;
import es.inerttia.ittws.controllers.entities.Familia;
import es.inerttia.ittws.controllers.entities.Marca;
import es.inerttia.ittws.controllers.entities.NivelClasificacion;
import es.inerttia.ittws.controllers.entities.PedidoSalidaClasificacion;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittws.controllers.entities.TipoPedido;
import es.inerttia.ittws.controllers.entities.custom.PedidoSalida;
import es.inerttia.ittws.controllers.entities.TerceroCentro;
import es.inerttia.ittws.controllers.entities.custom.Zona;
import es.inerttia.ittwscomun.Configuracion;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import es.inerttia.ittws.controllers.entities.Articulo;



@ManagedBean(name = "SelectionDialog")
@ViewScoped
public class SelectionDialog {

    private String tipoTabla;
    private List<Tercero> listaTercero;
    private List<Tercero> listaTerceroSelected;
    private List<Familia> listaFamilia;
    private List<Familia> listaFamiliaSelected;
    private List<Marca> listaMarcas;
    private List<Marca> listaMarcasSelected;
    private List<NivelClasificacion> listaNiveles;
    private List<NivelClasificacion> listaNivelesSelected;

    private List<CentroAlmacen> listCentrosAlmacenes;
    private CentroAlmacen centroAlmacenSelected;

    private List<Zona> listaCalles;
    private List<Zona> listaCallesSelected;
    private List<PedidoSalidaClasificacion> listaClasif;
    private List<PedidoSalidaClasificacion> listaClasifSelected;

    private int idCenter = 1;
    private int idWarehouse = 1;
    private String idCentroAlmacen;

    private List<Tercero> listaTercerosSingle;
    private Tercero terceroSelected;

    private List<TerceroCentro> listaCentroTercero;
    private TerceroCentro centroTerceroSelected;
    private String idTercero;
    
    
    private List<Articulo> listaArticulos;
    

    // <editor-fold defaultstate="collapsed" desc=" getters y setters "> 
    public List<Zona> getListaCallesSelected() {
        return listaCallesSelected;
    }

    public void setListaCallesSelected(List<Zona> listaCallesSelected) {
        this.listaCallesSelected = listaCallesSelected;
    }

    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    
    

    public TerceroCentro getCentroTerceroSelected() {
        return centroTerceroSelected;
    }

    public void setCentroTerceroSelected(TerceroCentro centroTerceroSelected) {
        this.centroTerceroSelected = centroTerceroSelected;
    }
    
    

    public List<TerceroCentro> getListaCentroTercero() {
        return listaCentroTercero;
    }

    public void setListaCentroTercero(List<TerceroCentro> listaCentroTercero) {
        this.listaCentroTercero = listaCentroTercero;
    }

    public String getIdTercero() {
        return idTercero;
    }

    public void setIdTercero(String idTercero) {
        this.idTercero = idTercero;
    }
    
    

    public Tercero getTerceroSelected() {
        return terceroSelected;
    }

    public void setTerceroSelected(Tercero terceroSelected) {
        this.terceroSelected = terceroSelected;
    }

    public List<Tercero> getListaTercerosSingle() {
        return listaTercerosSingle;
    }

    public void setListaTercerosSingle(List<Tercero> listaTercerosSingle) {
        this.listaTercerosSingle = listaTercerosSingle;
    }

    public List<CentroAlmacen> getListCentrosAlmacenes() {
        return listCentrosAlmacenes;
    }

    public void setListCentrosAlmacenes(List<CentroAlmacen> listCentrosAlmacenes) {
        this.listCentrosAlmacenes = listCentrosAlmacenes;
    }

    public CentroAlmacen getCentroAlmacenSelected() {
        return centroAlmacenSelected;
    }

    public void setCentroAlmacenSelected(CentroAlmacen centroAlmacenSelected) {
        this.centroAlmacenSelected = centroAlmacenSelected;
    }

    public String getIdCentroAlmacen() {
        return idCentroAlmacen;
    }

    public void setIdCentroAlmacen(String idCentroAlmacen) {
        this.idCentroAlmacen = idCentroAlmacen;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public int getIdWarehouse() {
        return idWarehouse;
    }

    public void setIdWarehouse(int idWarehouse) {
        this.idWarehouse = idWarehouse;
    }

    public List<PedidoSalidaClasificacion> getListaClasifSelected() {
        return listaClasifSelected;
    }

    public void setListaClasifSelected(List<PedidoSalidaClasificacion> listaClasifSelected) {
        this.listaClasifSelected = listaClasifSelected;
    }

    public List<Zona> getListaCalles() {
        return listaCalles;
    }

    public void setListaCalles(List<Zona> listaCalles) {
        this.listaCalles = listaCalles;
    }

    public List<PedidoSalidaClasificacion> getListaClasif() {
        return listaClasif;
    }

    public void setListaClasif(List<PedidoSalidaClasificacion> listaClasif) {
        this.listaClasif = listaClasif;
    }

    public String getTipoTabla() {
        return tipoTabla;
    }

    public void setTipoTabla(String tipoTabla) {
        this.tipoTabla = tipoTabla;
    }

    public List<Tercero> getListaTercero() {
        return listaTercero;
    }

    public void setListaTercero(List<Tercero> listaTercero) {
        this.listaTercero = listaTercero;
    }

    public List<Familia> getListaFamilia() {
        return listaFamilia;
    }

    public void setListaFamilia(List<Familia> listaFamilia) {
        this.listaFamilia = listaFamilia;
    }

    public List<Marca> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<Marca> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    public List<NivelClasificacion> getListaNiveles() {
        return listaNiveles;
    }

    public void setListaNiveles(List<NivelClasificacion> listaNiveles) {
        this.listaNiveles = listaNiveles;
    }

    public List<Tercero> getListaTerceroSelected() {
        return listaTerceroSelected;
    }

    public void setListaTerceroSelected(List<Tercero> listaTerceroSelected) {
        this.listaTerceroSelected = listaTerceroSelected;
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

    // </editor-fold>
    @PostConstruct
    public void init() {

        listaFamiliaSelected = new ArrayList();
        listaMarcasSelected = new ArrayList();
        listaNivelesSelected = new ArrayList();
        listaTerceroSelected = new ArrayList();
        listaTercerosSingle = new ArrayList();

        listaCalles = new ArrayList();
        listaClasif = new ArrayList();

        Map<String, String[]> paramMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterValuesMap();

        if (paramMap.containsKey("articulos")) {
            String[] paletDetails = paramMap.get("selection");
            tipoTabla = paletDetails[0];    
        } else {
            String[] paletDetails = paramMap.get("selection");
            tipoTabla = paletDetails[0];
        }
        llenarListas();
    }

    public void addAllTercero() {
        PrimeFaces.current().dialog().closeDynamic(listaTerceroSelected);
    }

    public void addAllFamilia() {
        PrimeFaces.current().dialog().closeDynamic(listaFamiliaSelected);

    }

    public void addAllNiveles() {
        PrimeFaces.current().dialog().closeDynamic(listaNivelesSelected);
    }

    public void addAllMarcas() {
        PrimeFaces.current().dialog().closeDynamic(listaMarcasSelected);

    }

    public void addAllCalles() {
        PrimeFaces.current().dialog().closeDynamic(listaCallesSelected);
    }

    public void addAllClasif() {
        PrimeFaces.current().dialog().closeDynamic(listaClasifSelected);

    }

    public void addCentroAlmacen() {
        PrimeFaces.current().dialog().closeDynamic(centroAlmacenSelected);
    }

    public void addTerceroSingle() {
        PrimeFaces.current().dialog().closeDynamic(terceroSelected);
    }

    public void addCentroTercero() {
        PrimeFaces.current().dialog().closeDynamic(centroTerceroSelected);
    }

    private void llenarListas() {
        if (null != tipoTabla) {
            switch (tipoTabla) {
                case "articulos": {
                    Configuracion conf = new Configuracion();
                    es.inerttia.ittws.controllers.ArticuloController ctl10 = new es.inerttia.ittws.controllers.ArticuloController(conf);
                    listaArticulos = ctl10.getArticulosConsulta();
                    conf.cerrar();
                    break;
                }
                
                default:
                    break;
            }
        }
    }
}
