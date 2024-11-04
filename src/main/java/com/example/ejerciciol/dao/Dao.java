package com.example.ejerciciol.dao;

import com.example.ejerciciol.conexion.ConexionBD;
import com.example.ejerciciol.model.AeropuertoInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {

    private ConexionBD conexion;
    public boolean verificarCredenciales(String user, String pass) {
        boolean access = false;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT count(*) FROM usuarios WHERE usuario = ? AND password = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Si hay al menos un registro que coincide, establece access en true
                access = true;
            }
            rs.close();
            conexion.closeConexion();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return access;
    }


    /**
     * Metodo para que salga la ventana de tipo error con el mensaje querido
     * @param mensaje
     */
    public void mostrarError(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("ERROR");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo informacion con el mensaje querido
     * @param mensaje
     */
    public void mostrarInformacion(String mensaje) {
        Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
        alertaInfo.setTitle("INFO");
        alertaInfo.setHeaderText(null);
        alertaInfo.setContentText(mensaje);
        alertaInfo.showAndWait();
    }

    public String primeraLetraMayo(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public int obtenerIdDireccion1(AeropuertoInfo aero) {
        int idDireccion = 0;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT id_direccion FROM aeropuertos WHERE id = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, aero.getIdAeropuerto());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idDireccion = rs.getInt("id_direccion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idDireccion;
    }

    /*public int obtenerIdDireccion(int id_aero) {
        int idDireccion = -1; // Cambiado a -1 para indicar un valor no encontrado o error
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conexion = new ConexionBD().getConexion(); // Asegúrate de que este método devuelva una conexión válida
            String consulta = "SELECT id_direccion FROM aeropuertos WHERE id = ?";
            pstmt = conexion.prepareStatement(consulta);
            pstmt.setInt(1, id_aero);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idDireccion = rs.getInt("id_direccion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, PreparedStatement y Connection
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idDireccion;
    }*/

    public int obtenerIdDireccion(int id_aero) {
        int idDireccion = -1; // Valor -1 para indicar que no se encontró ningún resultado
        ConexionBD conexionBD = null;
        Connection connection = null; // Declarar la conexión como Connection para poder usar métodos de SQL
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Crear la instancia de ConexionBD y obtener la conexión SQL
            conexionBD = new ConexionBD();
            connection = conexionBD.getConexion(); // Obtener la conexión desde ConexionBD

            // Preparar y ejecutar la consulta SQL
            String consulta = "SELECT id_direccion FROM aeropuertos WHERE id = ?";
            pstmt = connection.prepareStatement(consulta);
            pstmt.setInt(1, id_aero);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idDireccion = rs.getInt("id_direccion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar ResultSet, PreparedStatement, y Connection
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close(); // Cierra la conexión SQL directamente
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idDireccion;
    }

    public boolean eliminardireccion(int id_direccion) {
        try {
            conexion = new ConexionBD();
            String consulta = "DELETE FROM direcciones WHERE id = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, id_direccion);
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.closeConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerInfoAviones(int idAero) throws SQLException {
        StringBuilder informacion = new StringBuilder();
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;

        conexion = new ConexionBD(); // Suponemos que esta es tu clase para conectar con la base de datos
        try {
            String sqlAviones = "SELECT modelo,numero_asientos,velocidad_maxima,activado FROM aviones WHERE id_aeropuerto = ?";
            pstmt = conexion.getConexion().prepareStatement(sqlAviones);
            pstmt.setInt(1, idAero);

            ResultSet rsAviones = pstmt.executeQuery();
            boolean hayAviones = false; // Flag para verificar si hay aviones
            StringBuilder avionesInfo = new StringBuilder();

            while (rsAviones.next()) {
                if (!hayAviones) {
                    avionesInfo.append("\nAviones:").append("\n");
                    hayAviones = true; // Se encontraron aviones, cambia el flag a true
                }
                avionesInfo.append("\tModelo: ").append(rsAviones.getString("modelo")).append("\n")
                        .append("\tNúmero de asientos: ").append(rsAviones.getInt("numero_asientos")).append("\n")
                        .append("\tVelocidad máxima: ").append(rsAviones.getInt("velocidad_maxima")).append("\n")
                        .append("\t").append(rsAviones.getInt("activado") == 1 ? "Activado" : "Desactivado").append("\n");
            }
            if (hayAviones) {
                informacion.append(avionesInfo); // Añade la información de aviones solo si hay aviones
            }

            rsAviones.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Lanza la excepción para que pueda ser manejada más arriba en la pila de llamadas
        } finally {
            // Asegúrate de cerrar los recursos en el bloque finally
            try {
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.closeConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return informacion.toString();
    }

    public ObservableList<AeropuertoInfo> obtenerDatosAeropuertos(boolean btnPrivado) {
        ObservableList<AeropuertoInfo> aeropuertos = FXCollections.observableArrayList();
        String queryPublico = "SELECT\r\n"
                + "    a.id AS id_aeropuerto,\r\n"
                + "    a.nombre,\r\n"
                + "    d.pais,\r\n"
                + "    d.ciudad,\r\n"
                + "    d.calle,\r\n"
                + "    d.numero,\r\n"
                + "    a.anio_inauguracion,\r\n"
                + "    a.capacidad,\r\n"
                + "    ap.financiacion,\r\n"
                + "    ap.num_trabajadores\r\n"
                + "FROM\r\n"
                + "    aeropuertos a\r\n"
                + "JOIN direcciones d ON a.id_direccion = d.id\r\n"
                + "INNER JOIN aeropuertos_publicos ap ON a.id = ap.id_aeropuerto;\r\n"
                + "";
        String queryPrivado = "SELECT\r\n"
                + "    a.id AS id_aeropuerto,\r\n"
                + "    a.nombre,\r\n"
                + "    d.pais,\r\n"
                + "    d.ciudad,\r\n"
                + "    d.calle,\r\n"
                + "    d.numero,\r\n"
                + "    a.anio_inauguracion,\r\n"
                + "    a.capacidad,\r\n"
                + "    ap.numero_socios\r\n"
                + "FROM\r\n"
                + "    aeropuertos a\r\n"
                + "JOIN direcciones d ON a.id_direccion = d.id\r\n"
                + "INNER JOIN aeropuertos_privados ap ON a.id = ap.id_aeropuerto;\r\n"
                + "";
        try {
            conexion = new ConexionBD();
            if (btnPrivado) {		//TRUE
                PreparedStatement pst = conexion.getConexion().prepareStatement(queryPrivado);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int idAeropuerto = rs.getInt("id_aeropuerto");
                    String nombre = rs.getString("nombre");
                    String pais = rs.getString("pais");
                    String ciudad = rs.getString("ciudad");
                    String calle = rs.getString("calle");
                    int numero = rs.getInt("numero");
                    int anio_inauguracion = rs.getInt("anio_inauguracion");
                    int capacidad = rs.getInt("capacidad");
                    int numero_socios = rs.getInt("numero_socios");
                    // usando el constructor de aeropuertos privados
                    AeropuertoInfo aeropuerto = new AeropuertoInfo(idAeropuerto, nombre, pais, ciudad, calle, numero, anio_inauguracion, capacidad, numero_socios);
                    aeropuertos.add(aeropuerto);
                }
            } else {	//FALSE
                PreparedStatement pst = conexion.getConexion().prepareStatement(queryPublico);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    int idAeropuerto = rs.getInt("id_aeropuerto");
                    String nombre = rs.getString("nombre");
                    String pais = rs.getString("pais");
                    String ciudad = rs.getString("ciudad");
                    String calle = rs.getString("calle");
                    int numero = rs.getInt("numero");
                    int anio_inauguracion = rs.getInt("anio_inauguracion");
                    int capacidad = rs.getInt("capacidad");
                    BigDecimal financiacion = rs.getBigDecimal("financiacion");
                    int num_trabajadores = rs.getInt("num_trabajadores");

                    AeropuertoInfo aeropuerto = new AeropuertoInfo(idAeropuerto, nombre, pais, ciudad, calle, numero, anio_inauguracion, capacidad, financiacion, num_trabajadores);

                    aeropuertos.add(aeropuerto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aeropuertos;
    }

    public boolean aeroEsPrivado(int id_Aero) {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM aeropuertos_privados WHERE id_aeropuerto = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setInt(1, id_Aero);
            ResultSet rs = pstmt.executeQuery();
            boolean tieneResultados = rs.next();
            rs.close();
            pstmt.close();
            conexion.closeConexion();
            return tieneResultados;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEsNumero(String dato) {
        if (dato.matches("\\d+")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean modificarDireccion(AeropuertoInfo aero, int id_dir) {
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;

        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE direcciones SET pais = ?, ciudad = ?, calle = ?, numero = ? WHERE id = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, aero.getPais());
            pstmt.setString(2, aero.getCiudad());
            pstmt.setString(3, aero.getCalle());
            pstmt.setInt(4, aero.getNumero());
            pstmt.setInt(5, id_dir);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar el PreparedStatement y la conexión
            try {
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.closeConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean modificarAero(AeropuertoInfo aero, int id_aero) {
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;

        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE aeropuertos SET nombre = ?, anio_inauguracion = ?, capacidad = ? WHERE id = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, aero.getNombre());
            pstmt.setInt(2, aero.getAnio_inauguracion());
            pstmt.setInt(3, aero.getCapacidad());
            pstmt.setInt(4, id_aero);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar el PreparedStatement y la conexión
            try {
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.closeConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Metodo que modifica una aeropuerto privado
     * @param id_aero	id_Aeropuerto
     * @param socios	El nuevo número de socios
     * @return			true/false
     */
    public boolean modificarAeroPrivado(int id_aero, int socios) {
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;

        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE aeropuertos_privados SET numero_socios = ? WHERE id_aeropuerto = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setInt(1, socios);
            pstmt.setInt(2, id_aero);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar el PreparedStatement y la conexión
            try {
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.closeConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public boolean modificarAeroPublico(int id_aero, int numTra, BigDecimal finan) {
        ConexionBD conexion = null;
        PreparedStatement pstmt = null;

        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE aeropuertos_publicos SET num_trabajadores = ?, financiacion = ? WHERE id_aeropuerto = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setInt(1, numTra);
            pstmt.setBigDecimal(2, finan);
            pstmt.setInt(3, id_aero);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar el PreparedStatement y la conexión
            try {
                if (pstmt != null) pstmt.close();
                if (conexion != null) conexion.closeConexion();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



}
