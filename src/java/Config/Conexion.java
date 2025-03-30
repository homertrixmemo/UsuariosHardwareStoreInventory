package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/hardware_store";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ⚠ Asegúrate de que esté correcta
    private static Connection con = null;
    private static final Logger LOGGER = Logger.getLogger(Conexion.class.getName());

    // Método para obtener la conexión
    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL
                con = DriverManager.getConnection(URL, USER, PASSWORD); // Establecer la conexión
                LOGGER.log(Level.INFO, "✅ Conexión establecida con la base de datos.");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "❌ Error: Driver MySQL no encontrado. Asegúrate de incluir el JAR del conector.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "❌ Error SQL al conectar: {0}", e.getMessage());
        }
        return con;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion() {
        try {
            if (con != null && !con.isClosed()) {
                con.close(); // Cerrar la conexión
                con = null; // Limpiar la referencia
                LOGGER.log(Level.INFO, "🔌 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "⚠ Error al cerrar la conexión: {0}", e.getMessage());
        }
    }

    // Método para verificar si la conexión está activa (opcional)
    public static boolean isConexionActiva() {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "⚠ Error al verificar el estado de la conexión: {0}", e.getMessage());
            return false;
        }
    }
}
