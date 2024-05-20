package Items;

import Tabla.ShowcaseUtil;
import java.beans.IntrospectionException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.faces.context.FacesContext;
import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

public class LazyCustomerDataModel extends LazyDataModel<es.inerttia.ittws.controllers.entities.custom.Articulo> {

    private static final long serialVersionUID = 1L;

    private List<es.inerttia.ittws.controllers.entities.custom.Articulo> datasource;

    public LazyCustomerDataModel(List<es.inerttia.ittws.controllers.entities.custom.Articulo> datasource) {
        this.datasource = datasource;
    }

    @Override
    public es.inerttia.ittws.controllers.entities.custom.Articulo getRowData(String rowKey) {
        for (es.inerttia.ittws.controllers.entities.custom.Articulo articulo : datasource) {
            if (articulo.getIdArticulo().equals(rowKey)) {
                return articulo;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(es.inerttia.ittws.controllers.entities.custom.Articulo customer) {
        return String.valueOf(customer.getIdArticulo());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<es.inerttia.ittws.controllers.entities.custom.Articulo> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset cannot be negative: " + offset);
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero: " + pageSize);
        }

        List<es.inerttia.ittws.controllers.entities.custom.Articulo> customers = datasource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

        if (!sortBy.isEmpty()) {
            List<Comparator<es.inerttia.ittws.controllers.entities.custom.Articulo>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<es.inerttia.ittws.controllers.entities.custom.Articulo> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            customers.sort(cp);
        }

        return customers;
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
        boolean matching = true;

        for (FilterMeta filter : filterBy) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();

            try {
                if (filter.getField().equals("globalFilter")) {
                    es.inerttia.ittws.controllers.entities.custom.Articulo p = (es.inerttia.ittws.controllers.entities.custom.Articulo) o;

                    String value = ((String) filterValue).toLowerCase();
                    matching = (p.getIdArticulo() != null ? p.getIdArticulo().toLowerCase().contains(value) : false)
                            || (p.getReferencia() != null ? p.getReferencia().toLowerCase().contains(value) : false)
                            || (p.getDescripcion() != null ? p.getDescripcion().toLowerCase().contains(value) : false)
                            || (p.getCodigoBarras() != null ? p.getCodigoBarras().toLowerCase().contains(value) : false)
                            || (p.getDescripcion() != null ? p.getDescripcion().toLowerCase().contains(value) : false)
                            || (p.getIdArticuloDeposito() != null ? p.getIdArticuloDeposito().toLowerCase().contains(value) : false)
                            || (p.getFechaAlta() != null ? formatFechaESP(p.getFechaAlta()).contains(value) : false)
                            || (p.getIdMarca().getNombre()!= null ? p.getIdMarca().getNombre().toLowerCase().contains(value) : false)
                            || (p.getIdFamilia().getNombre()!= null ? p.getIdFamilia().getNombre().toLowerCase().contains(value) : false)
                            || (p.getFechaUltModif()!= null ? formatFechaESP(p.getFechaUltModif()).contains(value) : false)                           
                            || (p.getEstadoTexto()!= null ? p.getEstadoTexto().contains(value) : false)                           
                            || (p.getUsuarioUltBloqueoVentas().getUsername()!= null ? p.getUsuarioUltBloqueoVentas().getUsername().toLowerCase().contains(value) : false)
                            || (p.getFechaUltBloqueoVentas()!= null ? formatFechaESP(p.getFechaUltBloqueoVentas()).contains(value) : false)
                            || (p.getArticuloGrupo().getEtiquetaGrupo().getDescripcion()!= null ? p.getArticuloGrupo().getEtiquetaGrupo().getDescripcion().toLowerCase().contains(value) : false)
                            || (p.getTxtTipoPicking()!= null ? p.getTxtTipoPicking().toLowerCase().contains(value) : false)
                            || (p.getIdArticuloClasificacion().getDescripcion()!= null ? p.getIdArticuloClasificacion().getDescripcion().toLowerCase().contains(value) : false)
                            || (p.getTxtClasificacion()!= null ? p.getTxtClasificacion().toLowerCase().contains(value) : false);

                } else {
                    Object columnValue = String.valueOf(ShowcaseUtil.getPropertyValueViaReflection(o, filter.getField()));
                    matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
                }
            } catch (ReflectiveOperationException | IntrospectionException e) {
                matching = false;
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }

    public String formatFechaESP(Date f) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        return sdf.format(f);
    }

}
