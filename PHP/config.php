<?php
$hostname = "localhost";
$username = "root";
$password = ""; 
$database = "asistencia"; 

// Crear conexión
$conexion = new mysqli($hostname, $username, $password, $database);
if ($conexion->connect_error) {
    echo "El sitio web esta experimentando problemas";
}
?>
