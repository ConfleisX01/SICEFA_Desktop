package org.utl.dsm.dreamsoft_sicefa.Controller;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    @FXML
    private Button btnCentral;

    @FXML
    private Button btnSucursal;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadButtons();
    }

    public void loadButtons() {
        btnCentral.setOnAction(event -> {
            try {
                getUser();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnSucursal.setOnAction(event -> {

        });
    }
    public void getUser() throws UnirestException, IOException {
        String user = txtUsuario.getText();
        String password = txtContrasenia.getText();

        if(validateUser()) {
            HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DSM406_EjemploPost/api/ejemplo/login").queryString("user", user).queryString("password", password).asJson();
            String result = apiResponse.getBody().getObject().getString("response");
            if(result.equals("null")) {
                System.out.println("Credenciales Incorrectas");
            } else {
                System.out.println("Credenciales correctas");
            }
        } else {
            System.out.println("Campos vacios");
        }
    }

    public boolean validateUser() {
        if(txtUsuario.getText().isEmpty()) {
            return false;
        }

        if (txtContrasenia.getText().isEmpty()) {
            return false;
        }

        return true;
    }
}
