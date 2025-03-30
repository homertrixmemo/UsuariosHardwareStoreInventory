package ModelDAO;

import Config.Conexion;
import Interfaz.CRUDPerfil;
import Model.Perfil;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerfilDAO implements CRUDPerfil {

    private final Conexion cn = new Conexion();

    public List<Perfil> listar() {
        List<Perfil> lista = new ArrayList<>();
        String sql = "SELECT * FROM perfil";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Perfil p = new Perfil();
                p.setIdPerfil(rs.getInt("id_perfil"));
                p.setNombrePerfil(rs.getString("nombre_perfil"));
                p.setDescripcion(rs.getString("descripcion"));
                lista.add(p);
            }
            System.out.println("Perfiles encontrados: " + lista.size());
        } catch (SQLException e) {
            System.err.println("Error al listar perfiles: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Optional<Perfil> obtener(int id) {
        Perfil perfil = null;
        String sql = "SELECT * FROM perfil WHERE id_perfil = ?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                perfil = new Perfil();
                perfil.setIdPerfil(rs.getInt("id_perfil"));
                perfil.setNombrePerfil(rs.getString("nombre_perfil"));
                perfil.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener perfil: " + e.getMessage());
        }
        return Optional.ofNullable(perfil);
    }

    @Override
    public boolean agregar(Perfil perfil) {
        String sqlVerificar = "SELECT COUNT(*) FROM perfil WHERE nombre_perfil = ?";
        String sqlInsertar = "INSERT INTO perfil (nombre_perfil, descripcion) VALUES (?, ?)";

        try (Connection con = cn.getConnection(); PreparedStatement psVerificar = con.prepareStatement(sqlVerificar)) {

            psVerificar.setString(1, perfil.getNombrePerfil());
            ResultSet rs = psVerificar.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.err.println("Error: Ya existe un perfil con el mismo nombre.");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInsertar)) {
                ps.setString(1, perfil.getNombrePerfil());
                ps.setString(2, perfil.getDescripcion());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al agregar perfil: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Perfil perfil) {
        String sql = "UPDATE perfil SET nombre_perfil=?, descripcion=? WHERE id_perfil=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, perfil.getNombrePerfil());
            ps.setString(2, perfil.getDescripcion());
            ps.setInt(3, perfil.getIdPerfil());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar perfil: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        if (tieneClientesAsociados(id)) {
            System.err.println("No se puede eliminar el perfil porque tiene clientes asociados.");
            return false;
        }

        String sql = "DELETE FROM perfil WHERE id_perfil=?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar perfil: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneClientesAsociados(int idPerfil) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE perfil_id_perfil = ?";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPerfil);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar clientes asociados: " + e.getMessage());
        }
        return false;
    }

    public List<Usuario> obtenerUsuariosPorPerfil(int idPerfil) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE perfil_id_perfil = ?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPerfil);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario")); // Corregido
                usuario.setEmail(rs.getString("email"));
                usuario.setIdPerfil(rs.getInt("perfil_id_perfil"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por perfil: " + e.getMessage());
            e.printStackTrace();
        }

        return listaUsuarios;
    }
}
