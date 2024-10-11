package com.microservice.reservas_inventario.infraestructure.services;

import com.microservice.reservas_inventario.api.domain.request.ReservaRequest;
import com.microservice.reservas_inventario.api.domain.response.ProductoResponse;
import com.microservice.reservas_inventario.client.ProductoClient;
import com.microservice.reservas_inventario.domain.entities.ReservaInventario;
import com.microservice.reservas_inventario.domain.repositories.ReservaInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final ReservaInventarioRepository reservaInventarioRepository;
    private final ProductoClient productoClient;

    /**
     * Agregar existencias a un producto.
     */
    public ProductoResponse agregarExistencias(Long productoId, int cantidad) {
        // Obtener información del producto desde el microservicio de productos
        ProductoResponse producto = productoClient.obtenerProductoPorId(productoId.intValue());

        // Actualizar el stock
        int nuevoStock = producto.getStock() + cantidad;

        // Llamada para actualizar el producto en el microservicio de productos
        return productoClient.actualizarStockProducto(productoId.intValue(), nuevoStock);
    }

    /**
     * Reducir existencias de un producto.
     */
    public ProductoResponse reducirExistencias(Long productoId, int cantidad) {
        // Obtener información del producto desde el microservicio de productos
        ProductoResponse producto = productoClient.obtenerProductoPorId(productoId.intValue());

        // Validar que hay suficiente stock
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("No hay suficiente stock disponible");
        }

        // Actualizar el stock
        int nuevoStock = producto.getStock() - cantidad;

        // Si el stock es cero, marcar el producto como agotado
        return productoClient.actualizarStockProducto(productoId.intValue(), nuevoStock);
    }

    /**
     * Consultar existencias de un producto por ID.
     */
    public ProductoResponse consultarProducto(Long productoId) {
        return productoClient.obtenerProductoPorId(productoId.intValue());
    }

    /**
     * Consultar productos con bajo inventario.
     */
    public List<ProductoResponse> consultarProductosBajoStock(int limite) {
        return productoClient.obtenerProductosBajoStock(limite);
    }

    /**
     * Reservar inventario para un pedido.
     */
    public ReservaInventario reservarInventario(ReservaRequest request) {
        // Obtener información del producto desde el microservicio de productos
        ProductoResponse producto = productoClient.obtenerProductoPorId(request.getProductoId());

        // Validar si el stock es suficiente para la reserva
        if (producto.getStock() < request.getCantidad()) {
            throw new IllegalArgumentException("No hay suficiente stock para reservar");
        }

        // Crear una nueva reserva de inventario
        ReservaInventario reserva = new ReservaInventario();
        reserva.setProductoId(request.getProductoId());
        reserva.setCantidad(request.getCantidad());
        reserva.setPedidoId(request.getPedidoId());
        reserva.setFechaReserva(LocalDate.now());
        reserva.setEstado("Pendiente");

        // Reducir el stock en el microservicio de productos
        productoClient.actualizarStockProducto(request.getProductoId(), producto.getStock() - request.getCantidad());

        // Guardar la reserva en la base de datos de inventario
        return reservaInventarioRepository.save(reserva);
    }

    /**
     * Liberar una reserva de inventario.
     */
    public int liberarInventario(Long reservaId) {
        // Buscar la reserva por ID
        ReservaInventario reserva = reservaInventarioRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        // Obtener la cantidad de stock reservada
        int cantidadLiberada = reserva.getCantidad();

        // Marcar la reserva como cancelada
        reserva.setEstado("Cancelado");
        reservaInventarioRepository.save(reserva);

        // Devolver la cantidad de stock liberada al producto
        ProductoResponse producto = productoClient.obtenerProductoPorId(reserva.getProductoId());
        productoClient.actualizarStockProducto(reserva.getProductoId(), producto.getStock() + cantidadLiberada);

        // Devolver la cantidad de stock liberada
        return cantidadLiberada;
    }

    /**
     * Marcar un producto como agotado.
     */
    public ProductoResponse marcarProductoComoAgotado(Long productoId) {
        // Obtener información del producto desde el microservicio de productos
        ProductoResponse producto = productoClient.obtenerProductoPorId(productoId.intValue());

        // Marcar el producto como agotado y poner el stock a 0
        return productoClient.actualizarStockProducto(productoId.intValue(), 0);
    }
    /**
     * Listar todas las reservas del inventario.
     */
    public List<ReservaInventario> listarReservas() {
        return reservaInventarioRepository.findAll();
    }

}