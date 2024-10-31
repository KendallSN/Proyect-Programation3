
package cr.ac.una.sigeceuna.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Filter {
    public ObjectProperty<String> operator;
    public ObjectProperty<String> attribute;
    public SimpleStringProperty text;
   
    public Filter(){
        this.attribute=new SimpleObjectProperty("");
        this.operator=new SimpleObjectProperty("");
        this.text=new SimpleStringProperty("");
    }
    
    public Filter(String operator,String attribute,String text){
        this.operator=new SimpleObjectProperty(operator);
        this.attribute=new SimpleObjectProperty(attribute);
        this.text=new SimpleStringProperty(text);
    }

    public String getOperator() {
        return operator.get();
    }

    public void setOperator(String operator) {
        this.operator.set(operator);
    }

    public String getAttribute() {
        return attribute.get();
    }

    public void setAttribute(String attribute) {
        this.attribute.set(attribute);
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }
    
}
