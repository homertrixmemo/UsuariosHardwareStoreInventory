package ModelDAO;

import Model.Usuario;
import Model.Rol;
import Model.Perfil;
import Config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.email, u.password, "
                + "p.id_perfil, p.nombre_perfil, r.nombre_rol "
                + "FROM usuario u "
                + "JOIN perfil p ON u.perfil_id_perfil = p.id_perfil "
                + "LEFT JOIN rol r ON u.rol_id_rol = r.id_rol"; // <--- CAMBIO AQUÍ

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("id_perfil"),
                        rs.getString("nombre_perfil"),
                        rs.getString("nombre_rol") // <-- Si un usuario no tiene rol, devuelve NULL
                );
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    // ✅ Método para listar usuarios asociados a un perfil específico
    public List<Usuario> listarPorPerfil(int idPerfil) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.email, u.password, "
                + "p.id_perfil, p.nombre_perfil "
                + "FROM usuario u "
                + "JOIN perfil p ON u.perfil_id_perfil = p.id_perfil "
                + "WHERE u.perfil_id_perfil = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPerfil);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre_usuario"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("id_perfil"),
                            rs.getString("nombre_perfil")
                    );
                    listaUsuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public List<Usuario> listarPorRol(int rolId) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.email, r.nombre_rol "
                + "FROM usuario u "
                + "INNER JOIN ur ON u.id_usuario = ur.usuario_id_usuario "
                + "INNER JOIN rol r ON ur.rol_id_rol = r.id_rol "
                + "WHERE r.id_rol = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, rolId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombreRol(rs.getString("nombre_rol"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    public List<Perfil> listarPerfiles() {
        List<Perfil> listaPerfiles = new ArrayList<>();
        String sql = "SELECT id_perfil, nombre_perfil FROM perfil"; // Ajusta los nombres según tu BD.

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setIdPerfil(rs.getInt("id_perfil"));
                perfil.setNombrePerfil(rs.getString("nombre_perfil"));
                listaPerfiles.add(perfil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPerfiles;
    }

    public List<Rol> listarRoles() {
        List<Rol> listaRoles = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol FROM rol"; // Ajusta si los nombres de la tabla/columnas son diferentes.

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                listaRoles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRoles;
    }

    // 📌 1️⃣ Método para obtener los roles asociados a un usuario
    public List<Rol> obtenerRolesAsociados(int idUsuario) {
        List<Rol> listaRoles = new ArrayList<>();
        String sql = "SELECT r.id_rol, r.nombre FROM  ur "
                + "JOIN rol r ON ur.rol_id_rol = r.id_rol WHERE ur.usuario_id_usuario = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setNombreRol(rs.getString("nombre_rol"));
                listaRoles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRoles;
    }

    // ✅ Método para agregar un usuario
    public boolean agregar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre_usuario, email, password, perfil_id_perfil, rol_id_rol) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword()); // Asegúrate de encriptarla si es necesario
            ps.setInt(4, usuario.getIdPerfil());
            ps.setInt(5, usuario.getIdRol());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Método para obtener el último ID insertado en la tabla usuario
    public int obtenerUltimoId() {
        String sql = "SELECT LAST_INSERT_ID()";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int ultimoId = rs.getInt(1);
                System.out.println("Último ID insertado: " + ultimoId);
                return ultimoId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ✅ Método para asignar un rol a un usuario en la tabla 
    public boolean asignarRol(int usuarioId, int rolId) {
        String sql = "INSERT INTO  (usuario_id_usuario, rol_id_rol) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, rolId);

            int filasAfectadas = ps.executeUpdate();
            System.out.println("📝 Filas insertadas en : " + filasAfectadas); // ✅ Verifica en consola

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Método para actualizar un usuario
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre_usuario = ?, email = ?, password = ?, perfil_id_perfil = ?, rol_id_rol = ? WHERE id_usuario = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getIdPerfil());  // Usamos getIdPerfil() para el valor de perfil_id_perfil
            ps.setInt(5, usuario.getIdRol());     // Usamos getIdRol() para el valor de rol_id_rol
            ps.setInt(6, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // ✅ Método para actualizar el rol de un usuario
    public boolean actualizarRol(int usuarioId, int rolId) {
        String sql = "INSERT INTO  (usuario_id_usuario, rol_id_rol) VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE rol_id_rol = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, rolId);
            ps.setInt(3, rolId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 📌 2️⃣ Método para eliminar un usuario
    public boolean eliminarUsuario(int idUsuario) {
        String sql1 = "UPDATE usuario SET perfil_id_perfil = NULL, rol_id_rol = NULL WHERE id_usuario = ?";
        String sql2 = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps1 = con.prepareStatement(sql1); PreparedStatement ps2 = con.prepareStatement(sql2)) {

            // 1️⃣ Desvincular el usuario de perfil y rol
            ps1.setInt(1, idUsuario);
            ps1.executeUpdate();

            // 2️⃣ Eliminar el usuario
            ps2.setInt(1, idUsuario);
            return ps2.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Método optimizado para obtener un usuario por ID con perfil y rol correctos
    public Usuario obtenerPorId(int id) {
        Usuario usuario = null;
        String sql = "SELECT u.id_usuario, u.nombre_usuario, u.email, u.password, "
                + "u.perfil_id_perfil, p.nombre_perfil, u.rol_id_rol, r.nombre_rol "
                + "FROM usuario u "
                + "JOIN perfil p ON u.perfil_id_perfil = p.id_perfil "
                + "LEFT JOIN rol r ON u.rol_id_rol = r.id_rol "
                + "WHERE u.id_usuario = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setIdPerfil(rs.getInt("perfil_id_perfil")); // Establece el ID del perfil
                    usuario.setIdRol(rs.getInt("rol_id_rol"));        // Establece el ID del rol
                    usuario.setNombrePerfil(rs.getString("nombre_perfil")); // (Opcional)
                    usuario.setNombreRol(rs.getString("nombre_rol"));   // (Opcional)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public int obtenerIdRolPorNombre(String nombreRol) {
        String sql = "SELECT id_rol FROM rol WHERE nombre_rol = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_rol");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el rol
    }
}
