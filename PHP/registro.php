<?php
include 'config.php';

// Verifica si la conexión está abierta
if (!$conexion) {
    die("Error en la conexión: " . mysqli_connect_error());
}

$nombre = isset($_POST['nombre']) ? $_POST['nombre'] : null;
$apellidop = isset($_POST['apellidop']) ? $_POST['apellidop'] : null;
$apellidom = isset($_POST['apellidom']) ? $_POST['apellidom'] : null;
$usuario = isset($_POST['usuario']) ? $_POST['usuario'] : null;
$contrasena = isset($_POST['contrasena']) ? $_POST['contrasena'] : null;

$consulta = "INSERT INTO usuarios (nombre, apellidop, apellidom, usuario, contrasena) VALUES ('".$nombre."','".$apellidop."','".$apellidom."','".$usuario."','".$contrasena."')";
$resultado = mysqli_query($conexion, $consulta);

if (!$resultado) {
    // Manejar el error
    die('Error en la consulta: ' . mysqli_error($conexion));
}

// Cierra la conexión después de finalizar las operaciones de la base de datos
mysqli_close($conexion);
?>