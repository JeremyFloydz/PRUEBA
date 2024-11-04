package com.example.ejerciciol.controller;

import java.math.BigDecimal;

import com.example.ejerciciol.dao.Dao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.example.ejerciciol.model.AeropuertoInfo;

public class EditarAero {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnGuardar;

    @FXML
    private RadioButton btnPrivado;

    @FXML
    private RadioButton btnPublico;

    @FXML
    private GridPane gridPrivado;

    @FXML
    private GridPane gridPublico;

    @FXML
    private ToggleGroup grupoBtn;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtCalle;

    @FXML
    private TextField txtCapacidad;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtFinanciacion;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtNsocios;

    @FXML
    private TextField txtNtrabajadores;

    @FXML
    private TextField txtNumero;

    @FXML
    private TextField txtPais;
    
    private ObservableList<AeropuertoInfo> aeros;
    private AeropuertoInfo aero;
    private GestionAeropuertos escenaPrincipalController;
    
    Dao dao = new Dao();
    
    boolean esPrivado;
    
    private int idAeroSeleccionado;

    /**
     * Maneja el evento del botón guardar. Recoge y valida los datos introducidos por el usuario,
     * y actualiza la información del aeropuerto en la base de datos.
     * 
     * @param event El evento de acción que desencadena este método.
     */
    @FXML
    void guardar(ActionEvent event) {
    	String nombre = this.txtNom.getText();
    	String pais = this.txtPais.getText();
    	String ciudad = this.txtCiudad.getText();
    	String calle = this.txtCalle.getText();
    	
    	String num = this.txtNumero.getText();
    	String an = this.txtAnio.getText();
    	String capa = this.txtCapacidad.getText();
    	
    	//Poner los datos de forma que le primera letra en mayuscula
    	nombre = dao.primeraLetraMayo(nombre);
    	pais = dao.primeraLetraMayo(pais);
    	ciudad = dao.primeraLetraMayo(ciudad);
    	calle = dao.primeraLetraMayo(calle);	
    	
    	//Inicialización de variables
    	int numero = 0;
    	int anio = 0;
    	int capacidad = 0;
    	
    	int nSocios = 0;
    	
    	BigDecimal financiacion = null;
    	int numTrabajadores = 0;
    	
    	//Saber que tipo de aeropuerto vamos a modificar
    	if (esPrivado) {
    		String numSo = this.txtNsocios.getText();
    		
        	if (nombre.isEmpty()||pais.isEmpty()||ciudad.isEmpty()||calle.isEmpty()
        			||num.isEmpty()||an.isEmpty()||capa.isEmpty()||numSo.isEmpty()) {
        		dao.mostrarError("Hay que rellenar todos los campos");
        	} else {
        		//Conversion
        		try {
        		  	numero = Integer.parseInt(num);
        	    	anio = Integer.parseInt(an);
        	    	capacidad = Integer.parseInt(capa);
        	    	nSocios = Integer.parseInt(numSo);
        		} catch(NumberFormatException e) {
    				dao.mostrarError("Todos los campos (número,año de inauguración,capacidad y número de socios) deben ser números enteros válidos.");
    			}
        		if (dao.checkEsNumero(nombre)||dao.checkEsNumero(pais)||dao.checkEsNumero(ciudad)||dao.checkEsNumero(calle)) {
        			dao.mostrarError("Los campos (nombre, país, ciudad, calle) no pueden ser números!");
        		} else {
        			//Hacer check de si ya existe algun registro igual al nuevo modificado
        			AeropuertoInfo aeroModificado;
        			aeroModificado = new AeropuertoInfo(idAeroSeleccionado, nombre, pais, ciudad, calle, numero, anio, capacidad, nSocios);
        			if (aeros.contains(aeroModificado)) {
        				dao.mostrarError("Aeropuerto ya existe con los nuevos datos");
        			} else {		//Hacemos los cambios
        				//Direcciones
        				int id_direccion = dao.obtenerIdDireccion(idAeroSeleccionado);
        				boolean modiDirExito = dao.modificarDireccion(aeroModificado, id_direccion);
        				if (modiDirExito) {
        					System.out.println("Direccion bien modificado");
        				} else {
        					System.err.println("Error al modificar la direccion!");
        				}
        				
        				//Aeropuerto
        				boolean modiAeroExito = dao.modificarAero(aeroModificado, idAeroSeleccionado);
        				if (modiAeroExito) {
        					System.out.println("Aeropuerto bien modificado");
        				} else {
        					System.out.println("Error al modificar el aeropuerto!");
        				}
        				
        				//Aeropuerto_privado
        				boolean modiAeroPrivadoExito = dao.modificarAeroPrivado(idAeroSeleccionado, aeroModificado.getNum_socios());
        				if (modiAeroPrivadoExito) {
        					System.out.println("Aeropuerto_privado bien modificado");
        				} else {
        					System.out.println("Error al modificar el aeropuerto_privado!");
        				}
        				//Todo bien hecho
        				if (modiDirExito && modiAeroExito && modiAeroPrivadoExito) {
        					dao.mostrarInformacion("Modificación del aeropuerto bien hecha");
        				} else {
        					dao.mostrarError("No se ha podido hacer la modificación del aeropuerto!");
        				}
        				//Cerrar la ventana
        				Stage stage = (Stage) btnGuardar.getScene().getWindow();
        				stage.close();
        			}     			
        		}
        	}
    	} else {	//Estamos modificando un aero publico
    		String finan = this.txtFinanciacion.getText();//double+1letramayu)
    		String nTra = this.txtNtrabajadores.getText();
    		
    		if (nombre.isEmpty()||pais.isEmpty()||ciudad.isEmpty()||calle.isEmpty()
        			||num.isEmpty()||an.isEmpty()||capa.isEmpty()||finan.isEmpty()|| nTra.isEmpty()) {
        		dao.mostrarError("Hay que rellenar todos los campos");
        	} else {
        		//Conversion
        		try {
         		  	numero = Integer.parseInt(num);
        	    	anio = Integer.parseInt(an);
        	    	capacidad = Integer.parseInt(capa);
        	    	financiacion = new BigDecimal(finan);
        	    	numTrabajadores = Integer.parseInt(nTra);
        		} catch(NumberFormatException e) {
    				dao.mostrarError("Todos los campos (número,año de inauguración,capacidad, financiación y número de trabajadores) deben ser números enteros válidos.");
        		}
        		if (dao.checkEsNumero(nombre)||dao.checkEsNumero(pais)||dao.checkEsNumero(ciudad)||dao.checkEsNumero(calle)) {
        			dao.mostrarError("Los campos (nombre, país, ciudad, calle) no pueden ser números!");
        		} else {
        			//Hacer check de si ya existe algun registro igual al nuevo modificado
        			AeropuertoInfo aeroModificado;
        			aeroModificado = new AeropuertoInfo(idAeroSeleccionado, nombre, pais, ciudad, calle, numero, anio, capacidad, financiacion, numTrabajadores);
        			if (aeros.contains(aeroModificado)) {
        				dao.mostrarError("Aeropuerto ya existe con los nuevos datos");
        			} else {//Hacemos los cambios
        				//Direcciones
        				int id_direccion = dao.obtenerIdDireccion(idAeroSeleccionado);
        				boolean modiDirExito = dao.modificarDireccion(aeroModificado, id_direccion);
        				if (modiDirExito) {
        					System.out.println("Direccion bien modificado");
        				} else {
        					System.err.println("Error al modificar la direccion!");
        				}
        				
        				//Aeropuerto
        				boolean modiAeroExito = dao.modificarAero(aeroModificado, idAeroSeleccionado);
        				if (modiAeroExito) {
        					System.out.println("Aeropuerto bien modificado");
        				} else {
        					System.out.println("Error al modificar el aeropuerto!");
        				}
        				
        				//Aeropuerto_publico
        				boolean modiAeroPublicoExito = dao.modificarAeroPublico(idAeroSeleccionado, numTrabajadores, financiacion);
        				if (modiAeroPublicoExito) {
        					System.out.println("Aeropuerto_publico bien modificado");
        				} else {
        					System.out.println("Error al modificar el aeropuerto_publico!");
        				}
        				//Todo bien hecho
        				if (modiDirExito && modiAeroExito && modiAeroPublicoExito) {
        					dao.mostrarInformacion("Modificación del aeropuerto bien hecha");
        				} else {
        					dao.mostrarError("No se ha podido hacer la modificación del aeropuerto!");
        				}
        				//Cerrar la ventana
        				Stage stage = (Stage) btnGuardar.getScene().getWindow();
        				stage.close();	
        			}
        		}
        	}
    	}
    }
    
    /**
     *	Metodo para cerrar la ventana editar 
     * @param event	Hacer click en el bóton cancelar
     */
    @FXML
    void cancelar(ActionEvent event) {
    	Stage stage = (Stage) btnCancelar.getScene().getWindow();
    	stage.close();
    }
    
    /**
     * Establece el controlador de la escena principal.
     * 
     * @param gestionAeropuertos Controlador de la escena principal con la que interactúa esta clase.
     */
	public void setEscenaPrincipalController(GestionAeropuertos gestionAeropuertos) {
		this.escenaPrincipalController = gestionAeropuertos;
	}
	
    /**
     * Inicializa los atributos de la clase con los datos del aeropuerto seleccionado y la lista de aeropuertos.
     * 
     * @param listaAeropuertos Lista observable de información de aeropuertos.
     * @param aeroSeleccionado Objeto AeropuertoInfo que representa al aeropuerto seleccionado.
     */
	public void initAttributtes(ObservableList<AeropuertoInfo> listaAeropuertos, AeropuertoInfo aeroSeleccionado) {
		
		this.aeros = listaAeropuertos;
		aero = aeroSeleccionado;
		txtNom.setText(aeroSeleccionado.getNombre());
		txtPais.setText(aeroSeleccionado.getPais());
		txtCiudad.setText(aeroSeleccionado.getCiudad());
		txtCalle.setText(aeroSeleccionado.getCalle());
		txtNumero.setText(aeroSeleccionado.getNumero() + "");
		txtAnio.setText(aeroSeleccionado.getAnio_inauguracion() + "");
		txtCapacidad.setText(aeroSeleccionado.getCapacidad() + "");
				
		//Obtengo el id del aeropuerto seleccionado y con un metodo consigo 
		//el id del Aeropuerto seleccionado
		idAeroSeleccionado = aeroSeleccionado.getIdAeropuerto();
		// si el aeropuerto privado o no
		esPrivado = dao.aeroEsPrivado(idAeroSeleccionado);
		//Y según el tipo del aeropuerto pongo los datos 
		if (esPrivado) {
			btnPublico.setSelected(false);			
			btnPrivado.setSelected(true);
			
			gridPrivado.setVisible(true);
			gridPublico.setVisible(false);
			txtNsocios.setText(aeroSeleccionado.getNum_socios() +"");			
		} else {
			btnPrivado.setSelected(false);
			btnPublico.setSelected(true);
			
			gridPublico.setVisible(true);
			gridPrivado.setVisible(false);
			txtFinanciacion.setText(aeroSeleccionado.getFinanciacion() + "");
			txtNtrabajadores.setText(aeroSeleccionado.getNum_trabajadores() +"");
		}	
	}
}
