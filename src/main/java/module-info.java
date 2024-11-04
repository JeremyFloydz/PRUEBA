module com.example.ejerciciol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.security.auth;
    requires org.checkerframework.checker.qual;

    opens com.example.ejerciciol to javafx.fxml;
    exports com.example.ejerciciol;

    exports com.example.ejerciciol.controller;
    opens com.example.ejerciciol.controller to javafx.fxml;

    exports com.example.ejerciciol.model;
    exports com.example.ejerciciol.dao;
}
