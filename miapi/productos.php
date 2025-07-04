<?php
header('Content-Type: application/json');

$headers = apache_request_headers();
$authorization = $headers['Authorization'] ?? '';

if ($authorization === 'Bearer abc123') {
    // Devuelve lista de productos
    $productos = [
        ['id' => 1, 'nombre' => 'Producto A', 'precio' => 10],
        ['id' => 2, 'nombre' => 'Producto B', 'precio' => 20]
    ];
    echo json_encode(['productos' => $productos]);
} else {
    echo json_encode(['error' => 'No autorizado']);
}
?>
