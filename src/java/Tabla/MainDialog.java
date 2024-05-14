package Tabla;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PF;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;


@ManagedBean(name = "MainDialog")
@ViewScoped
public class MainDialog {

    private List<Lugar> listaLugares;
    private List<Lugar> selectedLugares;

    @PostConstruct
    public void init() {
        listaLugares = new ArrayList<>();
        listaLugares.add(new Lugar(1, "Malaga", "España"));
        listaLugares.add(new Lugar(2, "Madrid", "España"));
        listaLugares.add(new Lugar(3, "Paris", "Francia"));
        listaLugares.add(new Lugar(4, "Roma", "Italia"));
        listaLugares.add(new Lugar(5, "Barcelona", "España"));

        selectedLugares = new ArrayList<>();
    }

    public void onItemUnselect(UnselectEvent event) {
        Lugar l = (Lugar) event.getObject();
        selectedLugares.remove(l);
    }

    public void selectedOptionsChanged(Lugar lugar) {
        if (selectedLugares.contains(lugar)) {
            selectedLugares.remove(lugar);
        } else {
            selectedLugares.add(lugar);
        }
    }

    public void selectAll() {
        selectedLugares.addAll(listaLugares);
    }

    public void addAll() {
        PrimeFaces.current().dialog().closeDynamic(selectedLugares);
    }

    public List<Lugar> getSelectedLugares() {
        return selectedLugares;
    }

    public void setSelectedLugares(List<Lugar> selectedLugares) {
        this.selectedLugares = selectedLugares;
    }

    public boolean hasSelectedLugares() {
        return !selectedLugares.isEmpty();
    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
    }

    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
    }

    
}
