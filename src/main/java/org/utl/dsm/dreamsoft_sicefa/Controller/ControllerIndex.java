package org.utl.dsm.dreamsoft_sicefa.Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerIndex implements Initializable {

    @FXML
    private Button btnComenzar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButtons();
    }

    public void loadButtons() {
        btnComenzar.setOnAction(event -> {
            showLogin();
        });
    }

    private void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnComenzar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar cualquier excepción de carga del FXML
        }
    }
}
