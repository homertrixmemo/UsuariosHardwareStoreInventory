package Controller;

import Model.Cliente;
import Model.Perfil;
import ModelDAO.ClienteDAO;
import ModelDAO.PerfilDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteController", urlPatterns = {"/ClienteController"})
public class ClienteController extends HttpServlet {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private PerfilDAO perfilDAO = new PerfilDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }

        switch (action) {
            case "listar":
                listarClientes(request, response);
                break;
            case "nuevo":
                mostrarFormularioAgregar(request, response);
                break;
            case "editar":
                mostrarFormularioEdicion(request, response);
                break;
            default:
                listarClientes(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("🔄 Acción recibida en doPost(): " + action);

        if (action == null) {
            response.sendRedirect("ClienteController?action=listar");
            return;
        }

        switch (action) {
            case "guardar":
                guardarCliente(request, response);
                break;
            case "actualizar":
                actualizarCliente(request, response);
                break;
            case "eliminar":
                eliminarCliente(request, response);
                break;
            default:
                response.sendRedirect("ClienteController?action=listar");
                break;
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> listaClientes = clienteDAO.listar();
        System.out.println("Clientes en request: " + listaClientes.size()); // <--- Depuración
        request.setAttribute("listaClientes", listaClientes);
        request.getRequestDispatcher("views/listarClientes.jsp").forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Perfil> listaPerfiles = perfilDAO.listar(); // Obtener perfiles desde la BD
        request.setAttribute("listaPerfiles", listaPerfiles);
        request.getRequestDispatcher("views/addCliente.jsp").forward(request, response);
    }

    private void mostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener ID del cliente desde la URL
            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect("ClienteController?action=listar&error=ID no válido");
                return;
            }

            int idCliente = Integer.parseInt(idParam);
            Cliente cliente = clienteDAO.buscarPorId(idCliente);

            if (cliente == null) {
                response.sendRedirect("ClienteController?action=listar&error=Cliente no encontrado");
                return;
            }

            // Obtener la lista de perfiles
            List<Perfil> listaPerfiles = perfilDAO.listar();

            // Enviar atributos al JSP
            request.setAttribute("cliente", cliente);
            request.setAttribute("listaPerfiles", listaPerfiles);

            // Redirigir a la vista de edición
            request.getRequestDispatcher("views/editCliente.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("ClienteController?action=listar&error=ID inválido");
        }
    }

    private void guardarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("✅ Método guardarCliente() ejecutado");

            // Obtener parámetros del formulario
            String tipoDocumento = request.getParameter("tipoDocumento");
            String numeroDocumento = request.getParameter("numeroDocumento");
            String nombres = request.getParameter("nombres");
            String primerApellido = request.getParameter("primerApellido");
            String segundoApellido = request.getParameter("segundoApellido");
            String telefonoMovil = request.getParameter("telefonoMovil");
            String direccionResidencia = request.getParameter("direccionResidencia");
            String contactoEmergencia = request.getParameter("contactoEmergencia");
            String telefonoContacto = request.getParameter("telefonoContacto");
            String perfilStr = request.getParameter("perfil"); // Obtener como String
            int perfilId = 0; // Inicializar

            System.out.println("📌 Datos recibidos (antes de la conversión): "
                    + "tipoDocumento=" + tipoDocumento + ", "
                    + "numeroDocumento=" + numeroDocumento + ", "
                    + "nombres=" + nombres + ", "
                    + "perfil=" + perfilStr); // Imprimir el valor del perfil

            if (perfilStr != null && !perfilStr.isEmpty()) {
                perfilId = Integer.parseInt(perfilStr); // Convertir si no es nulo o vacío
            } else {
                System.err.println("⚠️ El valor del perfil es nulo o vacío.");
                request.setAttribute("errorMensaje", "Error: El perfil es obligatorio.");
                request.getRequestDispatcher("views/addCliente.jsp").forward(request, response);
                return; // Salir del método si el perfil es inválido
            }

            System.out.println("📌 Datos recibidos: " + tipoDocumento + ", " + numeroDocumento + ", " + nombres + ", perfilId=" + perfilId);

            // Crear objeto Cliente
            Cliente cliente = new Cliente(tipoDocumento, numeroDocumento, nombres, primerApellido,
                    segundoApellido, telefonoMovil, direccionResidencia,
                    contactoEmergencia, telefonoContacto, perfilId);

            // Guardar en la base de datos
            clienteDAO.guardar(cliente);
            System.out.println("✅ Cliente guardado en la base de datos");

            // Redirigir a la lista de clientes
            response.sendRedirect("ClienteController?action=listar");

        } catch (NumberFormatException e) {
            System.err.println("⚠️ Error en los datos (NumberFormatException): " + e.getMessage());
            e.printStackTrace(); // Imprimir el stack trace completo
            request.setAttribute("errorMensaje", "Error en los datos. Verifica que el perfil sea un número válido.");
            request.getRequestDispatcher("views/addCliente.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Error al guardar cliente: " + e.getMessage());
            request.setAttribute("errorMensaje", "Error al guardar el cliente: " + e.getMessage()); // Enviar mensaje a la vista
            request.getRequestDispatcher("views/addCliente.jsp").forward(request, response); // Regresar al formulario
        }
    }

    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String tipoDocumento = request.getParameter("tipoDocumento");
            String numeroDocumento = request.getParameter("numeroDocumento");
            String nombres = request.getParameter("nombres");
            String primerApellido = request.getParameter("primerApellido");
            String segundoApellido = request.getParameter("segundoApellido");
            String telefonoMovil = request.getParameter("telefonoMovil");
            String direccionResidencia = request.getParameter("direccionResidencia");
            String contactoEmergencia = request.getParameter("contactoEmergencia");
            String telefonoContacto = request.getParameter("telefonoContacto");
            int perfilIdPerfil = Integer.parseInt(request.getParameter("perfilIdPerfil"));

            Cliente cliente = new Cliente(idCliente, tipoDocumento, numeroDocumento, nombres,
                    primerApellido, segundoApellido, telefonoMovil,
                    direccionResidencia, contactoEmergencia, telefonoContacto,
                    perfilIdPerfil);

            boolean actualizado = clienteDAO.actualizar(cliente);

            if (actualizado) {
                response.sendRedirect("ClienteController?action=listar&mensaje=Cliente actualizado con éxito");
            } else {
                response.sendRedirect("ClienteController?action=listar&error=No se pudo actualizar el cliente");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMensaje", "ID de cliente inválido");
            response.sendRedirect("ClienteController?action=listar");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ClienteController?action=listar");
        }
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));

            if (clienteDAO.tieneDependencias(idCliente)) {
                response.sendRedirect("ClienteController?action=listar&error=No se puede eliminar el cliente porque tiene registros asociados.");
                return;
            }

            boolean eliminado = clienteDAO.eliminarCliente(idCliente);

            if (eliminado) {
                response.sendRedirect("ClienteController?action=listar&success=Cliente eliminado con éxito");
            } else {
                response.sendRedirect("ClienteController?action=listar&error=No se pudo eliminar el cliente");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("ClienteController?action=listar&error=ID de cliente inválido");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ClienteController?action=listar&error=Error inesperado al eliminar cliente");
        }
    }
}
