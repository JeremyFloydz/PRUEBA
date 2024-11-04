package com.example.ejerciciol.conexion;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase de conexión a la base de datos
 */
public class ConexionBD {
    private Connection conexion;

    /**
     * Constructor que establece una conexión a la base de datos al instanciar la clase.
     *
     * @throws SQLException Hay que controlar errores de SQL.
     */
    public ConexionBD() throws SQLException {
        Properties configuracion = getConfiguracion();  // Cargar configuración desde el archivo de propiedades.

        // Configuración de la conexión usando las propiedades
        Properties connConfig = new Properties();
        connConfig.setProperty("user", configuracion.getProperty("user"));
        connConfig.setProperty("password", configuracion.getProperty("password"));

        // Construcción de la URL de conexión
        String url = "jdbc:mariadb://" + configuracion.getProperty("address") + ":" +
                configuracion.getProperty("port") + "/" + configuracion.getProperty("database") +
                "?serverTimezone=Europe/Madrid";

        // Asignación de la conexión a la variable de instancia
        this.conexion = DriverManager.getConnection(url, connConfig);
        this.conexion.setAutoCommit(true);

        // Información de conexión para depuración
        DatabaseMetaData databaseMetaData = conexion.getMetaData();
        System.out.println("--- Datos de conexión ------------------------------------------");
        System.out.printf("Base de datos: %s%n", databaseMetaData.getDatabaseProductName());
        System.out.printf("  Versión: %s%n", databaseMetaData.getDatabaseProductVersion());
        System.out.printf("Driver: %s%n", databaseMetaData.getDriverName());
        System.out.printf("  Versión: %s%n", databaseMetaData.getDriverVersion());
        System.out.println("----------------------------------------------------------------");
    }

    /**
     * Función que obtiene la configuración para la conexión a la base de datos
     *
     * @return objeto Properties con los datos de conexión a la base de datos
     */
    private Properties getConfiguracion() {
        Properties properties = new Properties();
        File configFile = new File("configuracion.properties");

        try (FileInputStream configFileReader = new FileInputStream(configFile)) {
            properties.load(configFileReader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("configuracion.properties no encontrado en la ruta " + configFile.getPath(), e);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
        }
        return properties;
    }

    /**
     * Esta clase devuelve la conexión creada.
     *
     * @return una conexión a la base de datos.
     */
    public Connection getConexion() {
        return this.conexion;
    }

    /**
     * Método para cerrar la conexión con la base de datos.
     *
     * @throws SQLException en caso de errores al cerrar la conexión.
     */
    public void closeConexion() throws SQLException {
        if (this.conexion != null && !this.conexion.isClosed()) {
            this.conexion.close();
            System.out.println("Conexión cerrada exitosamente.");
        }
    }
}
