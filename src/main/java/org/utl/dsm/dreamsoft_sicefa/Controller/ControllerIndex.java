package org.utl.dsm.dreamsoft_sicefa.Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerIndex implements Initializable {
    @FXML
    private Button btnComenzar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButtons();
    }

    public void loadButtons() {
        btnComenzar.setOnAction(event -> {
            try {
                setLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void setLogin() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(this.getClass().getResource("view_login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();

        Stage ventanaIndex = (Stage) btnComenzar.getScene().getWindow();
        ventanaIndex.close();
    }
}
