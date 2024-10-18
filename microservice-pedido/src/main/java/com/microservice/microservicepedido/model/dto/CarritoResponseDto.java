package com.microservice.microservicepedido.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleCarrito {
        private int id;
        private Producto producto;
        private int cantidad;

        @Data
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
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
    @AllArgsConstructor
    @NoArgsConstructor
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