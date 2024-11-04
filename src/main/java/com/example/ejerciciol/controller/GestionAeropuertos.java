package com.example.ejerciciol.controller;

import com.example.ejerciciol.dao.Dao;
import com.example.ejerciciol.model.AeropuertoInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class GestionAeropuertos implements Initializable {

    //Columnas de la tabla
    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColID;

    @FXML
    private TableColumn<AeropuertoInfo, String> aeroColNom;

    @FXML
    private TableColumn<AeropuertoInfo, String> aeroColPais;

    @FXML
    private TableColumn<AeropuertoInfo, String> aeroColCiudad;

    @FXML
    private TableColumn<AeropuertoInfo, String> aeroColCalle;

    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColNumero;

    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColAnio;

    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColCapacidad;

    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColNsocios;

    @FXML
    private TableColumn<AeropuertoInfo, BigDecimal> aeroColFinanciacion;

    @FXML
    private TableColumn<AeropuertoInfo, Integer> aeroColNtrabajadores;

    //Bótones
    @FXML
    private RadioButton btnPrivados;

    @FXML
    private RadioButton btnPublicos;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private TableView<AeropuertoInfo> tablaAeros;

    ObservableList<AeropuertoInfo> listaAeropuertos;

    @FXML
    private TextField txtfiltrarNom;

    @FXML
    void filtrarAeros(KeyEvent event) {
        // Obtiene el texto ingresado en el campo de filtrado y lo convierte a minúsculas
        String filtro = txtfiltrarNom.getText().toLowerCase();

        // Iterar a través de la lista de personas y agregar las que coincidan con el filtro
        ObservableList<AeropuertoInfo> resultadosFiltrados = FXCollections.observableArrayList();

        for (AeropuertoInfo aeropuerto : listaAeropuertos) {
            if (aeropuerto.getNombre().toLowerCase().contains(filtro)) {
                resultadosFiltrados.add(aeropuerto);
            }
        }
        // Actualizar la tabla con los resultados filtrados
        tablaAeros.setItems(resultadosFiltrados);
    }

    /**
     * Metodo que lleva a la ventana de añadir un nuevo aeropuerto
     * @param event
     * @throws IOException
     */
    @FXML
    void anadirAero(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AniadirEditarAero.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(true);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aviones - Añadir Aeropuerto");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Metodo que llama al metodo de la ventana llevando el registro seleccionado
     * @param event		Evento de click
     * @throws IOException
     */
    @FXML
    void editarAero(ActionEvent event) throws IOException {
        Dao dao = new Dao();
        AeropuertoInfo AeroSeleccionado = tablaAeros.getSelectionModel().getSelectedItem();
        if (AeroSeleccionado != null) {
            ventanaEditarAero(AeroSeleccionado);
            actualizarTabla(btnPrivados.isSelected()); // Actualizar la tabla después de editar
        } else {
            dao.mostrarError("Selecciona un aeropuerto para modificar");
        }
    }

    /**
     * Metodo que lanza la ventana editar
     * @param aeroSeleccionado	El aeropuerto seleccionado
     * @throws IOException		Pillar excepciónes
     */
    public void ventanaEditarAero(AeropuertoInfo aeroSeleccionado) throws IOException {
        // Cargar el archivo FXML de la ventana modal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditarAero.fxml"));
        Parent root = loader.load();

        // Crear una instancia del controlador de la ventana modal
        EditarAero modalController = loader.getController();
        modalController.initAttributtes(listaAeropuertos, aeroSeleccionado);

        // Configurar el controlador de la ventana modal
        modalController.setEscenaPrincipalController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aviones - Editar Aeropuerto");
        stage.setScene(scene);
        stage.showAndWait();
        // Actualizar la tabla
        tablaAeros.refresh();
    }

    /**
     * Metodo que elimina un aeropuerto
     * @param event	Al hacer click en el bóton
     */
    @FXML
    void borrarAero(ActionEvent event) {
        //si el aero privado o publico
        //1 eliminar el aero_tipo
        //2 aeropuerto
        //3 direccion
        Dao dao = new Dao();
        AeropuertoInfo aeroSeleccionado = tablaAeros.getSelectionModel().getSelectedItem();
        if (aeroSeleccionado == null) {
            dao.mostrarError("Selecciona un aeropuerto para eliminar");
        } else {
            //Lanzar la ventana de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Seguro que quieres eliminar este aeropuerto?");
            alert.setContentText(aeroSeleccionado.getNombre());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //Una vez eliminada una direccion se elimina el aero y aero_tipo (delete on cascade)
                int id_direccion = dao.obtenerIdDireccion(aeroSeleccionado.getIdAeropuerto());
                boolean eliDir = dao.eliminardireccion(id_direccion);
                if (eliDir) {
                    dao.mostrarInformacion("Aeropuerto bien eliminado");
                } else {
                    dao.mostrarError("Error al eliminar el aeropuerto");
                }
            }
        }
    }

    /**
     * Metodo que muestra todos los datos de un aeropuerto
     * @param event		Al hacer click en el bóton
     * @throws SQLException
     */
    @FXML
    void mostrarAero(ActionEvent event) throws SQLException {
        Dao dao = new Dao();
        AeropuertoInfo aeroSeleccionado = tablaAeros.getSelectionModel().getSelectedItem();
        if (aeroSeleccionado == null) {
            dao.mostrarError("Selecciona un aeropuerto para mostrar su información");
        } else {
            //mostrar ventana modal tipo info
            //id_aeropuerto
            int id_aero = aeroSeleccionado.getIdAeropuerto();

            //Datos de la parte superior
            String nombre = aeroSeleccionado.getNombre();
            String pais = aeroSeleccionado.getPais();
            String direccion = "C\\ "+aeroSeleccionado.getCalle()+" "+aeroSeleccionado.getNumero()+", "+aeroSeleccionado.getCiudad();
            int anio = aeroSeleccionado.getAnio_inauguracion();
            int capacidad = aeroSeleccionado.getCapacidad();
            String datosSuperior = "Nombre: " + nombre + "\n" +
                    "País: " + pais + "\n" +
                    "Dirección: " + direccion + "\n" +
                    "Año de Inauguración: " + anio + "\n" +
                    "Capacidad: " + capacidad;

            //Parte del medio datos de aviones
            String infoAviones = dao.obtenerInfoAviones(id_aero);

            //Parte de abajo datos según el tipo del aeropuerto
            String infoTipo = "";
            if (dao.aeroEsPrivado(id_aero)) {
                infoTipo = infoTipo + "Privado\n" + "Número de socios: " + aeroSeleccionado.getNum_socios() ;
            } else {
                infoTipo = infoTipo + "Publico\n" + "Financiación: " + aeroSeleccionado.getFinanciacion() + "\nNúmero de trabajadores: " + aeroSeleccionado.getNum_trabajadores();
            }


            // Concatenar los datos superiores con la información de los aviones
            String datosCompletos = datosSuperior + infoAviones + infoTipo;

            //La ventana de información
            Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
            alertaInfo.setTitle("Información");
            alertaInfo.setHeaderText(null);
            alertaInfo.setContentText(datosCompletos);
            alertaInfo.showAndWait();
        }
    }



    @FXML
    void anadirAvion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AniadirAvion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aviones - Añadir Avión");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void activacionAvion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Activar-DesactivarAvion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aviones - Activar/Desactivar Avión");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Metodo que borra un avión
     * @param event		Al hacer click en el bóton
     * @throws IOException
     */
    @FXML
    void borrarAvion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BorrarAvion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aviones - Eliminar Avión");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void ayuda(ActionEvent event) {
        //DE momento no hace nada
    }

    //Actualizar la tabla
    private void actualizarTabla(boolean esPrivado) {
        Dao dao = new Dao();
        listaAeropuertos = dao.obtenerDatosAeropuertos(esPrivado);
        tablaAeros.setItems(listaAeropuertos);
        tablaAeros.refresh();

        // Configurar visibilidad de columnas según el tipo de aeropuerto
        aeroColNsocios.setVisible(esPrivado);
        aeroColFinanciacion.setVisible(!esPrivado);
        aeroColNtrabajadores.setVisible(!esPrivado);
    }


    private void configurarColumnasTabla() {
        aeroColID.setCellValueFactory(new PropertyValueFactory<>("idAeropuerto"));
        aeroColNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        aeroColPais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        aeroColCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        aeroColCalle.setCellValueFactory(new PropertyValueFactory<>("calle"));
        aeroColNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        aeroColAnio.setCellValueFactory(new PropertyValueFactory<>("anio_inauguracion"));
        aeroColCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        aeroColNsocios.setCellValueFactory(new PropertyValueFactory<>("num_socios"));
        aeroColFinanciacion.setCellValueFactory(new PropertyValueFactory<>("financiacion"));
        aeroColNtrabajadores.setCellValueFactory(new PropertyValueFactory<>("num_trabajadores"));
    }

    /**
     * Inicializar las tablas con los datos desde la BD
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        configurarColumnasTabla(); // Configura las columnas de la tabla

        // Seleccionar un botón de radio por defecto btnPrivado
        btnPrivados.setSelected(true);
        actualizarTabla(true); // Cargar datos para aeropuertos privados primero

        // Agregar listeners a los botones de radio
        btnPrivados.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                actualizarTabla(true);
            }
        });

        btnPublicos.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                actualizarTabla(false);
            }
        });
    }


}