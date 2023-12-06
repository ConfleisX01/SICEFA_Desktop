package org.utl.dsm.dreamsoft_sicefa.Controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.utl.dsm.dreamsoft_sicefa.Model.Sucursal;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerSucursal implements Initializable {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnConsultas;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnVentas;

    @FXML
    private ComboBox<String> comEstado;

    @FXML
    private TableView<Sucursal> tblSucursal;

    @FXML
    private TableColumn<Sucursal, String> tcolDomicilio;

    @FXML
    private TableColumn<Sucursal, Integer> tcolEstatus;

    @FXML
    private TableColumn<Sucursal, Integer> tcolID;

    @FXML
    private TableColumn<Sucursal, String> tcolNombreSucursal;

    @FXML
    private TableColumn<Sucursal, String> tcolTelefono;

    @FXML
    private TableColumn<Sucursal, String> tcolTitular;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCodigoPostal;

    @FXML
    private TextField txtColonia;

    @FXML
    private TextField txtDomicilio;

    @FXML
    private TextField txtEstatus;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLatitud;

    @FXML
    private TextField txtLongitud;

    @FXML
    private TextField txtNombreSucursal;

    @FXML
    private TextField txtRFC;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTitular;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // hacemos disabled los inputs id y estatus
        txtEstatus.setDisable(true);
        txtId.setDisable(true);


        //Usamos ComboBox para agregar los estados
        ObservableList<String> opcionesEs = FXCollections.observableArrayList("Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Ciudad de México", "Coahuila", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas");
        comEstado.setItems(opcionesEs);
        Gson gson = new Gson();

        // boton para agregar

        btnAgregar.setOnAction(event -> {
            try {
                save();
                mostrarDatos();
                limpiarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnModificar.setOnAction(event -> {
            try {
                modify();
                mostrarDatos();
                limpiarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnEliminar.setOnAction(event -> {
            try {
                eliminate();
                mostrarDatos();
                limpiarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnLimpiar.setOnAction(event -> {
            try {
                limpiarDatos();
            }  catch (Exception e) {
                e.printStackTrace();
            }
        });

      /*  btnBuscar.setOnAction(event -> {
            try {
                search();
                mostrarDatos();
                //mostrarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
        tblSucursal.setOnMouseClicked(event -> {
            Sucursal selectSucursal = tblSucursal.getSelectionModel().getSelectedItem();
            // comprobamos que no se seleccone nada nuli¿o
            if (selectSucursal != null) {
                selectInputs(selectSucursal);
            }
        });
        inicializarTabla();

    }

    public void save() throws UnirestException, IOException {

        Sucursal s = new Sucursal();
        s.setNombreSucursal(txtNombreSucursal.getText());
        s.setTitular(txtTitular.getText());
        s.setDomicilio(txtDomicilio.getText());
        s.setColonia(txtColonia.getText());
        s.setCodigoPostal(txtCodigoPostal.getText());
        s.setCiudad(txtCiudad.getText());
        s.setEstado(comEstado.getValue());
        s.setTelefono(txtTelefono.getText());
        s.setLatitud(txtLatitud.getText());
        s.setLongitud(txtLongitud.getText());
        s.setRfc(txtRFC.getText());

        // url del servicio
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/sucursal/insertarSucursal";
        Gson gson = new Gson();
        String params = gson.toJson(s);
        System.out.println(s);
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url).field("sucursal", params).asJson();
            String respuesta = apResponse.getBody().getObject().getString("response");
            System.out.println(respuesta);
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    public void modify() throws UnirestException, IOException {
        Sucursal s = new Sucursal();
        s.setNombreSucursal(txtNombreSucursal.getText());
        s.setTitular(txtTitular.getText());
        s.setRfc(txtRFC.getText());
        s.setDomicilio(txtDomicilio.getText());
        s.setCodigoPostal(txtCodigoPostal.getText());
        s.setCiudad(txtCiudad.getText());
        s.setEstado(comEstado.getValue());
        s.setTelefono(txtTelefono.getText());
        s.setLatitud(txtLatitud.getText());
        s.setLongitud(txtLongitud.getText());
        s.setEstatus(Integer.parseInt(txtEstatus.getText()));
        s.setColonia(txtColonia.getText());
        s.setIdSucursal(Integer.parseInt(txtId.getText()));
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/sucursal/modificarSucursal";
        Gson gson = new Gson();
        String params = gson.toJson(s);
        System.out.println(s.getNombreSucursal());
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url).field("sucursal", params).asJson();
            String respuesta = apResponse.getBody().getObject().getString("response");
            System.out.println(respuesta);
            System.out.println(s);

        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminate() throws UnirestException, IOException {
        Sucursal s = new Sucursal();
        s.setIdSucursal(Integer.parseInt(txtId.getText()));

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/sucursal/eliminarSucursal";
        Gson gson = new Gson();
        String params = gson.toJson(s);
        System.out.println(s);
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url).field("sucursal", params).asJson();
            String respuesta = apResponse.getBody().getObject().getString("response");
            System.out.println(respuesta);
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    /*public void search() throws UnirestException, IOException {
       String dato = txtBuscar.getText().trim().toLowerCase();

        try {
            String url = "http://localhost:8080/DreamSoft_SICEFA/api/empleado/getAll";
            HttpResponse<JsonNode> response = Unirest.get(url).asJson();
            if (response.getStatus() == 200) {
                JsonNode responseBody = response.getBody();
                if(responseBody != null){
                    Gson gson = new Gson();
                    Empleado[] empleados = gson.fromJson(responseBody.toString(), Empleado[].class);

                }
            }
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }*/

    public void inicializarTabla() {
        tcolID.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdSucursal()));
        tcolNombreSucursal.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNombreSucursal()).asString());
        tcolTitular.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTitular()).asString());
        tcolDomicilio.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDomicilio()).asString());
        tcolTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTelefono()).asString());
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEstatus()));

        mostrarDatos();
    }

    public void mostrarDatos() {
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/sucursal/getAll";

        try {
            HttpResponse<JsonNode> response = Unirest.get(url).asJson();
            if (response.getStatus() == 200) {
                JsonNode body = response.getBody();
                Gson gson = new Gson();
                Sucursal[] sucursales = gson.fromJson(body.toString(), Sucursal[].class);

                // Imprimir los datos de cada empleado en la consola
                for (Sucursal sucursal : sucursales) {
                    System.out.println("ID: " + sucursal.getIdSucursal());
                    System.out.println("Nombre: " + sucursal.getNombreSucursal());
                    System.out.println("Teléfono: " + sucursal.getTelefono());
                    System.out.println("Estatus: " + sucursal.getEstatus());
                    System.out.println("--------------------------------------------");
                }

                // Actualizar la tabla con la lista de empleados
                ObservableList<Sucursal> listaSucursales = FXCollections.observableArrayList(Arrays.asList(sucursales));
                tblSucursal.setItems(listaSucursales);
            } else {
                System.out.println("Error al obtener los datos de los empleados. Código de estado: " + response.getStatus());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private void selectInputs(Sucursal sucursal) {
        // datos de persona
        txtNombreSucursal.setText(sucursal.getNombreSucursal());
        txtTitular.setText(sucursal.getTitular());
        txtRFC.setText(sucursal.getRfc());
        txtDomicilio.setText(sucursal.getDomicilio());
        txtColonia.setText(sucursal.getColonia());
        txtCodigoPostal.setText(String.valueOf(sucursal.getCodigoPostal()));
        txtCiudad.setText(sucursal.getCiudad());
        comEstado.setValue(sucursal.getEstado());
        txtTelefono.setText(sucursal.getTelefono());
        txtLatitud.setText(sucursal.getLatitud());
        txtLongitud.setText(sucursal.getLongitud());
        txtId.setText(String.valueOf(sucursal.getIdSucursal()));
        txtEstatus.setText(String.valueOf(sucursal.getEstatus()));
    }
    public  void limpiarDatos(){
        txtNombreSucursal.setText("");
        txtTitular.setText("");
        txtRFC.setText("");
        txtDomicilio.setText("");
        txtColonia.setText("");
        txtCodigoPostal.setText("");
        txtCiudad.setText("");
        comEstado.setValue(null); // Si el ComboBox es nullable
        txtTelefono.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        txtId.setText("");
        txtEstatus.setText("");
    }
}
