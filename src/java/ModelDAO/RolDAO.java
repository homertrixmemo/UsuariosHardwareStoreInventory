package ModelDAO;

import Config.Conexion;
import Model.Rol;
import Model.Usuario;
import Interfaz.CRUDRol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RolDAO implements CRUDRol {

    private final Conexion cn = new Conexion();

    @Override
    public List<Rol> listar() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM rol";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                rol.setDescripcion(rs.getString("descripcion"));
                lista.add(rol);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    public List<Rol> listarRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol FROM rol";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                roles.add(rol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public List<Usuario> listarUsuariosPorRol(int idRol) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.email "
                + "FROM usuario u "
                + "WHERE u.rol_id_rol = ?";  // ⚠ Asegúrate de que la columna existe en tu tabla

        try (Connection con = Conexion.getConnection(); // Usar la conexión correctamente
                 PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));  // ✅ Debe coincidir con la consulta
                    usuario.setEmail(rs.getString("email"));
                    listaUsuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios por rol: " + e.getMessage());
        }

        System.out.println("Usuarios encontrados: " + listaUsuarios.size()); // 🛠 Debugging
        return listaUsuarios;
    }

    @Override
    public Optional<Rol> obtenerPorId(int id_rol) {
        String sql = "SELECT * FROM rol WHERE id_rol = ?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id_rol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setNombreRol(rs.getString("nombre_rol"));
                    rol.setDescripcion(rs.getString("descripcion"));
                    return Optional.of(rol);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rol por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean agregar(Rol rol) {
        String sql = "INSERT INTO rol (nombre_rol, descripcion) VALUES (?, ?)";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            ps.setString(2, rol.getDescripcion());
            int filasInsertadas = ps.executeUpdate();
            System.out.println("Filas insertadas: " + filasInsertadas);
            return filasInsertadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar rol: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Rol rol) {
        String sql = "UPDATE rol SET nombre_rol = ?, descripcion = ? WHERE id_rol = ?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            ps.setString(2, rol.getDescripcion());
            ps.setInt(3, rol.getIdRol());
            int filasActualizadas = ps.executeUpdate();
            System.out.println("Filas actualizadas: " + filasActualizadas);
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar rol: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idRol) {
        boolean eliminado = false;
        String sql = "DELETE FROM rol WHERE id_rol = ?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            eliminado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
        }
        return eliminado;
    }

    public boolean tieneUsuariosAsociados(int idRol) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE rol_id_rol = ?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Usuarios asociados al rol ID " + idRol + ": " + count); // ✅ Depuración
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
