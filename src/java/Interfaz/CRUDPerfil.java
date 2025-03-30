package Interfaz;

import Model.Perfil;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDPerfil {

    List<Perfil> listar() throws SQLException;  // Listar todos los perfiles

    Optional<Perfil> obtener(int id) throws SQLException; // Obtener un perfil por ID

    boolean agregar(Perfil perfil) throws SQLException;  // Agregar un perfil

    boolean actualizar(Perfil perfil) throws SQLException; // Actualizar un perfil

    boolean eliminar(int id) throws SQLException; // Eliminar un perfil
}
