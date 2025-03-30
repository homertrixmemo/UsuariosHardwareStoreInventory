package Interfaz;

import Model.Rol;
import Model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDRol {

    List<Rol> listar() throws SQLException;

    Optional<Rol> obtenerPorId(int id_rol) throws SQLException;

    boolean agregar(Rol rol) throws SQLException;

    boolean actualizar(Rol rol) throws SQLException;

    boolean eliminar(int id_rol) throws SQLException;

    boolean tieneUsuariosAsociados(int idRol);

    List<Usuario> listarUsuariosPorRol(int idRol); // <-- Agregado aquí
}
