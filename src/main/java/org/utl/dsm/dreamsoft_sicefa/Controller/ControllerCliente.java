package org.utl.dsm.dreamsoft_sicefa.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.utl.dsm.dreamsoft_sicefa.Model.Cliente;
import org.utl.dsm.dreamsoft_sicefa.Model.Persona;

public class ControllerCliente implements Initializable {
    @FXML
    private Button btnAgregar;

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
    private Button btnBuscar;
    @FXML
    private ComboBox<String> comEstado;
    @FXML
    private ComboBox<String> comGenero;

    @FXML
    private DatePicker dataFecha;

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> tcolApellidos;

    @FXML
    private TableColumn<Cliente, Integer> tcolEstatus;

    @FXML
    private TableColumn<Cliente, String> tcolRfc;

    @FXML
    private TableColumn<Cliente, String> tcolTelefono;

    @FXML
    private TableColumn<Cliente, String> tcolFechaRegistro;


    @FXML
    private TableColumn<Cliente, String> tcolID;

    @FXML
    private TableColumn<Cliente, String> tcolNombre;

    @FXML
    private TextField txtApellidoMaterno;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtCURP;

    @FXML
    private TextField txtCiudad;
    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCodigoPostal;

    @FXML
    private TextField txtDomicilio;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFoto;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtRFC;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtEstatus;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnRegresar;

    @FXML
    private ObservableList<Cliente> clientes;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Usamos ComboBox para agregar el Genero
        ObservableList<String> opciones = FXCollections.observableArrayList("M",
                "F",
                "O");
        comGenero.setItems(opciones);

        //Usamos ComboBox para agregar los estados
        ObservableList<String> opcionesEs = FXCollections.observableArrayList("Aguascalientes",
                "Baja California",
                "Baja California Sur",
                "Campeche",
                "Chiapas",
                "Chihuahua",
                "Ciudad de México",
                "Coahuila",
                "Colima",
                "Durango",
                "Estado de México",
                "Guanajuato",
                "Guerrero",
                "Hidalgo",
                "Jalisco",
                "Michoacán",
                "Morelos",
                "Nayarit",
                "Nuevo León",
                "Oaxaca",
                "Puebla",
                "Querétaro",
                "Quintana Roo",
                "San Luis Potosí",
                "Sinaloa",
                "Sonora",
                "Tabasco",
                "Tamaulipas",
                "Tlaxcala",
                "Veracruz",
                "Yucatán",
                "Zacatecas");
        comEstado.setItems(opcionesEs);
        Gson gson = new Gson();

        //Boton que sirve para agregar un registro de clientes
        btnAgregar.setOnAction(event -> {
            try {
                save();
                mostrar();
            } catch (UnirestException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Boton que sirve para actualizar un cliente
        btnModificar.setOnAction(event -> {
            try {
                update();
                mostrar();
            } catch (UnirestException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Boton que sirve para eliminar un cliente
        btnEliminar.setOnAction(event -> {
            try {
                delete();
                mostrar();
            } catch (UnirestException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnBuscar.setOnAction(event -> {
            search();
        });

        btnEmpleados.setOnAction(event -> {
            mostrarEmpleados();
        });

        btnRegresar.setOnAction(event -> {
            regresarLogin();
        });

        //Inicializar las columnas de la tabla clientes.
        tcolID.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdCliente()).asString());
        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getNombre()));
        tcolApellidos.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getApellidoPaterno() + " " +
                param.getValue().getPersona().getApellidoMaterno()));

        tcolRfc.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()));
        tcolTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getTelefono()));
        tcolFechaRegistro.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFechaRegistro()));
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEstatus()));


        //Inicializamos la lista de los Clientes.
        clientes = FXCollections.observableArrayList();
        tblClientes.setItems(clientes);
        //Seleccionar inputs
        tblClientes.setOnMouseClicked(event -> {
            Cliente selectCliente = tblClientes.getSelectionModel().getSelectedItem();

            if (selectCliente != null) {
                selectInputs(selectCliente);
            }
        });

        //Sirve para mostrar los clientes en la tabla
        try {
            mostrar();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Función que sirve para agregar un nuevo cliente.
    public void save() throws UnirestException, IOException {
        Persona persona = new Persona();
        persona.setNombre(txtNombre.getText());
        persona.setApellidoPaterno(txtApellidoPaterno.getText());
        persona.setApellidoMaterno(txtApellidoMaterno.getText());
        persona.setGenero(comGenero.getValue());
        persona.setFechaNacimiento(String.valueOf(dataFecha.getValue()));
        persona.setRfc(txtRFC.getText());
        persona.setCurp(txtCURP.getText());
        persona.setDomicilio(txtDomicilio.getText());
        persona.setCodigoPostal(txtCodigoPostal.getText());
        persona.setCiudad(txtCiudad.getText());
        persona.setEstado(comEstado.getValue());
        persona.setTelefono(txtTelefono.getText());
        persona.setFoto(txtFoto.getText());

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setEmail(txtEmail.getText());

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/cliente/insertarCliente";
        Gson gson = new Gson();
        String params = gson.toJson(cliente);

        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("datosCliente", params)
                    .asJson();
            String respuesta = apiResponse.getBody().getObject().getString("result");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Registro correcto");
            alert.show();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Error");
            alert.show();
        }
        // Después de guardar el cliente, lo agregamos a la lista de clientes
        clientes.add(cliente);
        // Y actualizamos el TableView
        tblClientes.refresh();
        clear();
    }

    //Función que sirve para actulizar/ modificar un cliente
    public void update() throws UnirestException, IOException {
        Persona persona = new Persona();
        persona.setNombre(txtNombre.getText());
        persona.setApellidoPaterno(txtApellidoPaterno.getText());
        persona.setApellidoMaterno(txtApellidoMaterno.getText());
        persona.setGenero(comGenero.getValue());
        persona.setFechaNacimiento(String.valueOf(dataFecha.getValue()));
        persona.setRfc(txtRFC.getText());
        persona.setCurp(txtCURP.getText());
        persona.setDomicilio(txtDomicilio.getText());
        persona.setCodigoPostal(txtCodigoPostal.getText());
        persona.setCiudad(txtCiudad.getText());
        persona.setEstado(comEstado.getValue());
        persona.setTelefono(txtTelefono.getText());
        persona.setFoto(txtFoto.getText());

        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setIdCliente(Integer.parseInt(txtId.getText()));
        cliente.setEmail(txtEmail.getText());
        cliente.setEstatus(Integer.parseInt(txtEstatus.getText()));

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/cliente/updateCliente";
        Gson gson = new Gson();
        String params = gson.toJson(cliente);

        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("datosCliente", params)
                    .asJson();
            String respuesta = apiResponse.getBody().getObject().getString("result");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Cliente actualizado");
            alert.show();
            mostrar();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Error");
            alert.show();
        }
        clear();
    }

    //Función que sirve para eliminar un cliente o ponerlo en estatus de 0
    public void delete() throws UnirestException, IOException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(Integer.parseInt(txtId.getText()));

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/cliente/deleteCliente";
        Gson gson = new Gson();
        String params = gson.toJson(cliente);

        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("datosCliente", params)
                    .asJson();
            String respuesta = apiResponse.getBody().getObject().getString("result");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Cliente eliminado");
            alert.show();
            mostrar();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Error");
            alert.show();
        }
        clear();
    }

    public void search(){
        String nombreBuscar = txtBuscar.getText().trim().toLowerCase();

        if (nombreBuscar.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ingrese un nombre para buscar.");
            alert.show();
        } else {
            try {
                HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/cliente/getAll")
                        .asJson();

                JsonNode responseBody = apiResponse.getBody();
                if (responseBody != null && responseBody.isArray()) {
                    Gson gson = new Gson();
                    Cliente[] allClientes = gson.fromJson(responseBody.toString(), Cliente[].class);

                    List<Cliente> clientesEncontrados = Arrays.stream(allClientes)
                            .filter(cliente -> cliente.getPersona().getNombre().toLowerCase().contains(nombreBuscar))
                            .collect(Collectors.toList());

                    tblClientes.getItems().clear();
                    tblClientes.getItems().addAll(clientesEncontrados);
                    tblClientes.refresh();
                    int numClientesEncontrados = clientesEncontrados.size();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se encontraron " + numClientesEncontrados + " clientes.");
                    alert.show();
                    System.out.println(nombreBuscar);
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
    }

    //Función que sirve para mostrar los datos que tenemos en la tabla del frontend
    public void mostrar() throws UnirestException, IOException {
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/cliente/getAll")
                    .asJson();

            JsonNode responseBody = apiResponse.getBody();
            if (responseBody != null && responseBody.isArray()) {
                Gson gson = new Gson();
                Cliente[] clientes = gson.fromJson(responseBody.toString(), Cliente[].class);

                // Limpiar la tabla antes de agregar nuevos datos
                tblClientes.getItems().clear();

                // Agregar los datos de los clientes a la tabla
                tblClientes.getItems().addAll(Arrays.asList(clientes));
            }
        } catch (UnirestException e) {
            e.printStackTrace(); // Manejo apropiado de errores
        }
    }


    //Función que sirve para limpiar campos, utilizada tambien el sava, update y delete
    public void clear() {
        txtId.clear();
        txtNombre.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        comGenero.getSelectionModel().clearSelection();
        dataFecha.setValue(null);
        txtRFC.clear();
        txtCURP.clear();
        txtCodigoPostal.clear();
        txtDomicilio.clear();
        txtCiudad.clear();
        comEstado.getSelectionModel().clearSelection();
        txtTelefono.clear();
        txtEmail.clear();
        txtEstatus.clear();
        txtFoto.clear();
    }

    //Función que sirve para seleccionar un campo de la tabla.
    private void selectInputs(Cliente cliente) {
        txtId.setText(String.valueOf(cliente.getIdCliente()));
        txtNombre.setText(cliente.getPersona().getNombre());
        txtApellidoPaterno.setText(cliente.getPersona().getApellidoPaterno());
        txtApellidoMaterno.setText(cliente.getPersona().getApellidoMaterno());
        comGenero.setValue(cliente.getPersona().getGenero());

        String fechaNacimiento = cliente.getPersona().getFechaNacimiento();
        if (fechaNacimiento != null && !fechaNacimiento.isEmpty()) {
            dataFecha.setValue(LocalDate.parse(fechaNacimiento));
        }

        txtRFC.setText(cliente.getPersona().getRfc());
        txtCURP.setText(cliente.getPersona().getCurp());
        txtDomicilio.setText(cliente.getPersona().getDomicilio());
        txtCodigoPostal.setText(cliente.getPersona().getCodigoPostal());
        comEstado.setValue(cliente.getPersona().getEstado());
        txtCiudad.setText(cliente.getPersona().getCiudad());
        txtTelefono.setText(cliente.getPersona().getTelefono());
        txtFoto.setText(cliente.getPersona().getFoto());
        txtEmail.setText(cliente.getEmail());
        txtEstatus.setText(String.valueOf(cliente.getEstatus()));
    }

    public void mostrarEmpleados(){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_empleado.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnEmpleados.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void regresarLogin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

