
package Tabla;

import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorter implements Comparator<Product> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Product product1, Product product2) {
        try {
            Object value1 = ShowcaseUtil.getPropertyValueViaReflection(product1, sortField);
            Object value2 = ShowcaseUtil.getPropertyValueViaReflection(product2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}