<?php
include 'config.php';

// Verifica si la conexión está abierta
if (!$conexion) {
    die("Error en la conexión: " . mysqli_connect_error());
}

// Recuperar los datos enviados desde Android
$nombre = isset($_POST['nombre']) ? $_POST['nombre'] : null;
$apellidop = isset($_POST['apellidop']) ? $_POST['apellidop'] : null;
$apellidom = isset($_POST['apellidom']) ? $_POST['apellidom'] : null;
$fecha = isset($_POST['fecha']) ? $_POST['fecha'] : null;
$hora = isset($_POST['hora']) ? $_POST['hora'] : null;

// Insertar los datos en la tabla 'asis'
$consulta = "INSERT INTO reg_asistencia (nombre, apellidop, apellidom, fecha, hora, mensaje) VALUES ('$nombre', '$apellidop', '$apellidom', '$fecha', '$hora', 'Sí asistió')";
$resultado = mysqli_query($conexion, $consulta);

if (!$resultado) {
    // Si hay un error, devolver una respuesta con el mensaje de error
    echo json_encode(["resultado" => "error", "mensaje" => mysqli_error($conexion)]);
} else {
    // Si la inserción fue exitosa, devolver una respuesta con el resultado
    echo json_encode(["resultado" => "exito"]);
}

// Cierra la conexión
mysqli_close($conexion);
?>
