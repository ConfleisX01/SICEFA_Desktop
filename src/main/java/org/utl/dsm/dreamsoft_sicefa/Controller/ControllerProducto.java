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
import org.utl.dsm.dreamsoft_sicefa.Model.Producto;

public class ControllerProducto implements Initializable {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnSucursales;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnVentas;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> tcolCodigoBarras;

    @FXML
    private TableColumn<Producto, Integer> tcolEstatus;

    @FXML
    private TableColumn<Producto, String> tcolFoto;

    @FXML
    private TableColumn<Producto, Integer> tcolID;

    @FXML
    private TableColumn<Producto, String> tcolNombreProducto;

    @FXML
    private TableColumn<Producto, Float> tcolPrecio;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCodigoBarras;

    @FXML
    private TextField txtConcentracion;

    @FXML
    private TextField txtContraindicaciones;

    @FXML
    private TextField txtEstatus;

    @FXML
    private TextField txtFormaFarmaceutica;

    @FXML
    private TextField txtFoto;

    @FXML
    private TextField txtIdProducto;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNombreGenerico;

    @FXML
    private TextField txtPrecioCompra;

    @FXML
    private TextField txtPrecioVenta;

    @FXML
    private TextField txtPresentacion;

    @FXML
    private TextField txtPrincipalIndicacion;

    @FXML
    private TextField txtRutaFoto;

    @FXML
    private TextField txtUnidadMedida;

    @FXML
    private TextField txtUnidadesEnvase;

    @FXML
    private Button btnRegresar;

    @FXML
    private ObservableList<Producto> productos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gson gson = new Gson();

        //Boton que sirve para agregar un registro de productos
        btnAgregar.setOnAction(event -> {
            try {
                save();
                mostrar();
            } catch (UnirestException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnModificar.setOnAction(event -> {
            try {
                update();
                mostrar();
            } catch (UnirestException | IOException e) {
                throw new RuntimeException(e);
            }
        });


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

        btnSucursales.setOnAction(event -> {
            mostrarSucursales();
        });

        btnRegresar.setOnAction(event -> {
            regresarLogin();
        });

        //Inicializamos las columnas
        tcolID.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getIdProducto()));
        tcolFoto.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPresentacion()));
        tcolNombreProducto.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNombre()));
        tcolPrecio.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPrecioCompra()));
        tcolCodigoBarras.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getUnidadesEnvase()).asString());
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getEstatus()));

        //Inicializamos la lista de los productos.
        productos = FXCollections.observableArrayList();
        tblProductos.setItems(productos);

        //Llamdado de la función de producto para seleccionar inputs
        // que tenemos.
        tblProductos.setOnMouseClicked(event -> {
            Producto selectProducto = tblProductos.getSelectionModel().getSelectedItem();

            if (selectProducto != null) {
                selectInputs(selectProducto);
            }
        });

        //Sirve para mostrar los productos en la tabla
        try {
            mostrar();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Función que sirve para agregar un nuevo producto.
    public void save() throws UnirestException, IOException {
        Producto p = new Producto();
        p.setNombre(txtNombre.getText());
        p.setNombreGenerico(txtNombreGenerico.getText());
        p.setFormaFarmaceutica(txtFormaFarmaceutica.getText());
        p.setUnidadMedida(txtUnidadMedida.getText());
        p.setPresentacion(txtPresentacion.getText());
        p.setPrincipalIndicacion(txtPrincipalIndicacion.getText());
        p.setContraindicaciones(txtContraindicaciones.getText());
        p.setConcentracion(txtConcentracion.getText());
        p.setUnidadesEnvase(Integer.parseInt(txtUnidadesEnvase.getText()));
        p.setPrecioCompra(Float.parseFloat(txtPrecioCompra.getText()));
        p.setPrecioVenta(Float.parseFloat(txtPrecioVenta.getText()));
        p.setFoto(txtFoto.getText());
        p.setRutaFoto(txtRutaFoto.getText());
        p.setCodigoBarras(txtCodigoBarras.getText());

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/producto/insertProducto";
        Gson gson = new Gson();
        String params = gson.toJson(p);

        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("producto", params)
                    .asJson();
            //String response = apiResponse.getBody().getObject().getString("response");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Registro correcto");
            alert.show();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error");
            alert.show();
        }
        // Después de guardar el producto, lo agregamos a la lista de productos
        productos.add(p);
        // Y actualizamos el TableView
        tblProductos.refresh();
        clear();
    }

    public void update() throws UnirestException, IOException {
        Producto p = new Producto();
        p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
        p.setNombre(txtNombre.getText());
        p.setNombreGenerico(txtNombreGenerico.getText());
        p.setFormaFarmaceutica(txtFormaFarmaceutica.getText());
        p.setUnidadMedida(txtUnidadMedida.getText());
        p.setPresentacion(txtPresentacion.getText());
        p.setPrincipalIndicacion(txtPrincipalIndicacion.getText());
        p.setContraindicaciones(txtContraindicaciones.getText());
        p.setConcentracion(txtConcentracion.getText());
        p.setUnidadesEnvase(Integer.parseInt(txtUnidadesEnvase.getText()));
        p.setPrecioCompra(Float.parseFloat(txtPrecioCompra.getText()));
        p.setPrecioVenta(Float.parseFloat(txtPrecioVenta.getText()));
        p.setFoto(txtFoto.getText());
        p.setRutaFoto(txtRutaFoto.getText());
        p.setCodigoBarras(txtCodigoBarras.getText());
        //p.setEstatus(Integer.parseInt(txtEstatus.getText()));

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/producto/updateProducto";
        Gson gson = new Gson();
        String params = gson.toJson(p);
        System.out.println(p);
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("producto", params)
                    .asJson();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Registro actualizado");
            alert.show();
            mostrar();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error");
            alert.show();
        }
        clear();
    }

    public void delete() throws UnirestException, IOException {
        Producto p = new Producto();
        p.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
        p.setEstatus(0);

        String url = "http://localhost:8080/DreamSoft_SICEFA/api/producto/deleteProducto";
        Gson gson = new Gson();
        String params = gson.toJson(p);

        try {
            HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                    .field("producto", params)
                    .asJson();
            String respuesta = apiResponse.getBody().getObject().getString("response");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Producto eliminado");
            alert.show();
            mostrar();
        } catch (UnirestException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error");
            alert.show();
        }
        clear();
    }

    public void search() {
        String nombreBuscar = txtBuscar.getText().trim().toLowerCase();

        if (nombreBuscar.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Ingrese un producto a buscar.");
            alert.show();
        } else {
            try {
                HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/producto/getAll")
                        .asJson();

                JsonNode responseBody = apiResponse.getBody();
                if (responseBody != null && responseBody.isArray()) {
                    Gson gson = new Gson();
                    Producto[] allProductos = gson.fromJson(responseBody.toString(), Producto[].class);

                    List<Producto> productosEncontrados = Arrays.stream(allProductos)
                            .filter(producto -> producto.getNombre().toLowerCase().contains(nombreBuscar))
                            .collect(Collectors.toList());

                    tblProductos.getItems().clear();
                    tblProductos.getItems().addAll(productosEncontrados);
                    tblProductos.refresh();
                    int numProductosEncontrados = productosEncontrados.size();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se encontraron " + numProductosEncontrados + " productos.");
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
            HttpResponse<JsonNode> apiResponse = Unirest.get("http://localhost:8080/DreamSoft_SICEFA/api/producto/getAll")
                    .asJson();

            JsonNode responseBody = apiResponse.getBody();
            if (responseBody != null && responseBody.isArray()) {
                Gson gson = new Gson();
                Producto[] productos = gson.fromJson(responseBody.toString(), Producto[].class);

                // Limpiar la tabla antes de agregar nuevos datos
                tblProductos.getItems().clear();

                // Agregar los datos de las sucursales a la tabla
                tblProductos.getItems().addAll(Arrays.asList(productos));
            }
        } catch (UnirestException e) {
            e.printStackTrace(); // Manejo apropiado de errores
        }
    }


    //Función que sirve para limpiar campos, utilizada tambien el sava, update y delete
    public void clear() {
        txtIdProducto.clear();
        txtNombre.clear();
        txtNombreGenerico.clear();
        txtFormaFarmaceutica.clear();
        txtUnidadMedida.clear();
        txtPresentacion.clear();
        txtPrincipalIndicacion.clear();
        txtContraindicaciones.clear();
        txtConcentracion.clear();
        txtUnidadesEnvase.clear();
        txtPrecioCompra.clear();
        txtPrecioVenta.clear();
        txtFoto.clear();
        txtRutaFoto.clear();
        txtCodigoBarras.clear();
    }

    //Función que sirve para seleccionar un campo de la tabla.
    private void selectInputs(Producto producto) {
        txtIdProducto.setText(String.valueOf(producto.getIdProducto()));
        txtNombre.setText(producto.getNombre());
        txtNombreGenerico.setText(producto.getNombreGenerico());
        txtFormaFarmaceutica.setText(producto.getFormaFarmaceutica());
        txtUnidadMedida.setText(producto.getUnidadMedida());
        txtPresentacion.setText(producto.getPresentacion());
        txtPrincipalIndicacion.setText(producto.getPrincipalIndicacion());
        txtContraindicaciones.setText(producto.getContraindicaciones());
        txtConcentracion.setText(producto.getConcentracion());
        txtUnidadesEnvase.setText(String.valueOf(producto.getUnidadesEnvase()));
        txtPrecioCompra.setText(String.valueOf(producto.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(producto.getPrecioVenta()));
        txtFoto.setText(producto.getFoto());
        txtRutaFoto.setText(producto.getRutaFoto());
        txtCodigoBarras.setText(producto.getCodigoBarras());
        txtEstatus.setText(String.valueOf(producto.getEstatus()));
    }

    public void mostrarSucursales() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/dreamsoft_sicefa/view_sucursal.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btnSucursales.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
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
