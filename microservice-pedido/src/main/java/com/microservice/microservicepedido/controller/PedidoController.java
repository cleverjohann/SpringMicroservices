package com.microservice.microservicepedido.controller;

import com.microservice.microservicepedido.client.UsuarioCliente;
import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.dto.PedidoDto;
import com.microservice.microservicepedido.model.dto.UsuarioDto;
import com.microservice.microservicepedido.service.IPedidoService;
import com.microservice.microserviceusuarios.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final IPedidoService pedidoService;
    private final UsuarioCliente usuarioCliente;

    Map<String, Object> response = new HashMap<>();


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
        response.clear();
        Pedido entity = pedidoService.findById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    private Usuario comprobarUsuario(String id_usuario) {
        ResponseEntity<Usuario> usuario = usuarioCliente.findById(id_usuario);

        //Si no existe el usuario retornamos un error
        if (usuario.getBody() == null) {
            return null;
        }

        return usuario.getBody();
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

    @PostMapping("/guardar")
    public ResponseEntity<?> save(@RequestBody PedidoDto dto) {
        response.clear();
        //Buscamos el usuario por id
        Usuario usuario = comprobarUsuario(dto.getId_usuario());

        if (usuario == null) {
            response.put("message", "Usuario no encontrado");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        //Creamos el pedido
        Pedido pedido = Pedido.builder()
                .montoTotal(dto.getMontoTotal())
                .estado(dto.getEstado())
                .direccionEnvio(dto.getDireccionEnvio())
                .usuario(usuario)
                .build();

        response.put("data", pedidoService.save(pedido));
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
