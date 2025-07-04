<?php
header('Content-Type: application/json');

// Simulación de datos recibidos por POST
$usuario = $_POST['usuario'] ?? '';
$contrasena = $_POST['contrasena'] ?? '';

if ($usuario === 'admin' && $contrasena === '1234') {
    // Respuesta JSON correcta
    echo json_encode(['token' => 'abc123']);
} else {
    // Respuesta de error
    echo json_encode(['error' => 'Credenciales inválidas']);
}
?>
