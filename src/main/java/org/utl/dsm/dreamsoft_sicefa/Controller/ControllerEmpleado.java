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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.utl.dsm.dreamsoft_sicefa.Model.Empleado;
import org.utl.dsm.dreamsoft_sicefa.Model.Persona;
import org.utl.dsm.dreamsoft_sicefa.Model.Usuario;
import org.utl.dsm.dreamsoft_sicefa.Model.Sucursal;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerEmpleado implements Initializable {

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
    private ComboBox<String> comGenero;

    @FXML
    private DatePicker dataFecha;

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TableColumn<Empleado, String> tcolApellidoPaterno;

    @FXML
    private TableColumn<Empleado, Integer> tcolEstatus;

    @FXML
    private TableColumn<Empleado, String> tcolFechaRegistro;

    @FXML
    private TableColumn<Empleado, Integer> tcolID;

    @FXML
    private TableColumn<Empleado, String> tcolNombre;

    @FXML
    private TableColumn<Empleado, String> tcolRfc;

    @FXML
    private TableColumn<Empleado, String> tcolTelefono;

    @FXML
    private TextField txtApellidoMaterno;

    @FXML
    private TextField txtApellidoPaterno;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCURP;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtCodigoPostal;

    @FXML
    private TextField txtDomicilio;

    @FXML
    private TextField txtEstatus;

    @FXML
    private TextField txtFoto;

    @FXML
    private TextField txtIDSucursal;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPuesto;

    @FXML
    private TextField txtRFC;

    @FXML
    private TextField txtRol;

    @FXML
    private TextField txtSalarioBruto;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnRegresar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtEstatus.setDisable(true);
        txtId.setDisable(true);
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

        // boton para agregar

        btnAgregar.setOnAction(event -> {
            try {
                save();
                mostrarDatos();
                limpiarDatos();
                //mostrarDatos();
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
                //mostrarDatos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

       btnRegresar.setOnAction(event -> {
            regresarLogin();
        });

       btnClientes.setOnAction(event -> {
           mostrarClientes();
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
        tblEmpleados.setOnMouseClicked(event -> {
            Empleado selectEmpleado = tblEmpleados.getSelectionModel().getSelectedItem();
            // comprobamos que no se seleccone nada nuli¿o
            if (selectEmpleado != null) {
                selectInputs(selectEmpleado);
            }
        });
        inicializarTabla();

    }

    public void save() throws UnirestException, IOException {
        // se insertan los datos de Persona
        Persona p = new Persona();
        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApellidoPaterno.getText());
        p.setApellidoMaterno(txtApellidoMaterno.getText());
        p.setGenero(comGenero.getValue());
        p.setFechaNacimiento(String.valueOf(dataFecha.getValue()));
        p.setRfc(txtRFC.getText());
        p.setCurp(txtCURP.getText());
        p.setDomicilio(txtDomicilio.getText());
        p.setCodigoPostal(txtCodigoPostal.getText());
        p.setCiudad(txtCiudad.getText());
        p.setEstado(comEstado.getValue());
        p.setTelefono(txtTelefono.getText());
        p.setFoto(txtFoto.getText());
        // se insertan datos de sucursal
        Sucursal s = new Sucursal();
        s.setIdSucursal(Integer.parseInt(txtIDSucursal.getText()));
        // se insertan datos de usuario
        Usuario u = new Usuario();
        u.setRol(txtRol.getText());
        // se insertan datos de empleado
        Empleado e = new Empleado();
        e.setPuesto(txtPuesto.getText());
        e.setSalarioBruto(Float.parseFloat(txtSalarioBruto.getText()));
        // insertamos todos los datos em empleado
        e.setPersona(p);
        e.setUsuario(u);
        e.setSucursal(s);
        // url del servicio
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/empleado/insertarEmpleado";
        Gson gson = new Gson();
        String params = gson.toJson(e);
        System.out.println(e);
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url)
                    .field("empleado", params)
                    .asJson();
            String respuesta = apResponse.getBody().getObject().getString("response");
            System.out.println(respuesta);
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    public void modify() throws UnirestException, IOException {
        // se insertan los datos de Persona
        Persona p = new Persona();
        p.setNombre(txtNombre.getText());
        p.setApellidoPaterno(txtApellidoPaterno.getText());
        p.setApellidoMaterno(txtApellidoMaterno.getText());
        p.setGenero(comGenero.getValue());
        p.setFechaNacimiento(String.valueOf(dataFecha.getValue()));
        p.setRfc(txtRFC.getText());
        p.setCurp(txtCURP.getText());
        p.setDomicilio(txtDomicilio.getText());
        p.setCodigoPostal(txtCodigoPostal.getText());
        p.setCiudad(txtCiudad.getText());
        p.setEstado(comEstado.getValue());
        p.setTelefono(txtTelefono.getText());
        p.setFoto(txtFoto.getText());
        // se insertan datos de sucursal
        Sucursal s = new Sucursal();
        s.setIdSucursal(Integer.parseInt(txtIDSucursal.getText()));
        // se insertan datos de usuario
        Usuario u = new Usuario();
        u.setRol(txtRol.getText());
        // se insertan datos de empleado
        Empleado e = new Empleado();
        e.setIdEmpleado(Integer.parseInt(txtId.getText()));
        e.setPuesto(txtPuesto.getText());
        e.setSalarioBruto(Float.parseFloat(txtSalarioBruto.getText()));
        e.setActivo(Integer.parseInt(txtEstatus.getText()));
        // insertamos todos los datos em empleado
        e.setPersona(p);
        e.setUsuario(u);
        e.setSucursal(s);
        // url del servicio
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/empleado/modificarEmpleado";
        Gson gson = new Gson();
        String params = gson.toJson(e);
        System.out.println(e);
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url)
                    .field("empleado", params)
                    .asJson();
            String respuesta = apResponse.getBody().getObject().getString("response");
            System.out.println(respuesta);
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminate() throws UnirestException, IOException {
        // se insertan los datos de Persona
        Empleado e = new Empleado();
        e.setIdEmpleado(Integer.parseInt(txtId.getText()));
        // url del servicio
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/empleado/eliminarEmpleado";
        Gson gson = new Gson();
        String params = gson.toJson(e);
        System.out.println(e);
        try {
            HttpResponse<JsonNode> apResponse = Unirest.post(url)
                    .field("empleado", params)
                    .asJson();
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
        tcolID.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdEmpleado()));
        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getNombre()).asString());
        tcolApellidoPaterno.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getApellidoPaterno()).asString());
        tcolRfc.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()).asString());
        tcolTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getTelefono()).asString());
        tcolFechaRegistro.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getFechaNacimiento()).asString());
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getActivo()));

        mostrarDatos();
    }

    public void mostrarDatos() {
        String url = "http://localhost:8080/DreamSoft_SICEFA/api/empleado/getAll";

        try {
            HttpResponse<JsonNode> response = Unirest.get(url).asJson();
            if (response.getStatus() == 200) {
                JsonNode body = response.getBody();
                Gson gson = new Gson();
                Empleado[] empleados = gson.fromJson(body.toString(), Empleado[].class);

                // Imprimir los datos de cada empleado en la consola
                for (Empleado empleado : empleados) {
                    System.out.println("ID: " + empleado.getIdEmpleado());
                    System.out.println("Nombre: " + empleado.getPersona().getNombre());
                    System.out.println("Apellido Paterno: " + empleado.getPersona().getApellidoPaterno());
                    System.out.println("RFC: " + empleado.getPersona().getRfc());
                    System.out.println("Teléfono: " + empleado.getPersona().getTelefono());
                    System.out.println("Fecha de Registro: " + empleado.getPersona().getFechaNacimiento());
                    System.out.println("Estatus: " + empleado.getActivo());
                    System.out.println("--------------------------------------------");
                }

                // Actualizar la tabla con la lista de empleados
                ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(Arrays.asList(empleados));
                tblEmpleados.setItems(listaEmpleados);
            } else {
                System.out.println("Error al obtener los datos de los empleados. Código de estado: " + response.getStatus());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private void selectInputs(Empleado empleado) {
        // datos de persona
        txtNombre.setText(empleado.getPersona().getNombre());
        txtApellidoPaterno.setText(empleado.getPersona().getApellidoPaterno());
        txtApellidoMaterno.setText(empleado.getPersona().getApellidoMaterno());
        comGenero.setValue(empleado.getPersona().getGenero());
        txtRFC.setText(empleado.getPersona().getRfc());
        txtCURP.setText(empleado.getPersona().getCurp());
        txtDomicilio.setText(empleado.getPersona().getDomicilio());
        txtCodigoPostal.setText(String.valueOf(empleado.getPersona().getCodigoPostal()));
        txtCiudad.setText(empleado.getPersona().getCiudad());
        comEstado.setValue(empleado.getPersona().getEstado());
        txtTelefono.setText(empleado.getPersona().getTelefono());
        txtFoto.setText(empleado.getPersona().getFoto());
        //datos de sucursal
        txtIDSucursal.setText(String.valueOf(empleado.getSucursal().getIdSucursal()));
        //datos usuario
        txtRol.setText(empleado.getUsuario().getRol());
        // datos empleado
        txtId.setText(String.valueOf(empleado.getIdEmpleado()));
        txtPuesto.setText(empleado.getPuesto());
        txtSalarioBruto.setText(String.valueOf(empleado.getSalarioBruto()));
        txtEstatus.setText(String.valueOf(empleado.getActivo()));


        String fechaString = empleado.getPersona().getFechaNacimiento();
        if (fechaString != null && !fechaString.isEmpty()) {
            dataFecha.setValue(LocalDate.parse(fechaString));
        }
    }


    public void limpiarDatos() {
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        comGenero.setValue(null); // Si el ComboBox es nullable
        txtRFC.setText("");
        txtCURP.setText("");
        txtDomicilio.setText("");
        txtCodigoPostal.setText("");
        txtCiudad.setText("");
        comEstado.setValue(null); // Si el ComboBox es nullable
        txtTelefono.setText("");
        txtFoto.setText("");
        txtIDSucursal.setText("");
        txtRol.setText("");
        txtId.setText("");
        txtPuesto.setText("");
        txtSalarioBruto.setText("");
        txtEstatus.setText("");
        dataFecha.setValue(null); // Si el DatePicker es nullable
    }

    public  void mostrarClientes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_clientes.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnRegresar.getScene().getWindow();
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
