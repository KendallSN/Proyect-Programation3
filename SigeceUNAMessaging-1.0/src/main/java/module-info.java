module cr.ac.una.sigeceunamessaging {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires jakarta.ws.rs;
    requires jakarta.json;
    requires jakarta.xml.bind;
    requires java.sql;
    requires java.xml;
    requires java.desktop;
    requires javafx.media;
    
    opens cr.ac.una.sigeceunamessaging to javafx.fxml;
    exports cr.ac.una.sigeceunamessaging;
    exports cr.ac.una.sigeceunamessaging.controller;
    exports cr.ac.una.sigeceunamessaging.service;
    opens cr.ac.una.sigeceunamessaging.controller to javafx.fxml;
    opens cr.ac.una.sigeceunamessaging.model;
    requires javafx.graphics;
    requires java.base;

    
}
