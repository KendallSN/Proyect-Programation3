module cr.ac.una.sigeceunasecurity {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires jakarta.jws;
    requires jakarta.ws.rs;
    requires jakarta.json;
    requires jakarta.xml.bind;
    requires jakarta.xml.ws;
    requires java.sql;
    requires java.xml;
    requires java.desktop;

    opens cr.ac.una.sigeceunasecurity to javafx.fxml;
    exports cr.ac.una.sigeceunasecurity;
    exports cr.ac.una.sigeceunasecurity.controller;
    opens cr.ac.una.sigeceunasecurity.controller to javafx.fxml;
    opens cr.ac.una.sigeceunasecurityws.controller;
    opens cr.ac.una.sigeceunasecurity.model;
    requires javafx.graphics;
    requires java.base;
    requires webcam.capture;
    requires materialfx.all;
}
