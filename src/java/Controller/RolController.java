package Controller;

import Model.Rol;
import Model.Usuario;
import ModelDAO.RolDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RolController", urlPatterns = {"/RolController"})
public class RolController extends HttpServlet {

    private final RolDAO dao = new RolDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "listar":
                listarRoles(request, response);
                break;

            case "nuevo":
                request.getRequestDispatcher("views/addRol.jsp").forward(request, response);
                break;

            case "editar":
                editarRol(request, response);
                break;

            case "confirmarEliminar":
                confirmarEliminarRol(request, response);
                break;

            default:
                response.sendRedirect("RolController?action=listar");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "agregar":
            case "guardar":
                agregarRol(request, response);
                break;

            case "actualizar": // 🔹 ¡Falta este caso!
                actualizarRol(request, response);
                break;

            case "eliminar":
                eliminarRol(request, response);
                break;

            default:
                response.sendRedirect("RolController?action=listar");
        }
    }

    private void listarRoles(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Rol> listaRoles = dao.listar();
        request.setAttribute("roles", listaRoles);
        request.getRequestDispatcher("views/listarRoles.jsp").forward(request, response);
    }

    private void editarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idRol = Integer.parseInt(request.getParameter("id"));
            Rol rol = dao.obtenerPorId(idRol).orElse(null);
            if (rol != null) {
                request.setAttribute("rol", rol);
                request.getRequestDispatcher("views/editRol.jsp").forward(request, response);
            } else {
                response.sendRedirect("RolController?action=listar&error=Rol no encontrado");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("RolController?action=listar&error=ID inválido");
        }
    }

    private void confirmarEliminarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idRol = Integer.parseInt(request.getParameter("id"));
            RolDAO rolDAO = new RolDAO(); // Instancia de RolDAO
            List<Usuario> listaUsuarios = rolDAO.listarUsuariosPorRol(idRol);

            if (listaUsuarios.isEmpty()) {
                // No hay usuarios asociados, proceder con la eliminación
                request.setAttribute("idRol", idRol);
                request.getRequestDispatcher("views/confirmDeleteRol.jsp").forward(request, response);
            } else {
                // Hay usuarios asociados, mostrar la lista en rolesAsociados.jsp
                request.setAttribute("idRol", idRol);
                request.setAttribute("listaUsuarios", listaUsuarios);
                request.getRequestDispatcher("views/rolesAsociados.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("RolController?action=listar&error=ID inválido");
        }
    }

    protected void eliminarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idRol = Integer.parseInt(request.getParameter("id"));
            RolDAO rolDAO = new RolDAO();

            // 🚨 Verificar si el rol tiene usuarios asociados ANTES de eliminarlo
            if (rolDAO.tieneUsuariosAsociados(idRol)) {
                System.out.println("No se puede eliminar el rol " + idRol + " porque tiene usuarios asociados."); // 🛠 Debug
                List<Usuario> listaUsuarios = rolDAO.listarUsuariosPorRol(idRol);
                request.setAttribute("listaUsuarios", listaUsuarios);
                request.setAttribute("idRol", idRol);
                request.setAttribute("error", "No se puede eliminar el rol porque tiene usuarios asociados.");
                request.getRequestDispatcher("views/rolesAsociados.jsp").forward(request, response);
                return;
            }

            // ✅ Si no tiene usuarios asociados, proceder con la eliminación
            boolean eliminado = rolDAO.eliminar(idRol);
            if (eliminado) {
                request.setAttribute("mensaje", "Rol eliminado correctamente.");
            } else {
                request.setAttribute("error", "No se pudo eliminar el rol.");
            }

            // 🚀 Recargar lista de roles antes de redirigir
            List<Rol> listaRoles = rolDAO.listar();
            request.setAttribute("roles", listaRoles);
            request.getRequestDispatcher("views/listarRoles.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("RolController?action=listar&error=ID inválido");
        }
    }

    private void agregarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre_rol"); // Cambiado para coincidir con el formulario
        String descripcion = request.getParameter("descripcion");

        if (nombre == null || nombre.trim().isEmpty()) {
            response.sendRedirect("RolController?action=nuevo&error=El nombre del rol es obligatorio");
            return;
        }

        Rol nuevoRol = new Rol();
        nuevoRol.setNombreRol(nombre);
        nuevoRol.setDescripcion(descripcion); // Asegurar que Rol tenga este atributo

        boolean agregado = dao.agregar(nuevoRol);

        if (agregado) {
            response.sendRedirect("RolController?action=listar&mensaje=Rol agregado correctamente");
        } else {
            response.sendRedirect("RolController?action=nuevo&error=No se pudo agregar el rol");
        }
    }

    private void actualizarRol(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener los datos del formulario
            int idRol = Integer.parseInt(request.getParameter("id_rol"));
            String nombre = request.getParameter("nombre_rol");
            String descripcion = request.getParameter("descripcion");

            // Validar que el nombre del rol no esté vacío
            if (nombre == null || nombre.trim().isEmpty()) {
                response.sendRedirect("RolController?action=editar&id=" + idRol + "&error=El nombre del rol es obligatorio");
                return;
            }

            // Crear objeto Rol con los datos actualizados
            Rol rol = new Rol(idRol, nombre, descripcion);

            // Llamar al DAO para actualizar el rol en la base de datos
            boolean actualizado = dao.actualizar(rol);

            // Mensaje de éxito o error
            if (actualizado) {
                response.sendRedirect("RolController?action=listar&mensaje=Rol actualizado correctamente");
            } else {
                response.sendRedirect("RolController?action=editar&id=" + idRol + "&error=No se pudo actualizar el rol");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("RolController?action=listar&error=ID de rol inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("RolController?action=listar&error=Error al actualizar el rol");
        }
    }

}
