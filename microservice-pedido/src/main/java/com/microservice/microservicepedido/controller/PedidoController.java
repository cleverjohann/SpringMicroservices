package com.microservice.microservicepedido.controller;

import com.microservice.microservicepedido.client.CarritoCliente;
import com.microservice.microservicepedido.client.UsuarioCliente;
import com.microservice.microservicepedido.model.Cupone;
import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.model.dto.PedidoDto;
import com.microservice.microservicepedido.model.dto.PedidoResponseDto;
import com.microservice.microservicepedido.model.dto.UsuarioDto;
import com.microservice.microservicepedido.service.ICuponService;
import com.microservice.microservicepedido.service.IPedidoService;
import lombok.RequiredArgsConstructor;
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
public class PedidoController {

    private final IPedidoService pedidoService;
    private final UsuarioCliente usuarioCliente;
    private final ICuponService cuponService;
    private final CarritoCliente carritoCliente;

    Map<String, Object> response = new HashMap<>();


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

    //Consultar estado pedido
    @GetMapping("/estado/{id}")
    public ResponseEntity<?> consultarEstadoPedido(@PathVariable Integer id) {
        response.clear();
        String estado = pedidoService.consultarEstadoPedido(id);
        response.put("estado", estado);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/listar")
    public ResponseEntity<?> listAll(@RequestBody UsuarioDto dto) {
        response.clear();

        //Buscamos el usuario por id
        Usuario usuario = comprobarUsuario(dto.getId_usuario());

        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        //Si existe el usuario retornamos la lista de pedidos
        List<Pedido> allByUsuarioId = pedidoService.findAllByUsuarioId(usuario);
        response.put("data", allByUsuarioId);
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Creamos el pedido
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody PedidoDto dto) {
        response.clear();
        //Buscamos el usuario por id
        Usuario usuario = comprobarUsuario(dto.getId_usuario());
        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        //Validamos el cupon
        Cupone cupon = cuponService.findByCodigo(dto.getCodigoCupon());

        if (cupon == null) {
            response.put("message", "Cupón no válido");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        BigDecimal total = BigDecimal.ZERO;

        // Validamos el carrito
        try {
            ResponseEntity<Map<String, Object>> respuesta = carritoCliente.consultarCarrito(Integer.valueOf(dto.getId_carrito()));
            if (respuesta.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> body = respuesta.getBody();

                if (body != null && body.containsKey("carrito")) {
                    Map<String, Object> carrito = (Map<String, Object>) body.get("carrito");

                    if (carrito.containsKey("total")) {
                        // Convertir el total de Double a BigDecimal
                        Double totalDouble = (Double) carrito.get("total");
                        total = BigDecimal.valueOf(totalDouble);
                    }
                }
            }
        } catch (Exception e) {
            response.put("message", "Error al consultar el carrito: " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        //Creamos el pedido
        Pedido pedido = Pedido.builder()
                .montoTotal(total)
                .direccionEnvio(dto.getDireccionEnvio())
                .usuario(usuario)
                .codigoCupon(cupon)
                .build();

        response.put("data", pedidoService.save(pedido));
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
