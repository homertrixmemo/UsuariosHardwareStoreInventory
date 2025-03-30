package Util;

import org.mindrot.jbcrypt.BCrypt;

public class SeguridadUtil {
    // Método para encriptar la contraseña
    public static String encriptar(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Genera un hash con fuerza 12
    }

    // Método para verificar la contraseña ingresada con la almacenada
    public static boolean verificar(String password, String hashAlmacenado) {
        return BCrypt.checkpw(password, hashAlmacenado);
    }
}
