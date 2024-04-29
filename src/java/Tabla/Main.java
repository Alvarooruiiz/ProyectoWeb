package Tabla;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aruize
 */
@ManagedBean(name = "Main")
@ViewScoped
public class Main {

    private List<Product> lista;
    private Product selectedProduct;
    private boolean editando;

    @PostConstruct
    public void init() {
        lista = new ArrayList<>();
        lista.add(new Product("f230fh0g3", "Bamboo", "Accessories", 24, 25.50,new Date(1900/01/01), true));
        lista.add(new Product("nvklal433", "Black Watch", "Accessories", 61, 5.50,new Date(2000/01/01), false));
        lista.add(new Product("zz21cz3c1", "Blue Band", "Fitness", 2, 50.50,new Date(2005/01/11), true));
        lista.add(new Product("244wgerg2", "Blue T-Shirt", "Clothing", 25, 100,new Date(2010/03/01), false));
    }

    public List<Product> getLista() {
        return lista;
    }

    public void setLista(List<Product> lista) {
        this.lista = lista;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public void openNew() {
        selectedProduct = new Product();
        editando = false;
    }

    public void addProduct() {
        if (!editando) {
            if (selectedProduct.getCode() == null || selectedProduct.getCode().isEmpty()) {
                showError("El código no es correcto o está vacío");
            } else if (selectedProduct.getName().isEmpty()) {
                showError("El nombre no es correcto o está vacío");
            } else if (selectedProduct.getCategory().isEmpty() || selectedProduct.getCategory() == null) {
                showError("La categoría no es correcta o está vacía");
            } else if (selectedProduct.getQuantity() < 0) {
                showError("La cantidad no puede ser menor de 0");
            } else {
                boolean codeExists = false;
                for (Product product : lista) {
                    if (product.getCode().equals(selectedProduct.getCode())) {
                        codeExists = true;
                        break;
                    }
                }

                if (codeExists) {
                    showError("El código del producto ya existe en la lista");
                } else {
                    lista.add(selectedProduct);
                    PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                }
            }
        } else {
            for (Product product : lista) {
                if (product.getCode().equals(selectedProduct.getCode())) {
                    product = selectedProduct;
                    PrimeFaces.current().executeScript("PF('dlg1New').hide()");
                    showMessaggeGood("Se ha añadido un producto");
                }
            }
        }

    }

    public void showError(String error) {
        FacesContext.getCurrentInstance().addMessage("Error Message", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message", error));
    }
    public void showMessaggeGood(String error) {
        FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", error));
    }

    public void deleteProduct(Product p) {
        lista.remove(p);
    }

    public void editProduct(Product p) {
        editando = true;
        this.selectedProduct = p;

    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
