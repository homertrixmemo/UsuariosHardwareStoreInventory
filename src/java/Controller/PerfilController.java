package Controller;

import Model.Perfil;
import Model.Cliente;
import Model.Usuario;
import ModelDAO.PerfilDAO;
import ModelDAO.ClienteDAO;
import ModelDAO.UsuarioDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PerfilController", urlPatterns = {"/PerfilController"})
public class PerfilController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PerfilDAO dao = new PerfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "listar"; // Si no hay acción, listar por defecto
        }

        try {
            switch (action) {
                case "nuevo":
                    request.getRequestDispatcher("views/addPerfil.jsp").forward(request, response);
                    break;
                case "listar":
                    listarPerfiles(request, response);
                    break;
                case "editar":
                    editarPerfil(request, response);
                    break;
                case "eliminar":
                    eliminarPerfil(request, response);
                    break;
                case "perfilesAsociados":
                    obtenerPerfilesAsociados(request, response);
                    break;
                default:
                    response.sendRedirect("PerfilController?action=listar");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action != null ? action : "") {
                case "agregar":
                    agregarPerfil(request, response);
                    break;
                case "actualizar":
                    actualizarPerfil(request, response);
                    break;
                case "eliminar":
                    eliminarPerfil(request, response);
                    break;
                default:
                    response.sendRedirect("PerfilController?action=listar");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void listarPerfiles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Perfil> lista = dao.listar(); // Obtener perfiles desde el DAO

            // Depuración
            System.out.println("Perfiles obtenidos en PerfilController: " + lista.size());
            for (Perfil p : lista) {
                System.out.println("Perfil en Controller: " + p.getNombrePerfil());
            }

            request.setAttribute("perfiles", lista);
            request.getRequestDispatcher("views/listarPerfiles.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error en listarPerfiles: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al listar los perfiles.");
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void agregarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
                request.setAttribute("errorMensaje", "Los campos no pueden estar vacíos");
                request.getRequestDispatcher("views/addPerfil.jsp").forward(request, response);
                return;
            }

            Perfil perfil = new Perfil();
            perfil.setNombrePerfil(nombre);
            perfil.setDescripcion(descripcion);

            dao.agregar(perfil);
            response.sendRedirect("PerfilController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void actualizarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");

            if (nombre == null || nombre.trim().isEmpty() || descripcion == null || descripcion.trim().isEmpty()) {
                request.setAttribute("errorMensaje", "Todos los campos son obligatorios");
                editarPerfil(request, response);
                return;
            }

            Perfil perfil = new Perfil();
            perfil.setIdPerfil(id);
            perfil.setNombrePerfil(nombre);
            perfil.setDescripcion(descripcion);

            dao.actualizar(perfil);
            response.sendRedirect("PerfilController?action=listar");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMensaje", "ID de perfil inválido");
            response.sendRedirect("PerfilController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void editarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Perfil perfil = dao.obtener(id).orElse(null);

            if (perfil == null) {
                request.getSession().setAttribute("errorMensaje", "El perfil no existe");
                response.sendRedirect("PerfilController?action=listar");
                return;
            }

            request.setAttribute("perfil", perfil);
            request.getRequestDispatcher("views/editPerfil.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMensaje", "ID de perfil inválido");
            response.sendRedirect("PerfilController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void eliminarPerfil(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ClienteDAO clienteDAO = new ClienteDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            List<Cliente> clientesAsociados = clienteDAO.obtenerClientesPorPerfil(id);
            List<Usuario> usuariosAsociados = usuarioDAO.listarPorPerfil(id);

            // 🔍 Depuración
            System.out.println("Intentando eliminar perfil con ID: " + id);
            System.out.println("Clientes asociados encontrados: " + clientesAsociados.size());
            System.out.println("Usuarios asociados encontrados: " + usuariosAsociados.size());

            if (!clientesAsociados.isEmpty() || !usuariosAsociados.isEmpty()) {
                System.out.println("El perfil tiene asociaciones, mostrando la vista perfilesAsociados.jsp");

                request.setAttribute("clientesAsociados", clientesAsociados);
                request.setAttribute("usuariosAsociados", usuariosAsociados);
                request.setAttribute("idPerfil", id);
                request.getRequestDispatcher("views/perfilesAsociados.jsp").forward(request, response);
            } else {
                boolean eliminado = dao.eliminar(id);
                if (eliminado) {
                    System.out.println("Perfil eliminado correctamente.");
                } else {
                    System.out.println("Error: No se pudo eliminar el perfil.");
                }
                response.sendRedirect("PerfilController?action=listar");
            }
        } catch (NumberFormatException e) {
            System.err.println("Error: ID de perfil inválido.");
            request.getSession().setAttribute("errorMensaje", "ID de perfil inválido");
            response.sendRedirect("PerfilController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }

    private void obtenerPerfilesAsociados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idPerfil = Integer.parseInt(request.getParameter("id"));
            ClienteDAO clienteDAO = new ClienteDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            List<Cliente> clientesAsociados = clienteDAO.listarPorPerfil(idPerfil);
            List<Usuario> usuariosAsociados = usuarioDAO.listarPorPerfil(idPerfil);

            request.setAttribute("clientesAsociados", clientesAsociados);
            request.setAttribute("usuariosAsociados", usuariosAsociados);
            request.setAttribute("idPerfil", idPerfil);

            request.getRequestDispatcher("views/perfilesAsociados.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMensaje", "ID de perfil inválido");
            response.sendRedirect("PerfilController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("PerfilController?action=listar");
        }
    }
}
