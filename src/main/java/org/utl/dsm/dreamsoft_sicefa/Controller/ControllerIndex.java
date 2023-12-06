package org.utl.dsm.dreamsoft_sicefa.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControllerIndex implements Initializable {

    @FXML
    private Button btnExit;

    @FXML
    private Button btnComenzar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButtons();
    }

    public void loadButtons() {
        btnExit.setOnAction(event -> {
            System.exit(0);
        });

        btnComenzar.setOnAction(event -> {

        });
    }
}
