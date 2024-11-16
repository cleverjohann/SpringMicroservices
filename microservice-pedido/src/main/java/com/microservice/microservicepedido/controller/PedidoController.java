package com.microservice.microservicepedido.controller;

import com.microservice.microservicepedido.client.CarritoCliente;
import com.microservice.microservicepedido.client.CorreoCliente;
import com.microservice.microservicepedido.client.UsuarioCliente;
import com.microservice.microservicepedido.client.response.CorreoClientDto;
import com.microservice.microservicepedido.model.*;
import com.microservice.microservicepedido.model.dto.CarritoResponseDto;
import com.microservice.microservicepedido.model.dto.PedidoDto;
import com.microservice.microservicepedido.model.dto.PedidoResponseDto;
import com.microservice.microservicepedido.service.ICuponService;
import com.microservice.microservicepedido.service.IMetodoPagoService;
import com.microservice.microservicepedido.service.IPagoService;
import com.microservice.microservicepedido.service.IPedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
@Log
public class PedidoController {

    private final IPedidoService pedidoService;
    private final UsuarioCliente usuarioCliente;
    private final ICuponService cuponService;
    private final CarritoCliente carritoCliente;
    private final CorreoCliente correoCliente;
    private final IMetodoPagoService metodoPagoService;
    private final IPagoService pagoService;

    Map<String, Object> response = new HashMap<>();

    //Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        response.clear();
        Pedido entity = pedidoService.findById(id);
        PedidoResponseDto responseDto = new PedidoResponseDto();
        responseDto.setId(entity.getId().toString());
        responseDto.setUsuario(entity.getUsuario().getNombres());
        responseDto.setFechaPedido(entity.getFechaPedido().toString());
        responseDto.setMontoTotal(entity.getMontoTotal().toString());
        responseDto.setEstado(entity.getEstado());
        responseDto.setDireccionEnvio(entity.getDireccionEnvio());
        responseDto.setCodigoCupon(entity.getCodigoCupon().getCodigo());
        response.put("data", responseDto);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Listar todos los pedidos por usuario
    @GetMapping("/listar/id")
    public ResponseEntity<?> listarPorId(@RequestParam Integer id) {
        response.clear();
        Usuario usuario = comprobarUsuario(id.toString());
        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        List<Pedido> allByUsuarioId = pedidoService.findAllByUsuarioId(usuario);
        response.put("pedidos", allByUsuarioId);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Consultar estado pedido
    @GetMapping("/estado/{id}")
    public ResponseEntity<?> consultarEstadoPedido(@PathVariable Integer id) {
        response.clear();
        String estado = pedidoService.consultarEstadoPedido(id);
        response.put("estado", estado);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Cambiar estado pedido
    @PostMapping("/estado/{id}")
    public ResponseEntity<?> cambiarEstadoPedido(@PathVariable Integer id, @RequestParam String estado) {
        response.clear();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            response.put("message", "Pedido no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        pedido.setEstado(estado);
        response.put("message", "Estado actualizado");
        response.put("estado", pedidoService.save(pedido).getEstado());
        response.put("status", HttpStatus.OK);
        //Una vez se confirma el pedido, se envía la notificación por correo al usuario
        correoCliente.enviarCorreo(new CorreoClientDto(pedido.getUsuario().getEmail(), "El estado de su pedido ha sido cambiado.", "El estado de su pedido ha sido cambiado a: " + estado));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Integer id) {
        response.clear();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            response.put("message", "Pedido no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        pedidoService.delete(pedido);
        response.put("message", "Pedido eliminado");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Repetir pedido anterior
    @PostMapping("/repetir/{id}")
    public ResponseEntity<?> repetirPedido(@PathVariable Integer id) {
        response.clear();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            response.put("message", "Pedido no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Pedido nuevoPedido = Pedido.builder()
                .montoTotal(pedido.getMontoTotal())
                .direccionEnvio(pedido.getDireccionEnvio())
                .usuario(pedido.getUsuario())
                .codigoCupon(pedido.getCodigoCupon())
                .idCarrito(pedido.getIdCarrito())
                .build();
        response.put("message", "Pedido repetido");
        response.put("data", pedidoService.save(nuevoPedido));
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Ver pedidos en proceso
    @GetMapping("/pendiente/{id}")
    public ResponseEntity<?> pedidosEnProceso(@PathVariable Integer id) {
        response.clear();
        List<Pedido> pedidos = pedidoService.findAllByEstadoAndIdUsuario("PENDIENTE", id);
        response.put("pedidos", pedidos);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Confirmar recepcion del pedido
    @PostMapping("/confirmar/{id}")
    public ResponseEntity<?> confirmarRecepcionPedido(@PathVariable Integer id) {
        response.clear();
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            response.put("message", "Pedido no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        pedido.setEstado("ENTREGADO");
        response.put("message", "Pedido entregado");
        response.put("data", pedidoService.save(pedido));
        response.put("status", HttpStatus.OK);
        //Enviamos correo de confirmación
        correoCliente.enviarCorreo(new CorreoClientDto(pedido.getUsuario().getEmail(), "Pedido entregado", "Su pedido ha sido entregado."));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Comprobar usuario
    private Usuario comprobarUsuario(String id_usuario) {
        ResponseEntity<Usuario> usuario = usuarioCliente.findById(id_usuario);

        //Si no existe el usuario retornamos un error
        if (usuario.getBody() == null) {
            return null;
        }

        return usuario.getBody();
    }

    //Cancelar Pedido
    @PostMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Integer id) {
        response.clear();
        Pedido pedido = pedidoService.findById(id);

        if (pedido == null) {
            response.put("message", "Pedido no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        //Validamos que el pedido no este cancelado
        if (pedido.getEstado().equals("CANCELADO")) {
            response.put("message", "El pedido ya se encuentra cancelado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        //Cambiamos el estado del pedido a cancelado
        pedido.setEstado("CANCELADO");
        response.put("data", pedidoService.save(pedido));
        response.put("status", HttpStatus.OK);
        //Enviamos correo de cancelación
        correoCliente.enviarCorreo(new CorreoClientDto(pedido.getUsuario().getEmail(), "Pedido cancelado", "Su pedido ha sido cancelado."));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Creamos el pedido
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody PedidoDto dto) {
        response.clear();

        CarritoResponseDto carrito = null;

        //Buscamos el usuario por id
        Usuario usuario = comprobarUsuario(dto.getId_usuario());
        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Cupone cupon = null;
        if (!dto.getCodigoCupon().isEmpty()) {
            //Validamos el cupon
            cupon = cuponService.findByCodigo(dto.getCodigoCupon());
            if (cupon == null) {
                response.put("message", "Cupón no válido");
                response.put("status", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }

        BigDecimal total;

        // Validamos el carrito
        try {
            ResponseEntity<CarritoResponseDto> carritoResponse = carritoCliente.consultarCarrito(Integer.parseInt(dto.getId_carrito()));
            carrito = carritoResponse.getBody();
            log.info("Carrito: " + carrito);

            if (carrito == null || !carrito.getStatus().equals("OK")) {
                response.put("message", "Carrito no encontrado o no válido");
                response.put("status", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            total = BigDecimal.valueOf(carrito.getCarrito().getTotal());

        } catch (Exception e) {
            response.put("message", "Error al consultar el carrito: " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Creamos el carrito
        Carrito carritoSave = Carrito.builder()
                .id(Integer.parseInt(dto.getId_carrito()))
                .build();


        //Creamos el pedido
        Pedido pedido = Pedido.builder()
                .montoTotal(total)
                .direccionEnvio(dto.getDireccionEnvio())
                .usuario(usuario)
                .codigoCupon(cupon)
                .idCarrito(carritoSave)
                .build();

        Pedido pedidoGuardado = pedidoService.crearPedido(pedido, carrito);

        //Creamos el metodo de pago
        MetodosPago metodosPago = new MetodosPago();
        metodosPago.setDescripcion(dto.getDescripcion_metodo_pago());
        metodosPago.setNombre(dto.getNombre_metodo_pago());
        MetodosPago metodoPagoGuardado = metodoPagoService.save(metodosPago);
        //Creamos el metodo de pago
        Pago pago = new Pago();
        pago.setPedido(pedidoGuardado);
        pago.setMonto(total);
        pago.setEstado("COMPLETADO");
        pago.setMetodoPago(metodoPagoGuardado);
        //Guardamos el metodo de pago
        pagoService.save(pago);

        response.put("data", pedidoGuardado);
        response.put("status", HttpStatus.OK);
        //Enviamos correo de confirmación
        correoCliente.enviarCorreo(new CorreoClientDto(usuario.getEmail(), "Pedido creado", "Su pedido ha sido creado."));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}