/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Items;

import Tabla.PeticionJSON;
import es.inerttia.ittws.controllers.TerceroController;
import es.inerttia.ittws.controllers.entities.Familia;
import es.inerttia.ittws.controllers.entities.Marca;
import es.inerttia.ittws.controllers.entities.NivelClasificacion;
import es.inerttia.ittws.controllers.entities.Tercero;
import es.inerttia.ittwscomun.Configuracion;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

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

    // <editor-fold defaultstate="collapsed" desc=" getters y setters "> 
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
        List<String> itemParameters;

        String paletDetails = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("selection");

        if (paletDetails != null) {
            itemParameters = Arrays.asList(paletDetails.split(","));
            tipoTabla = itemParameters.get(0);
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

    private void llenarListas() {
        if (null != tipoTabla) {
            switch (tipoTabla) {
                case "tercero": {
                    Configuracion conf = new Configuracion();
                    es.inerttia.ittws.controllers.TerceroController ctl2 = new TerceroController(conf);
                    listaTercero = ctl2.getTercerosDeposito();
                    conf.cerrar();
                    break;
                }
                case "familia": {
                    Configuracion conf = new Configuracion();
                    es.inerttia.ittws.controllers.FamiliaController ctl11 = new es.inerttia.ittws.controllers.FamiliaController(conf);
                    listaFamilia = ctl11.getFamiliasSeleccion();
                    conf.cerrar();
                    break;
                }
                case "marca": {
                    Configuracion conf = new Configuracion();
                    es.inerttia.ittws.controllers.MarcaController ctl12 = new es.inerttia.ittws.controllers.MarcaController(conf);
                    listaMarcas = ctl12.getMarcasSeleccion();
                    conf.cerrar();
                    break;
                }
                case "nivel": {
                    Configuracion conf = new Configuracion();
                    es.inerttia.ittws.controllers.NivelClasificacionController ctl13 = new es.inerttia.ittws.controllers.NivelClasificacionController(conf);
                    listaNiveles = ctl13.getNivelesClasificacion();
                    conf.cerrar();
                    break;
                }
                default:
                    break;
            }
        }
    }

}
