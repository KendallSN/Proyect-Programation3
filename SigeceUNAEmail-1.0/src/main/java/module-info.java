module cr.ac.una.sigeceunaemail {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires jakarta.ws.rs;
    requires jakarta.json;
    requires jakarta.xml.bind;
    requires java.sql;
    requires java.xml;
    requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires javafx.web;
    
    opens cr.ac.una.sigeceunaemail to javafx.fxml;
    exports cr.ac.una.sigeceunaemail;
    exports cr.ac.una.sigeceunaemail.controller;
    opens cr.ac.una.sigeceunaemail.controller to javafx.fxml;
    opens cr.ac.una.sigeceunaemail.model;
    
    requires javafx.graphics;
    requires java.base;
}
