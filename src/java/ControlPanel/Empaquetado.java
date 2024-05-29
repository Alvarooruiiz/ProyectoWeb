/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPanel;

import es.inerttia.ittws.controllers.EmpaquetadoController;
import es.inerttia.ittws.controllers.entities.EmpaquetadoActual;
import es.inerttia.ittws.controllers.entities.EmpaquetadoActualCL;
import es.inerttia.ittws.controllers.entities.EmpaquetadoActualDetalle;
import es.inerttia.ittws.controllers.entities.EmpaquetadoActualLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

@ManagedBean(name = "Empaquetado")
@ViewScoped
public class Empaquetado {

    private EmpaquetadoActual packed;

    private int centerId = 1;
    private int thirdPartyId = 1;
    private int thirdPartyCenterId = 1;

    private List<EmpaquetadoActualCL> packedContainers;
    private List<EmpaquetadoActualCL> packedContainersFiltered;
    private List<EmpaquetadoActualDetalle> packedDetails;
    private List<EmpaquetadoActualDetalle> packedDetailsFiltered;
    private List<EmpaquetadoActualCL> packedContainersDetail;
    private List<EmpaquetadoActualCL> packedContainersDetailFiltered;
    private List<EmpaquetadoActualLog> packedLogs;
    private List<EmpaquetadoActualLog> packedLogsFiltered;

    
    // <editor-fold defaultstate="collapsed" desc=" getters y setters ">
    public EmpaquetadoActual getPacked() {
        return packed;
    }

    public void setPacked(EmpaquetadoActual packed) {
        this.packed = packed;
    }

    public List<EmpaquetadoActualCL> getPackedContainersFiltered() {
        return packedContainersFiltered;
    }

    public void setPackedContainersFiltered(List<EmpaquetadoActualCL> packedContainersFiltered) {
        this.packedContainersFiltered = packedContainersFiltered;
    }

    public List<EmpaquetadoActualDetalle> getPackedDetailsFiltered() {
        return packedDetailsFiltered;
    }

    public void setPackedDetailsFiltered(List<EmpaquetadoActualDetalle> packedDetailsFiltered) {
        this.packedDetailsFiltered = packedDetailsFiltered;
    }

    public List<EmpaquetadoActualCL> getPackedContainersDetailFiltered() {
        return packedContainersDetailFiltered;
    }

    public void setPackedContainersDetailFiltered(List<EmpaquetadoActualCL> packedContainersDetailFiltered) {
        this.packedContainersDetailFiltered = packedContainersDetailFiltered;
    }

    public List<EmpaquetadoActualLog> getPackedLogsFiltered() {
        return packedLogsFiltered;
    }

    public void setPackedLogsFiltered(List<EmpaquetadoActualLog> packedLogsFiltered) {
        this.packedLogsFiltered = packedLogsFiltered;
    }

    
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(int thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public int getThirdPartyCenterId() {
        return thirdPartyCenterId;
    }

    public void setThirdPartyCenterId(int thirdPartyCenterId) {
        this.thirdPartyCenterId = thirdPartyCenterId;
    }

    public List<EmpaquetadoActualCL> getPackedContainers() {
        return packedContainers;
    }

    public void setPackedContainers(List<EmpaquetadoActualCL> packedContainers) {
        this.packedContainers = packedContainers;
    }

    public List<EmpaquetadoActualDetalle> getPackedDetails() {
        return packedDetails;
    }

    public void setPackedDetails(List<EmpaquetadoActualDetalle> packedDetails) {
        this.packedDetails = packedDetails;
    }

    public List<EmpaquetadoActualCL> getPackedContainersDetail() {
        return packedContainersDetail;
    }

    public void setPackedContainersDetail(List<EmpaquetadoActualCL> packedContainersDetail) {
        this.packedContainersDetail = packedContainersDetail;
    }

    public List<EmpaquetadoActualLog> getPackedLogs() {
        return packedLogs;
    }

    public void setPackedLogs(List<EmpaquetadoActualLog> packedLogs) {
        this.packedLogs = packedLogs;
    }
    // </editor-fold>
    

    @PostConstruct
    public void init() {
        packedContainersFiltered = new ArrayList<>();
        packedDetailsFiltered = new ArrayList<>();
        packedContainersDetailFiltered = new ArrayList<>();
        packedLogsFiltered = new ArrayList<>();
        
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterValuesMap();

        String[] paletDetails = paramMap.get("thirdPartyId");
        if (paletDetails != null && paletDetails.length > 0) {
            thirdPartyId = Integer.parseInt(paletDetails[0]);
        }

        String[] paletCentro = paramMap.get("thirdPartyCenterId");
        if (paletCentro != null && paletCentro.length > 0) {
            thirdPartyCenterId = Integer.parseInt(paletCentro[0]);
        }

        String[] paletCenter = paramMap.get("centerId");
        if (paletCenter != null && paletCenter.length > 0) {
            centerId = Integer.parseInt(paletCenter[0]);
        }

        es.inerttia.ittwscomun.Configuracion conf = new es.inerttia.ittwscomun.Configuracion();
        es.inerttia.ittws.controllers.EmpaquetadoController ctl = new EmpaquetadoController(conf);
        packedContainers = ctl.getContenedoresTercero(thirdPartyId, thirdPartyCenterId, centerId);
        packed = ctl.getEmpaquetadoActualTercero(thirdPartyId, thirdPartyCenterId);
        packedDetails = ctl.getPedidosExpedicionesEmpaquetado(packed.getIdEmpaquetadoActual());
        packedContainersDetail = ctl.getContenedoresEmpaquetado(packed.getIdEmpaquetadoActual());
        packedLogs = ctl.getLogEmpaquetado(packed.getIdEmpaquetadoActual());
        
        conf.cerrar();
    }
    
    public void dialogContenedorLogistico(String contLogID){
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

        params.put("contLogID", Arrays.asList(contLogID + ""));

        PrimeFaces.current().dialog().openDynamic("contenedorLogistico", options, params);
    }
    
}
