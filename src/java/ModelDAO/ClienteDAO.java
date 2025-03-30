package ModelDAO;

import Config.Conexion;
import Model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    // ✅ Constructor vacío
    public ClienteDAO() {
    }

    // Método para listar clientes
    public List<Cliente> listar() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT c.id_cliente, c.tipo_documento, c.numero_documento, "
                + "c.nombres, c.primer_apellido, c.segundo_apellido, c.telefono_movil, "
                + "c.direccion_residencia, c.contacto_emergencia, c.telefono_contacto, "
                + "p.id_perfil, p.nombre_perfil "
                + "FROM cliente c "
                + "LEFT JOIN perfil p ON c.perfil_id_perfil = p.id_perfil"; // <--- Cambio aquí

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("tipo_documento"),
                        rs.getString("numero_documento"),
                        rs.getString("nombres"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getString("telefono_movil"),
                        rs.getString("direccion_residencia"),
                        rs.getString("contacto_emergencia"),
                        rs.getString("telefono_contacto"),
                        rs.getInt("id_perfil") // <--- Cambio aquí
                );
                cliente.setNombrePerfil(rs.getString("nombre_perfil")); // <-- Ahora debería funcionar correctamente
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }

    public List<Cliente> listarPorPerfil(int idPerfil) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE perfil_id_perfil = ?";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idPerfil); // Asignar el parámetro antes de ejecutar la consulta
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setTipoDocumento(rs.getString("tipo_documento"));
                cliente.setNumeroDocumento(rs.getString("numero_documento"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setPrimerApellido(rs.getString("primer_apellido"));
                cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                cliente.setTelefonoMovil(rs.getString("telefono_movil"));
                cliente.setDireccionResidencia(rs.getString("direccion_residencia"));
                cliente.setContactoEmergencia(rs.getString("contacto_emergencia"));
                cliente.setTelefonoContacto(rs.getString("telefono_contacto"));
                cliente.setPerfilIdPerfil(rs.getInt("perfil_id_perfil"));

                lista.add(cliente);
            }
        } catch (Exception e) {
            System.err.println("Error al listar clientes por perfil: " + e.getMessage());
            e.printStackTrace(); // Imprime el error completo para depuración
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }

        return lista;
    }

    public List<Cliente> obtenerClientesPorPerfil(int idPerfil) {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombres, primer_apellido, numero_documento, telefono_movil FROM cliente WHERE perfil_id_perfil = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPerfil);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("id_cliente"),
                            null, // tipoDocumento
                            rs.getString("numero_documento"),
                            rs.getString("nombres"),
                            rs.getString("primer_apellido"),
                            null, // segundoApellido
                            rs.getString("telefono_movil"),
                            null, // direccionResidencia
                            null, // contactoEmergencia
                            null, // telefonoContacto
                            idPerfil // perfilIdPerfil
                    );
                    listaClientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes por perfil: " + e.getMessage());
            e.printStackTrace();
        }
        return listaClientes;
    }

    // Verifica si el perfil existe en la tabla perfil
    public boolean existePerfil(int perfilId) {
        String sql = "SELECT COUNT(*) FROM perfil WHERE id_perfil = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, perfilId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Devuelve true si el perfil existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO cliente (tipo_documento, numero_documento, nombres, primer_apellido, segundo_apellido, telefono_movil, direccion_residencia, contacto_emergencia, telefono_contacto, perfil_id_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getTipoDocumento());
            ps.setString(2, cliente.getNumeroDocumento());
            ps.setString(3, cliente.getNombres());
            ps.setString(4, cliente.getPrimerApellido());
            ps.setString(5, cliente.getSegundoApellido());
            ps.setString(6, cliente.getTelefonoMovil());
            ps.setString(7, cliente.getDireccionResidencia());
            ps.setString(8, cliente.getContactoEmergencia());
            ps.setString(9, cliente.getTelefonoContacto());
            ps.setInt(10, cliente.getPerfilIdPerfil());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Cliente insertado correctamente en la base de datos.");
            } else {
                System.err.println("❌ No se insertó ningún cliente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Error SQL: " + e.getMessage());
        }
    }

    // Método para agregar cliente
    public boolean agregar(Cliente cliente) {
        String sql = "INSERT INTO cliente (tipo_documento, numero_documento, nombres, primer_apellido, segundo_apellido, "
                + "telefono_movil, direccion_residencia, contacto_emergencia, telefono_contacto, perfil_id_perfil) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getTipoDocumento());
            ps.setString(2, cliente.getNumeroDocumento());
            ps.setString(3, cliente.getNombres());
            ps.setString(4, cliente.getPrimerApellido());
            ps.setString(5, cliente.getSegundoApellido());
            ps.setString(6, cliente.getTelefonoMovil());
            ps.setString(7, cliente.getDireccionResidencia());
            ps.setString(8, cliente.getContactoEmergencia());
            ps.setString(9, cliente.getTelefonoContacto());
            ps.setObject(10, cliente.getPerfilIdPerfil(), java.sql.Types.INTEGER);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente obtenerPorId(int idCliente) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setTipoDocumento(rs.getString("tipoDocumento"));
                cliente.setNumeroDocumento(rs.getString("numeroDocumento"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                cliente.setTelefonoMovil(rs.getString("telefonoMovil"));
                cliente.setDireccionResidencia(rs.getString("direccionResidencia"));
                cliente.setContactoEmergencia(rs.getString("contactoEmergencia"));
                cliente.setTelefonoContacto(rs.getString("telefonoContacto"));
                cliente.setPerfilIdPerfil(rs.getInt("perfil_idPerfil"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // O usa un logger
        }

        return cliente;
    }

    // Método para buscar cliente por ID
    public Cliente buscarPorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE id_cliente=?";
        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("tipo_documento"),
                        rs.getString("numero_documento"),
                        rs.getString("nombres"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getString("telefono_movil"),
                        rs.getString("direccion_residencia"),
                        rs.getString("contacto_emergencia"),
                        rs.getString("telefono_contacto"),
                        rs.getInt("perfil_id_perfil")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
        return cliente;
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET tipo_documento=?, numero_documento=?, nombres=?, primer_apellido=?, segundo_apellido=?, telefono_movil=?, direccion_residencia=?, contacto_emergencia=?, telefono_contacto=?, perfil_id_perfil=? WHERE id_cliente=?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getTipoDocumento());
            stmt.setString(2, cliente.getNumeroDocumento());
            stmt.setString(3, cliente.getNombres());
            stmt.setString(4, cliente.getPrimerApellido());
            stmt.setString(5, cliente.getSegundoApellido());
            stmt.setString(6, cliente.getTelefonoMovil());
            stmt.setString(7, cliente.getDireccionResidencia());
            stmt.setString(8, cliente.getContactoEmergencia());
            stmt.setString(9, cliente.getTelefonoContacto());
            stmt.setInt(10, cliente.getPerfilIdPerfil());
            stmt.setInt(11, cliente.getIdCliente()); // Asegúrate de que id_cliente es el último parámetro

            return stmt.executeUpdate() > 0; // Retorna true si se actualizó al menos un registro
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean tieneDependencias(int idCliente) {
        String sql = "SELECT COUNT(*) FROM alguna_tabla WHERE cliente_id = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si hay registros, significa que hay dependencias
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarCliente(int idCliente) {
        if (tieneDependencias(idCliente)) {
            return false; // No se puede eliminar si tiene dependencias
        }
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para cerrar conexión
    private void cerrarConexion() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
