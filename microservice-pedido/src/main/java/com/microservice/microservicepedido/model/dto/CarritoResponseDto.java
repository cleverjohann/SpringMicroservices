package com.microservice.microservicepedido.model.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class CarritoResponseDto {
    private List<DetalleCarrito> detalle_carrito;
    private Carrito carrito;
    private String status;

    @Data
    @ToString
    public static class DetalleCarrito {
        private int id;
        private Producto producto;
        private int cantidad;

        @Data
        @ToString
        public static class Producto {
            private int id;
            private String nombre;
            private String descripcion;
            private double precio;
            private int stock;
        }
    }

    @Data
    @ToString
    public static class Carrito {
        private String id_carrito;
        private String usuario;
        private String fechaCreacion;
        private String estado;
        private double subtotal;
        private double descuento;
        private double total;
    }
}