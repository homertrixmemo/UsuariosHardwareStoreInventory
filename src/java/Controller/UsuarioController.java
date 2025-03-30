package Controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Usuario;
import ModelDAO.UsuarioDAO;
import Model.Perfil;
import ModelDAO.PerfilDAO;
import Model.Rol;
import ModelDAO.RolDAO;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final PerfilDAO perfilDAO = new PerfilDAO();
    private final RolDAO rolDAO = new RolDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            listarUsuarios(request, response);
            return;
        }

        switch (action) {
            case "listar":
                listarUsuarios(request, response);
                break;
            case "nuevo":
                request.setAttribute("listaPerfiles", perfilDAO.listar());
                request.setAttribute("listaRoles", rolDAO.listar());
                request.getRequestDispatcher("views/addUsuario.jsp").forward(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            default:
                listarUsuarios(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("UsuarioController?action=listar");
            return;
        }

        switch (action) {
            case "agregar":
                agregarUsuario(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;
            case "eliminar":
                eliminarUsuario(request, response); // Llamar a eliminarUsuario desde doPost
                break;
            default:
                response.sendRedirect("UsuarioController?action=listar");
                break;
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("usuarios", usuarioDAO.listar());
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/listarUsuarios.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getParameter("nombre"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setPassword(request.getParameter("password"));

        String perfilStr = request.getParameter("perfil");
        String rolStr = request.getParameter("rol");

        if (perfilStr != null && !perfilStr.isEmpty()) {
            usuario.setIdPerfil(Integer.parseInt(perfilStr));
        } else {
            usuario.setIdPerfil(0);
        }

        if (rolStr != null && !rolStr.isEmpty()) {
            usuario.setIdRol(Integer.parseInt(rolStr));
        } else {
            usuario.setIdRol(0);
        }

        boolean agregado = usuarioDAO.agregar(usuario);

        if (agregado) {
            response.sendRedirect("UsuarioController?action=listar");
        } else {
            request.setAttribute("error", "No se pudo agregar el usuario.");
            request.getRequestDispatcher("addUsuario.jsp").forward(request, response);
        }
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = usuarioDAO.obtenerPorId(id);
            if (usuario == null) {
                response.sendRedirect("UsuarioController?action=listar&error=Usuario no encontrado");
                return;
            }

            request.setAttribute("usuario", usuario);
            request.setAttribute("listaPerfiles", perfilDAO.listar());
            request.setAttribute("listaRoles", rolDAO.listar());
            request.getRequestDispatcher("views/editUsuario.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("UsuarioController?action=listar&error=ID no válido");
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombreUsuario = request.getParameter("nombre");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Obtén los valores de las llaves foráneas usando los nombres correctos del formulario
            int perfilId = Integer.parseInt(request.getParameter("perfil_id"));
            int rolId = Integer.parseInt(request.getParameter("id_rol"));

            Usuario usuarioExistente = usuarioDAO.obtenerPorId(id);
            if (usuarioExistente == null) {
                response.sendRedirect("UsuarioController?action=listar&error=Usuario no encontrado");
                return;
            }

            // Manejar la contraseña (si se proporciona una nueva, encriptarla; si no, mantener la existente)
            String passwordFinal = (password == null || password.isEmpty()) ? usuarioExistente.getPassword() : encriptarSHA256(password);

            usuarioExistente.setNombreUsuario(nombreUsuario);
            usuarioExistente.setEmail(email);
            usuarioExistente.setPassword(passwordFinal);
            usuarioExistente.setIdPerfil(perfilId); // Establece el ID del perfil
            usuarioExistente.setIdRol(rolId);    // Establece el ID del rol

            if (usuarioDAO.actualizar(usuarioExistente)) {
                response.sendRedirect("UsuarioController?action=listar");
            } else {
                request.setAttribute("error", "Error al actualizar el usuario.");
                mostrarFormularioEditar(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("UsuarioController?action=listar&error=ID no válido o campos numéricos incorrectos");
        } catch (Exception e) {
            response.sendRedirect("UsuarioController?action=listar&error=Error al actualizar: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace en el log del servidor para debugging
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            boolean eliminado = usuarioDAO.eliminarUsuario(idUsuario);

            if (eliminado) {
                response.sendRedirect("UsuarioController?action=listar&success=Usuario eliminado con éxito");
            } else {
                response.sendRedirect("UsuarioController?action=listar&error=No se pudo eliminar el usuario");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("UsuarioController?action=listar&error=ID no válido");
        } catch (Exception e) {
            response.sendRedirect("UsuarioController?action=listar&error=Error inesperado al eliminar usuario");
        }
    }

    private String encriptarSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }
}
