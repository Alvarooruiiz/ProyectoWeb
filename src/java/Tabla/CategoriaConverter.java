/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tabla;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("categoryConverter")
public class CategoriaConverter implements Converter<Categoria> {

    @Override
    public Categoria getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Main mainBean = context.getApplication().evaluateExpressionGet(context, "#{Main}", Main.class);
        for (Categoria category : mainBean.getCategories()) {
            if (category.getCod() == Integer.parseInt(value)) {
                return category;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Categoria value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getCod());
    }
}