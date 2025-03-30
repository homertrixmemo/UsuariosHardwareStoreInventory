package Interfaz;

import Model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDUsuario {

    List<Usuario> listar() throws SQLException;  // Listar todos los usuarios

    Optional<Usuario> obtener(int id) throws SQLException; // Obtener un usuario por ID (evita null)

    boolean agregar(Usuario usuario) throws SQLException;  // Agregar un usuario

    boolean actualizar(Usuario usuario) throws SQLException; // Actualizar un usuario

    boolean eliminar(int id) throws SQLException; // Eliminar un usuario
}
