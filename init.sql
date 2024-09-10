CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    nombres VARCHAR(50) NOT NULL,
    apellidop VARCHAR(50) NOT NULL,
    apellidom VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    cuenta_verificada boolean DEFAULT FALSE
);

CREATE TABLE sesiones (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    usuario_id INT REFERENCES usuarios (id) ON DELETE CASCADE,
    token VARCHAR(100) NOT NULL
);

CREATE TABLE categorias_p (
    id SERIAL PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NOT NULL
);

CREATE TABLE productos (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    precio NUMERIC(12,2) NOT NULL,
    stock INT DEFAULT 0,
    categoria_id INT REFERENCES categorias_p (id) NOT NULL
);

CREATE TABLE pedidos (
    id SERIAL PRIMARY KEY NOT NULL,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE NOT NULL,
    fecha_pedido DATE NOT NULL,
    monto_total NUMERIC (12,2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    direccion_envio VARCHAR(255)
);

CREATE TABLE detalle_pedido (
    id SERIAL PRIMARY KEY NOT NULL,
    pedido_id INT REFERENCES pedidos(id) ON DELETE CASCADE NOT NULL,
    producto_id INT REFERENCES productos(id) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(12,2) NOT NULL,
    subtotal NUMERIC(12,2) NOT NULL
);

CREATE TABLE metodos_pago (
    id SERIAL PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE pagos (
    id SERIAL PRIMARY KEY NOT NULL,
    pedido_id INT REFERENCES pedidos(id) ON DELETE CASCADE NOT NULL,
    metodo_pago_id INT REFERENCES metodos_pago(id) NOT NULL,
    monto NUMERIC(12,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    estado VARCHAR(50) DEFAULT 'Completado'
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY NOT NULL,
    usuario_id INT REFERENCES usuarios(id) NOT NULL,
    producto_id INT REFERENCES productos(id) NOT NULL,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    fecha_review DATE NOT NULL
);