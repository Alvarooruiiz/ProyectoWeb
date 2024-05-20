/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

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

public class LazyCustomerDataModelProductos extends LazyDataModel<Product> {

    private static final long serialVersionUID = 1L;

    private List<Product> datasource;

    public LazyCustomerDataModelProductos(List<Product> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Product getRowData(String rowKey) {
        for (Product p : datasource) {
            if (p.getCode().equals(rowKey)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(Product product) {
        return String.valueOf(product.getCode());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<Product> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

        List<Product> customers = datasource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

        if (!sortBy.isEmpty()) {
            List<Comparator<Product>> comparators = sortBy.values().stream()
                    .map(o -> new LazySorterProducto(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Product> cp = ComparatorUtils.chainedComparator(comparators);
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
                    Product p = (Product) o;
                    
                    String value = ((String) filterValue).toLowerCase();
                    matching = (p.getCode() != null ? p.getCode().toLowerCase().contains(value) : false)
                            || (p.getName() != null ? p.getName().toLowerCase().contains(value) : false)
                            || (p.getCategory()!= null ? p.getCategory().getNombre().toLowerCase().contains(value) : false)
                            || (p.getBirth()!= null ? formatFechaESP(p.getBirth()).contains(value) : false);

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
    
    public String formatFechaESP(Date f){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        return sdf.format(f);
    }

}
