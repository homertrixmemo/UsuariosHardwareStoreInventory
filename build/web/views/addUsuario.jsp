<%-- 
    Document   : addUsuario
    Created on : 15 mar 2025, 18:13:19
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Usuario</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos optimizado -->
    </head>
    <body>
        <div class="container">
            <!-- Contenedor Principal -->
            <div class="card-form">
                <div class="form-header">
                    <h3>👤 Agregar Nuevo Usuario</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/UsuarioController" method="post">
                        <input type="hidden" name="action" value="agregar">

                        <!-- Campo Nombre -->
                        <div class="form-group">
                            <label for="nombre">Nombre:</label>
                            <input type="text" id="nombre" name="nombre" class="input-field" maxlength="50" placeholder="Ejemplo: Juan Pérez" required>
                        </div>

                        <!-- Campo Correo Electrónico -->
                        <div class="form-group">
                            <label for="email">Correo Electrónico:</label>
                            <input type="email" id="email" name="email" class="input-field" maxlength="100" placeholder="Ejemplo: usuario@email.com" required>
                        </div>

                        <!-- Campo Contraseña -->
                        <div class="form-group">
                            <label for="password">Contraseña:</label>
                            <input type="password" id="password" name="password" class="input-field" maxlength="20" required>
                        </div>

                        <!-- Campo Perfil -->
                        <div class="form-group">
                            <label for="perfil">Perfil:</label>
                            <select id="perfil" name="perfil" class="input-field" required>
                                <option value="">Seleccione un perfil</option>
                                <c:forEach var="perfil" items="${listaPerfiles}">
                                    <option value="${perfil.idPerfil}">${perfil.nombrePerfil}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Campo Rol -->
                        <div class="form-group">
                            <label for="rol">Rol:</label>
                            <select id="rol" name="rol" class="input-field" required>
                                <option value="">Seleccione un rol</option>
                                <c:forEach var="rol" items="${listaRoles}">
                                    <option value="${rol.idRol}">${rol.nombreRol}</option>
                                </c:forEach>
                            </select>
                        </div>


                        <!-- Botones de Acción -->
                        <div class="button-group">
                            <button type="submit" class="btn-save">💾 Guardar</button>
                            <a href="${pageContext.request.contextPath}/UsuarioController?action=listar" class="btn-cancel">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
