<%-- 
    Document   : index
    Created on : 14 mar 2025, 18:46:28
    Author     : Juan
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sistema de Gestión de Usuarios</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <!-- Barra de navegación -->
        <nav class="navbar">
            <div class="contenedor">
                <a class="navbar-brand" href="#">Gestión de Usuarios</a>
                <ul class="nav-menu">
                    <li><a href="ClienteController?action=listar">Clientes</a></li>
                    <li><a href="UsuarioController?action=listar">Usuarios</a></li>
                    <li><a href="PerfilController?action=listar">Perfiles</a></li>
                </ul>
            </div>
        </nav>

        <!-- Contenido principal -->
        <div class="contenedor">
            <h1>Bienvenido al Sistema de Gestión de Usuarios</h1>
            <div class="card-contenedor">
                <div class="card">
                    <h3>Gestión de Clientes</h3>
                    <p>Registrar, actualizar y eliminar clientes.</p>
                    <a href="ClienteController?action=listar" class="boton boton-primario">Acceder</a>
                </div>
                <div class="card">
                    <h3>Gestión de Usuarios</h3>
                    <p>Administrar los usuarios del sistema.</p>
                    <a href="UsuarioController?action=listar" class="boton boton-primario">Acceder</a>
                </div>
                <div class="card">
                    <h3>Gestión de Perfiles</h3>
                    <p>Definir roles y permisos de usuarios.</p>
                    <a href="PerfilController?action=listar" class="boton boton-primario">Acceder</a>
                </div>
            </div>
        </div>

        <!-- Pie de página -->
        <footer>
            <h3>&copy; 2025 - Servicio Nacional de Aprendizaje SENA - Módulos de software codificados y probados</h3>            
            <p><strong>Proyecto:</strong> GA7-220501096-AA2-EV02</p>
            <p><strong>Aprendices:</strong></p>    
            <p>Joaquín H Jiménez Rosas - Ficha: 2879723</p>
            <p>Juan David Gallego López - Ficha: 2879723</p>
            <p>David Ricardo Graffe Rodríguez - Ficha: 2879724</p>
        </footer>

    </body>
</html>
