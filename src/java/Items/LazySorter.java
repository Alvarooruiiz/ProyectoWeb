
package Items;

import Tabla.ShowcaseUtil;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

public class LazySorter implements Comparator<es.inerttia.ittws.controllers.entities.custom.Articulo> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(es.inerttia.ittws.controllers.entities.custom.Articulo product1, es.inerttia.ittws.controllers.entities.custom.Articulo product2) {
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