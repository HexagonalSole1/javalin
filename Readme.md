# API Endpoints - Trukea

## Usuarios
- `GET /api/usuarios` - Obtener todos los usuarios
- `GET /api/usuarios/{email}` - Obtener usuario por email

## Categorías
- `GET /api/categorias` - Obtener todas las categorías
- `GET /api/categorias/{id}` - Obtener categoría por ID

## Productos
- `GET /api/productos` - Obtener todos los productos
- `GET /api/productos/{id}` - Obtener producto por ID
- `GET /api/productos/usuario/{usuarioId}` - Obtener productos de un usuario específico
- `GET /api/productos/categoria/{categoriaId}` - Obtener productos de una categoría específica

## Trueques
- `GET /api/trueques` - Obtener todos los trueques
- `GET /api/trueques/{id}` - Obtener trueque por ID
- `GET /api/trueques/estado/{estado}` - Obtener trueques por estado (pendiente, aceptado, rechazado, etc.)

## Calificaciones
- `GET /api/calificaciones` - Obtener todas las calificaciones
- `GET /api/calificaciones/{id}` - Obtener calificación por ID
- `GET /api/calificaciones/usuario/{usuarioId}` - Obtener calificaciones de un usuario específico

## Servidor
- **Puerto:** 8082
- **URL Base:** http://localhost:8082

## Ejemplo de uso
```bash
# Obtener todos los usuarios
curl http://localhost:8082/api/usuarios

# Obtener productos de la categoría 1
curl http://localhost:8082/api/productos/categoria/1

# Obtener trueques pendientes
curl http://localhost:8082/api/trueques/estado/pendiente
```