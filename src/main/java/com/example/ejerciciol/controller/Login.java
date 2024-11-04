package com.example.ejerciciol.controller;

import java.io.IOException;


import com.example.ejerciciol.dao.Dao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @FXML
    void login(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        String user = this.txtUser.getText();
        String pass = this.txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            dao.mostrarError("Hay que introducir todos los datos!");
        } else {
            // Utiliza el método verificarCredenciales para comprobar las credenciales
            boolean access = dao.verificarCredenciales(user, pass);

            if (!access) {
                dao.mostrarError("Login incorrecto intenta de nuevo");
                vaciarCampos();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GestionarAeropuertos.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setResizable(true);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Aviones-Aeropuertos");
                stage.setScene(scene);
                stage.showAndWait();
                // Cerrar la ventana de login
                Stage currentStage = (Stage) btnLogin.getScene().getWindow();
                currentStage.close();
            }
        }
    }


    void vaciarCampos() {
        txtUser.clear();
        txtPassword.clear();
    }
}
