-- Tabla de Usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    nombres VARCHAR(50) NOT NULL,
    apellidop VARCHAR(50) NOT NULL,
    apellidom VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    cuenta_verificada BOOLEAN DEFAULT FALSE
);

-- Tabla de Sesiones
CREATE TABLE sesiones (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    usuario_id INT REFERENCES usuarios (id) ON DELETE CASCADE,
    token VARCHAR(100) NOT NULL
);

-- Tabla de Categorías de Productos
CREATE TABLE categorias_p (
    id SERIAL PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NOT NULL
);

-- Tabla de Productos con Campo "agotado" Añadido
CREATE TABLE productos (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    precio NUMERIC(12,2) NOT NULL,
    stock INT DEFAULT 0,
    categoria_id INT REFERENCES categorias_p (id) NOT NULL,
    agotado BOOLEAN DEFAULT FALSE  -- Campo añadido para indicar si el producto está agotado
);

-- Tabla de Pedidos
CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY NOT NULL,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE NOT NULL,
    fecha_pedido DATE NOT NULL,
    monto_total NUMERIC (12,2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    direccion_envio VARCHAR(255)
);

-- Detalle de Pedido
CREATE TABLE detalle_pedido (
    id SERIAL PRIMARY KEY NOT NULL,
    pedido_id INT REFERENCES pedidos(id) ON DELETE CASCADE NOT NULL,
    producto_id INT REFERENCES productos(id) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL
);

-- Métodos de Pago
CREATE TABLE metodos_pago (
    id SERIAL PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100)
);

-- Pagos
CREATE TABLE pagos (
    id SERIAL PRIMARY KEY NOT NULL,
    pedido_id INT REFERENCES pedidos(id) ON DELETE CASCADE NOT NULL,
    metodo_pago_id INT REFERENCES metodos_pago(id) NOT NULL,
    monto NUMERIC(12,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    estado VARCHAR(50) DEFAULT 'Completado'
);

-- Reviews de Productos
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY NOT NULL,
    usuario_id INT REFERENCES usuarios(id) NOT NULL,
    producto_id INT REFERENCES productos(id) NOT NULL,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    fecha_review DATE NOT NULL
);

-- Tabla de Carritos
CREATE TABLE public.carritos (
    id SERIAL PRIMARY KEY NOT NULL,
    usuario_id  INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    fecha_creacion DATE NOT NULL,
    estado VARCHAR(50) DEFAULT 'Activo', -- 'Activo' o 'Convertido en Pedido'
    subtotal NUMERIC(12, 2) DEFAULT 0,   -- Total sin aplicar descuentos
    descuento NUMERIC(12, 2) DEFAULT 0,  -- Descuento aplicado
    total NUMERIC(12, 2) DEFAULT 0       -- Total final después de aplicar descuentos
);

-- Detalle del Carrito
CREATE TABLE public.detalle_carrito (
    id SERIAL PRIMARY KEY NOT NULL,
    carrito_id   INTEGER NOT NULL REFERENCES carritos(id) ON DELETE CASCADE,
    producto_id  INTEGER NOT NULL REFERENCES productos(id),
    cantidad     INTEGER NOT NULL
);

-- Cupones
CREATE TABLE public.cupones (
    id SERIAL PRIMARY KEY NOT NULL,
    codigo VARCHAR(50) NOT NULL,  -- Código del cupón
    valor_descuento NUMERIC(12, 2) NOT NULL,  -- Valor del descuento
    estado VARCHAR(20) DEFAULT 'Activo'  -- 'Activo' o 'Inactivo'
);

-- Relacionar Cupones con Pedidos
ALTER TABLE public.pedidos
    ADD COLUMN codigo_cupon INTEGER REFERENCES cupones(id);

-- Tabla de Reservas de Inventario (Nueva Tabla para manejar las reservas)
CREATE TABLE reservas_inventario (
    id SERIAL PRIMARY KEY,
    producto_id INT REFERENCES productos(id),
    cantidad INT NOT NULL,
    pedido_id INT REFERENCES pedidos(id),
    fecha_reserva DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'Pendiente'  -- Puede ser 'Pendiente', 'Confirmado' o 'Cancelado'
);

-- Trigger para marcar producto como agotado cuando el stock llegue a 0
CREATE OR REPLACE FUNCTION marcar_agotado()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.stock = 0 THEN
        UPDATE productos SET agotado = TRUE WHERE id = NEW.id;
    ELSE
        UPDATE productos SET agotado = FALSE WHERE id = NEW.id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para ejecutar la función marcar_agotado
CREATE TRIGGER trigger_marcar_agotado
AFTER UPDATE ON productos
FOR EACH ROW
WHEN (NEW.stock = 0 OR NEW.stock > 0)
EXECUTE FUNCTION marcar_agotado();
