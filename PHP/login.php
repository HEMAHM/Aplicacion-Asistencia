<?php
include 'config.php';

$usu = $_POST['usuario'];
$con = $_POST['contrasena'];

$sentencia = $conexion->prepare("SELECT * FROM usuarios WHERE usuario=? AND contrasena=?");
$sentencia->bind_param('ss', $usu, $con);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
    echo json_encode($fila, JSON_UNESCAPED_UNICODE);
} else {
    // Si el usuario o contraseña no es correcto, devolver un JSON con un error
    echo json_encode(array("error" => "Usuario o contraseña incorrectos"), JSON_UNESCAPED_UNICODE);
}
$sentencia->close();
$conexion->close();
?>
