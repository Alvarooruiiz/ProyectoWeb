/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

import es.inerttia.ittwsEntidades.wsAlmacen.Palet;
import es.inerttia.ittwsEntidades.wsAlmacen.PaletLinea;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "ArticuloPalet")
@ViewScoped
public class ArticuloPalet {

    private List<Palet> listadoPalets;
    private List<PaletLinea> listadoLineasPalet;
    private Palet palet;
    private String ssccCode;

    public List<Palet> getListadoPalets() {
        return listadoPalets;
    }

    public void setListadoPalets(List<Palet> listadoPalets) {
        this.listadoPalets = listadoPalets;
    }

    public List<PaletLinea> getListadoLineasPalet() {
        return listadoLineasPalet;
    }

    public void setListadoLineasPalet(List<PaletLinea> listadoLineasPalet) {
        this.listadoLineasPalet = listadoLineasPalet;
    }

    public Palet getPalet() {
        return palet;
    }

    public void setPalet(Palet palet) {
        this.palet = palet;
    }

    public String getSsccCode() {
        return ssccCode;
    }

    public void setSsccCode(String ssccCode) {
        this.ssccCode = ssccCode;
    }

    
    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        List<String> itemParameters = new ArrayList();

        String paletDetails = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("palet");

        if (paletDetails != null) {
            itemParameters = Arrays.asList(paletDetails.split(","));
            PeticionJSON peticionJSON = new PeticionJSON();
            ssccCode = itemParameters.get(0);

            try {
                peticionJSON.hacerLlamada(ssccCode);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ArticuloPalet.class.getName()).log(Level.SEVERE, null, ex);
            }

            listadoPalets = peticionJSON.getPaletsRespuesta().getPalets();

            if (listadoPalets.isEmpty()) {
                showError("No se han encontrado palet por ese codigo");
            } else {
                palet = listadoPalets.get(0);
                listadoLineasPalet = palet.getLineas();
                System.out.println(palet);
            }
        }
    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
    }
}
