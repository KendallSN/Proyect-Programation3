module cr.ac.una.sigeceuna {
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
    requires itextpdf;
    requires commons.logging;
    requires org.apache.commons.collections4;
    requires net.sf.jasperreports.pdf;
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.fonts;
    
    opens cr.ac.una.sigeceuna to javafx.fxml;
    exports cr.ac.una.sigeceuna;
    exports cr.ac.una.sigeceuna.controller;
    exports cr.ac.una.sigeceuna.service;
    exports cr.ac.una.sigeceuna.model;
    opens cr.ac.una.sigeceuna.controller to javafx.fxml;
    opens cr.ac.una.sigeceuna.model;
    requires javafx.graphics;
    requires java.base;
}
