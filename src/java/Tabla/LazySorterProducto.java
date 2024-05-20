
package Tabla;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorterProducto implements Comparator<Product> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterProducto(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Product customer1, Product customer2) {
        try {
            Object value1 = ShowcaseUtil.getPropertyValueViaReflection(customer1, sortField);
            Object value2 = ShowcaseUtil.getPropertyValueViaReflection(customer2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}