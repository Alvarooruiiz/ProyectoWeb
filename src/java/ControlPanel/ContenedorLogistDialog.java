package ControlPanel;

import es.inerttia.ittws.controllers.ContenedorLogisticoController;
import es.inerttia.ittws.controllers.EmpaquetadoController;
import es.inerttia.ittws.controllers.entities.custom.ContenedorLogistico;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "ContLog")
@ViewScoped
public class ContenedorLogistDialog {

    private String conLogisID;
    private ContenedorLogistico logisticContainer;

    // <editor-fold defaultstate="collapsed" desc=" getters y setters ">
    public String getConLogisID() {
        return conLogisID;
    }

    public void setConLogisID(String conLogisID) {
        this.conLogisID = conLogisID;
    }

    public ContenedorLogistico getLogisticContainer() {
        return logisticContainer;
    }

    public void setLogisticContainer(ContenedorLogistico logisticContainer) {
        this.logisticContainer = logisticContainer;
    }
    
    

    // </editor-fold>
    @PostConstruct
    public void init() {
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterValuesMap();

        String[] paletDetails = paramMap.get("contLogID");
        if (paletDetails != null && paletDetails.length > 0) {
            conLogisID = paletDetails[0];
        }

        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.ContenedorLogisticoController ctl = new ContenedorLogisticoController(conf);
        logisticContainer = ctl.getContenedorLogisticoFicha(conLogisID);

        conf.cerrar();
    }
}
