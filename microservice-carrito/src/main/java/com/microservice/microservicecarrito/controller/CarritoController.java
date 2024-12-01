package com.microservice.microservicecarrito.controller;

import com.microservice.microservicecarrito.client.ProductoCliente;
import com.microservice.microservicecarrito.client.UsuarioCliente;
import com.microservice.microservicecarrito.client.response.ProductoResponse;
import com.microservice.microservicecarrito.model.Carrito;
import com.microservice.microservicecarrito.model.DetalleCarrito;
import com.microservice.microservicecarrito.model.Producto;
import com.microservice.microservicecarrito.model.Usuario;
import com.microservice.microservicecarrito.model.dto.*;
import com.microservice.microservicecarrito.service.iservice.ICarritoService;
import com.microservice.microservicecarrito.service.iservice.IDetalleCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:4200")
public class CarritoController {

    private final ICarritoService carritoService;
    private final ProductoCliente productoCliente;
    private final UsuarioCliente usuarioCliente;
    private final IDetalleCarritoService detalleCarritoService;

    Map<String, Object> response = new HashMap<>();

    //Consultar contenido de carrito
    @GetMapping("/consultar/{id}")
    public ResponseEntity<?> consultarCarrito(@PathVariable(name = "id") Integer id) {
        response.clear();
        Carrito carrito = carritoService.findByID(id);

        if (carrito == null) {
            response.put("mensaje", "Carrito no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Usuario usuario = carrito.getUsuario();
        CarritoResponseDto carritoResponseDto = createCarritoResponse(carrito, usuario);
        List<DetalleCarrito> detalleCarritoList = detalleCarritoService.obtenerDetalleCarritoPorIdCarrito(id);
        response.put("carrito", carritoResponseDto);
        response.put("detalle_carrito", detalleCarritoList);//Buscar detalles por id carrito
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Primero creamos el carrito
    @PostMapping("/crear")
    public ResponseEntity<?> crearCarrito(@RequestBody UsuarioIdDto dto) {
        response.clear();
        //Creamos el carrito
        Carrito carrito = new Carrito();
        //Buscamos al usuario
        Usuario usuario = usuarioCliente.findById(Integer.valueOf(dto.getUsuario_id())).getBody();
        if (usuario == null) {
            response.put("mensaje", "Usuario no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        carrito.setUsuario(usuario);
        carritoService.save(carrito);
        response.put("mensaje", "Carrito creado");
        //Creamos el response
        CarritoResponseDto carritoResponseDto = createCarritoResponse(carrito, usuario);
        response.put("carrito", carritoResponseDto);//Agregar un carritoResponseDto
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Agregamos producto al carrito
    @PostMapping("/agregarProducto")
    public ResponseEntity<?> agregarProducto(@RequestBody DetalleCarritoRequestDto dto) {
        response.clear();
        //Buscamos las entidades
        Carrito carrito = carritoService.findByID(Integer.valueOf(dto.getId_carrito()));
        ProductoResponse producto = productoCliente.obtenerProducto(Long.valueOf(dto.getId_producto())).getBody();
        if (carrito == null || producto == null) {
            response.put("mensaje", "Carrito o producto no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        //Creamos el producto
        Producto productoEntity = new Producto();
        productoEntity.setId(Math.toIntExact(producto.getId()));
        productoEntity.setNombre(producto.getNombre());
        productoEntity.setPrecio(BigDecimal.valueOf(producto.getPrecio()));
        productoEntity.setStock(producto.getStock());
        productoEntity.setDescripcion(producto.getDescripcion());
        //Verificamos si hay stock y que la cantidad sea menor o igual al stock
        if (productoEntity.getStock() == 0 || Integer.parseInt(dto.getCantidad()) > productoEntity.getStock()) {
            response.put("mensaje", "No hay stock suficiente");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        //Creamos el detalle carrito
        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setProducto(productoEntity);
        detalleCarrito.setCarrito(carrito);
        detalleCarrito.setCantidad(Integer.valueOf(dto.getCantidad()));
        //Guardamos el detalle carrito
        DetalleCarrito detalleCarrito1 = detalleCarritoService.agregarProducto(detalleCarrito);
        response.put("mensaje", "Producto agregado al carrito");
        response.put("detalle_carrito", detalleCarrito1);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Actualizar cantidad de producto
    @PutMapping("/actualizarCantidad/{id}")
    public ResponseEntity<?> actualizarCantidad(@PathVariable(name = "id") Integer id, @RequestBody CambiarCantidadProductoDto dto) {
        response.clear();
        //Guardamos el detalle del carrito
        DetalleCarrito detalleCarrito1 = detalleCarritoService.actualizarCantidadProducto(id, Integer.valueOf(dto.getCantidad()));
        response.put("mensaje", "Cantidad actualizada");
        response.put("detalle_carrito", detalleCarrito1);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Quitamos producto al carrito
    @PostMapping("/quitarProducto")
    public ResponseEntity<?> quitarProducto(@RequestBody DetalleCarritoRequestIdDto dto) {
        response.clear();
        //Buscamos el detalle del carrito
        DetalleCarrito detalleCarrito = detalleCarritoService.obtenerDetalleCarritoPorId(Integer.valueOf(dto.getId_detalle_carrito()));
        if (detalleCarrito == null) {
            response.put("mensaje", "Detalle del carrito no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        //Borramos el producto
        detalleCarritoService.borrarProducto(detalleCarrito);
        response.put("mensaje", "Producto eliminado del carrito");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //Aplicamos descuento al carrito
    @PostMapping("/aplicarDescuento/{id}")
    public ResponseEntity<?> aplicarDescuento(@PathVariable(name = "id") Integer id, @RequestBody CuponResponseDto dto) {
        response.clear();
        //Aplicamos el descuento
        Carrito carrito = carritoService.aplicarCupon(id, dto.getCodigo());
        if (carrito == null) {
            response.put("mensaje", "Cupon no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        response.put("mensaje", "Descuento aplicado");
        response.put("carrito", carrito);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Borrar todos los productos del carrito
    @DeleteMapping("/vaciarCarrito/{id}")
    public ResponseEntity<?> vaciarCarrito(@PathVariable(name = "id") Integer id) {
        response.clear();
        //Vaciamos el carrito
        detalleCarritoService.vaciarCarrito(id);
        response.put("mensaje", "Carrito vaciado");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> borrarCarrito(@PathVariable(name = "id") Integer id) {
        response.clear();
        Carrito carrito = carritoService.findByID(id);
        if (carrito == null) {
            response.put("mensaje", "Carrito no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        carritoService.borrarCarrito(carrito);
        response.put("mensaje", "Carrito eliminado");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Guardar carrito para despues
    @GetMapping("/guardarCarrito/{id}")
    public ResponseEntity<?> guardarCarritoParaDepues(@PathVariable(name = "id") Integer id) {
        response.clear();
        Carrito carrito = carritoService.guardarCarrito(id);
        if (carrito == null) {
            response.put("mensaje", "Carrito no encontrado");
            response.put("status", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        carritoService.guardarCarrito(id);
        response.put("mensaje", "Carrito guardado");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static CarritoResponseDto createCarritoResponse(Carrito carrito, Usuario usuario) {
        CarritoResponseDto carritoResponseDto = new CarritoResponseDto();
        carritoResponseDto.setId_carrito(String.valueOf(carrito.getId()));
        carritoResponseDto.setUsuario(usuario.getNombres());
        carritoResponseDto.setDescuento(carrito.getDescuento());
        carritoResponseDto.setEstado(carrito.getEstado());
        carritoResponseDto.setFechaCreacion(carrito.getFechaCreacion());
        carritoResponseDto.setSubtotal(carrito.getSubtotal());
        carritoResponseDto.setTotal(carrito.getTotal());
        return carritoResponseDto;
    }

}
