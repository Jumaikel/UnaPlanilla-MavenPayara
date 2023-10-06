/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module unaplanilla {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires jakarta.xml.bind;
    requires jakarta.ws.rs;
    requires jakarta.json;
    requires java.base;
    requires java.sql;
    
    opens cr.ac.una.unaplanilla to javafx.fxml, javafx.graphics;
    opens cr.ac.una.unaplanilla.controller to javafx.fxml,javafx.controls,com.jfoenix;
    
    exports cr.ac.una.unaplanilla.model;
}
