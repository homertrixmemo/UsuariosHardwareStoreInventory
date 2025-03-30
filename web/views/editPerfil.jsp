<%-- 
    Document   : edit
    Created on : 14 mar 2025, 19:02:48
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Perfil</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos optimizado -->
    </head>
    <body>
        <div class="container">
            <!-- Contenedor Principal -->
            <div class="card-form">
                <div class="form-header">
                    <h3>✏️ Editar Perfil</h3>
                </div>
                <div class="form-body">
                    <form action="${pageContext.request.contextPath}/PerfilController?action=actualizar" method="post">
                        <input type="hidden" name="id_cliente" value="${perfil.idPerfil}">

                        <!-- Campo Nombre -->
                        <div class="form-group">
                            <label for="nombre">Nombre del Perfil:</label>
                            <input type="text" id="nombre" name="nombre" class="input-field"
                                   value="${perfil.nombrePerfil}" maxlength="50"
                                   placeholder="Ejemplo: Administrador" required>
                        </div>

                        <!-- Campo Descripción -->
                        <div class="form-group">
                            <label for="descripcion">Descripción:</label>
                            <textarea id="descripcion" name="descripcion" class="textarea-field" rows="3"
                                      placeholder="Escriba una breve descripción..." required>${perfil.descripcion}</textarea>
                        </div>

                        <!-- Botones de Acción -->
                        <div class="button-group">
                            <button type="submit" class="btn-save" aria-label="Actualizar perfil">💾 Actualizar</button>
                            <a href="${pageContext.request.contextPath}/PerfilController?action=listar"
                               class="btn-cancel" aria-label="Cancelar">❌ Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
