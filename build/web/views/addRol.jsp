<%-- 
    Document   : addRol
    Created on : 14 mar 2025, 23:20:27
    Author     : Juan
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Agregar Nuevo Rol</title>
        <link rel="stylesheet" href="css/style.css"> <!-- Archivo de estilos centralizado -->
    </head>
    <body>
        <div class="contenedor">
            <div class="card-form">
                <h2 class="text-center">➕ Agregar Nuevo Rol</h2>
                <form action="RolController?action=agregar" method="post">
                    <!-- Campo Nombre -->
                    <label for="nombre_rol">Nombre del Rol:</label>
                    <input type="text" id="nombre_rol" name="nombre_rol" class="input-field" 
                           maxlength="50" placeholder="Ejemplo: Administrador" required>

                    <!-- Campo Descripción -->
                    <label for="descripcion">Descripción:</label>
                    <textarea id="descripcion" name="descripcion" class="textarea-field" rows="3" 
                              maxlength="255" placeholder="Escriba una breve descripción..." required></textarea>

                    <!-- Botones de Acción -->
                    <div class="button-group">
                        <a href="RolController?action=listar" class="btn-cancel">❌ Regresar</a>
                        <button type="submit" class="btn-save">💾 Guardar</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
