package org.utl.dsm.dreamsoft_sicefa.Controller;

import javax.swing.JOptionPane;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.utl.dsm.dreamsoft_sicefa.Model.Usuario;

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
                loginCentral();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnSucursal.setOnAction(event -> {
            try {
                loginSucursal();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loginCentral() throws UnirestException, IOException {
        String user = txtUsuario.getText();
        String password = txtContrasenia.getText();

        if (validateUser()) {
            HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/login/login")
                    .queryString("user", user).queryString("password", password).asJson();
            String result = apiResponse.getBody().getObject().getString("rol");
            if (result.equals("ADMC")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_sucursal.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnCentral.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales son inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Datos vacios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loginSucursal() throws UnirestException, IOException {
        String user = txtUsuario.getText();
        String password = txtContrasenia.getText();

        if (validateUser()) {
            HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/login/login")
                    .queryString("user", user).queryString("password", password).asJson();
            String result = apiResponse.getBody().getObject().getString("rol");
            if (result.equals("ADMS") || result.equals("ADMC")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_empleado.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) btnCentral.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales son inválidas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Datos vacios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validateUser() {
        if (txtUsuario.getText().isEmpty()) {
            return false;
        }

        if (txtContrasenia.getText().isEmpty()) {
            return false;
        }

        return true;
    }
}
